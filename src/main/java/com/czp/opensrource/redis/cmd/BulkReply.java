package com.czp.opensrource.redis.cmd;

import org.glassfish.grizzly.Buffer;

/**
 * 
 * Function:二进制操作回应<br>
 * $<number of bytes of the args>\r\n<args strings>\r\n
 *
 * Date :2015年12月17日 <br>
 * Author :coder_czp@126.com<br>
 * Copyright (c) 2015,coder_czp@126.com All Rights Reserved.
 */
public class BulkReply implements Replay {

	private static final byte MARKER = '$';
	protected byte[] lenBytes;
	protected byte[] data;
	protected int size;

	public BulkReply(byte[] data) {
		this.lenBytes = String.valueOf(data.length).getBytes();
		this.size = data.length + lenBytes.length + 5;
		this.data = data;
	}

	public int size() {
		return size;
	}

	public void write(Buffer out) {

		out.put(MARKER);
		out.put(lenBytes);
		out.put(CRLF);

		if (size <= 5)
			return;

		out.put(data);
		out.put(CRLF);
	}
}
