package com.czp.opensource.redis.main;

import java.io.IOException;

import com.czp.opensource.redis.cmd.handler.AuthCmdHandler;
import com.czp.opensource.redis.cmd.handler.GetCmdHandler;
import com.czp.opensource.redis.cmd.handler.SetCmdHandler;
import com.czp.opensource.redis.cmd.manger.CmdRecoder;
import com.czp.opensource.redis.cmd.manger.MemoryRecoder;
import com.czp.opensource.redis.net.ProxyServer;

/**
 * 
 * 
 * Function:程序入口<br>
 *
 * Date :2015年12月17日 <br>
 * Author :coder_czp@126.com<br>
 * Copyright (c) 2015,coder_czp@126.com All Rights Reserved.
 */
public class Main {

	public static void main(String[] args) throws IOException {
		CmdRecoder recoder = new MemoryRecoder();
		ProxyServer ser = new ProxyServer(6379, recoder);
		ser.addCmdHandler(new GetCmdHandler());
		ser.addCmdHandler(new SetCmdHandler());
		ser.addCmdHandler(new AuthCmdHandler());
		ser.start();
		System.in.read();
		ser.stop();
	}
}
