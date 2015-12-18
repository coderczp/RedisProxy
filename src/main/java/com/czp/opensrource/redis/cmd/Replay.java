package com.czp.opensrource.redis.cmd;

import org.glassfish.grizzly.Buffer;

/***
 * 
 * 
 * Function:服务端响应<br>
 *
 * Date :2015年12月17日 <br>
 * Author :coder_czp@126.com<br>
 * Copyright (c) 2015,coder_czp@126.com All Rights Reserved.
 */
public interface Replay {

	byte[] CRLF = { '\r', '\n' };
	Replay OK = new StatusReplay("OK");
	Replay PONG = new StatusReplay("PONG");

	int size();

	void write(Buffer out);
}
