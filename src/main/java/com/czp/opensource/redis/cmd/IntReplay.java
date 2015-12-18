package com.czp.opensource.redis.cmd;

import org.glassfish.grizzly.Buffer;

/**
 * 
 * Function:int回复类<br>
 *
 * Date :2015年12月17日 <br>
 * Author :coder_czp@126.com<br>
 * Copyright (c) 2015,coder_czp@126.com All Rights Reserved.
 */
public class IntReplay implements Replay {

	private static final byte MARKER = ':';
	private byte[] data;

	public IntReplay(int data) {
		this.data = String.valueOf(data).getBytes();
	}

	public int size() {
		return 3 + data.length;
	}

	public void write(Buffer out) {
		out.put(MARKER);
		out.put(data);
		out.put(CRLF);
	}

}
