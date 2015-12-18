package com.czp.opensrource.redis.cmd;

import org.glassfish.grizzly.Buffer;

/**
 * 
 * 
 * Function: 错误状态响应<br>
 *
 * Date :2015年12月17日 <br>
 * Author :coder_czp@126.com<br>
 * Copyright (c) 2015,coder_czp@126.com All Rights Reserved.
 */
public class ErrorReplay extends StatusReplay {

	private static final byte MARKER = '-';

	public ErrorReplay(String message) {
		super(message);
	}

	public void write(Buffer out) {
		out.put(MARKER);
		out.put(data);
		out.put(CRLF);
	}
}
