package com.czp.opensrource.redis.net;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainBuilder;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.czp.opensrource.redis.cmd.ErrorReplay;
import com.czp.opensrource.redis.cmd.Replay;
import com.czp.opensrource.redis.cmd.handler.CmdHandler;

/***
 * 
 * 
 * Function:接入服务,解析redis协议<br>
 * http://redis.io/topics/protocol<br>
 *
 * *<number of arguments> CR LF<br>
 * $<number of bytes of argument1> CR LF<br>
 * <argument data> CR LF <br>
 * ........... <br>
 * $<number of bytes of argumentN> CR LF<br>
 * <argument data> CR LF<br>
 * 
 * 
 * Date :2015年12月17日 <br>
 * Author :coder_czp@126.com<br>
 * Copyright (c) 2015,coder_czp@126.com All Rights Reserved.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ProxyServer extends BaseFilter {

	private int port;
	private TCPNIOTransport server;
	private Logger log = LoggerFactory.getLogger(ProxyServer.class);
	private Map<String, CmdHandler> handlers = new ConcurrentHashMap<String, CmdHandler>();

	public ProxyServer(int port) {

		FilterChainBuilder fcBuilder = FilterChainBuilder.stateless();
		fcBuilder.add(new TransportFilter());
		fcBuilder.add(this);

		this.server = TCPNIOTransportBuilder.newInstance().build();
		this.server.setProcessor(fcBuilder.build());
		this.port = port;
	}

	/**
	 * 注册命令处理器
	 * 
	 * @param handler
	 * @return
	 */
	public boolean addCmdHandler(CmdHandler handler) {
		return handlers.put(handler.getCmd(), handler) != null;
	}

	@Override
	public NextAction handleRead(FilterChainContext ctx) throws IOException {
		Buffer msg = ctx.getMessage();
		msg.allowBufferDispose(true);

		try {
			msg.mark();
			if (log.isDebugEnabled()) {
				String strMsg = msg.toStringContent();
				strMsg = strMsg.replaceAll("\r\n", "");
				log.debug("recive[{}]-[{}]", ctx.getAddress(), strMsg);
			}

			if ('*' != msg.get(0)) {
				/* inLine command only support ping-pong */
				writeToClient(ctx, Replay.PONG);
				return ctx.getStopAction();
			}

			decodeLength(msg);// argCount
			byte[] cmd = decodeOneArg(msg);
			String strCmd = new String(cmd);
			CmdHandler handler = handlers.get(strCmd.toLowerCase());
			if (handler != null) {
				writeToClient(ctx, handler.handle(strCmd));
			} else {
				writeToClient(ctx, new ErrorReplay("Unknow cmd:" + strCmd));
			}

		} catch (Exception e) {
			Object addr = ctx.getAddress();
			ctx.getConnection().closeSilently();
			log.error("read error:{}-{}", addr, msg.toStringContent(), e);
		}

		return ctx.getStopAction();
	}

	/**
	 * 解析所有参数
	 * 
	 * @param argCount
	 * @param buf
	 * @return
	 */
	public byte[][] decodeAllArgs(int argCount, Buffer buf) {
		byte[][] args = new byte[argCount][];
		for (int i = 0; i < args.length; i++) {
			args[i] = decodeOneArg(buf);
		}
		return args;
	}

	@Override
	public void exceptionOccurred(FilterChainContext ctx, Throwable error) {
		log.error("error:{}", ctx.getAddress(), error);
		ctx.getConnection().closeSilently();
	}

	public void start() throws IOException {
		log.info("server is runing at:{}", port);
		server.bind(port);
		server.start();
	}

	public void stop() throws IOException {
		server.shutdownNow();
		log.info("server has shutdown");
	}

	/**
	 * 解析参数:$Argbytes\r\ndata\r\n<br>
	 * 
	 * @param buf
	 * @return
	 */
	private byte[] decodeOneArg(Buffer buf) {
		int argLen = decodeLength(buf);
		byte[] arg = new byte[argLen];
		buf.get(arg);
		/* skip the \r\n */
		buf.getChar();
		return arg;
	}

	/**
	 * 解析参数个数或参数的字节大小
	 * 
	 * @param buf
	 * @return
	 */
	private int decodeLength(Buffer buf) {
		byte tmp = 0;
		int count = 0;
		buf.get();/* skip * or skip $ */
		while ((tmp = buf.get()) != '\r') {
			count = (count * 10) + (tmp - '0');
		}
		buf.get();/* skip the last \n */
		log.trace("args or data size:{}", count);
		return count;
	}

	/**
	 * 将结果回写客户端
	 * 
	 * @param ctx
	 * @param replay
	 */
	private void writeToClient(FilterChainContext ctx, Replay replay) {
		MemoryManager mm = ctx.getMemoryManager();
		Buffer buf = mm.allocate(replay.size());
		replay.write(buf);
		buf.flip();
		buf.allowBufferDispose(true);
		ctx.getConnection().write(buf);
	}

}
