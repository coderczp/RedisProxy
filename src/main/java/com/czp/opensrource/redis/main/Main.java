package com.czp.opensrource.redis.main;

import java.io.IOException;

import com.czp.opensrource.redis.cmd.handler.GetCmdHandler;
import com.czp.opensrource.redis.cmd.handler.SetCmdHandler;
import com.czp.opensrource.redis.net.ProxyServer;

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
		ProxyServer server = new ProxyServer(6379);
		server.addCmdHandler(new SetCmdHandler());
		server.addCmdHandler(new GetCmdHandler());
		server.start();
		System.in.read();
		server.stop();
	}
}
