package com.czp.opensource.redis.cmd;

import org.glassfish.grizzly.Buffer;

/***
 * 
 * Function:状态命令<br>
 *
 * Date :2015年12月17日 <br>
 * Author :coder_czp@126.com<br>
 * Copyright (c) 2015,coder_czp@126.com All Rights Reserved.
 */
public class StatusReplay implements Replay {

	private static final byte MARKER = '+';
	protected byte[] data;
	protected int size;

	public StatusReplay(String message) {
		this.data = message.getBytes();
		this.size = 3 + data.length;
	}

	public int size() {
		return size;
	}

	public void write(Buffer out) {
		out.put(MARKER);
		out.put(data);
		out.put(CRLF);
	}

}
