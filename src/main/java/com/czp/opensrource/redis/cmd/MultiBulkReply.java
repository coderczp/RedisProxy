package com.czp.opensrource.redis.cmd;

import java.util.LinkedList;
import java.util.List;

import org.glassfish.grizzly.Buffer;

/**
 * Function:批量操作回复类<br>
 ** <number of bulk replies>\r\n<bulk reply list>\r\n<br>
 *
 * Date :2015年12月17日 <br>
 * Author :coder_czp@126.com<br>
 * Copyright (c) 2015,coder_czp@126.com All Rights Reserved.
 */
public class MultiBulkReply implements Replay {

	private int size = 3;
	private static final byte frist = '*';
	private static final byte MARKER = '$';
	protected List<byte[]> datas = new LinkedList<byte[]>();

	public void add(byte[] data) {
		int len = String.valueOf(data.length).length();
		size += data.length + len + 5;
		datas.add(data);
	}

	public int size() {
		return size + String.valueOf(datas.size()).length();
	}

	public void write(Buffer out) {
		out.put(frist);
		out.put(String.valueOf(datas.size()).getBytes());
		out.put(CRLF);

		for (byte[] bs : datas) {
			out.put(MARKER);
			out.put(String.valueOf(bs.length).getBytes());
			out.put(CRLF);
			out.put(bs);
			out.put(CRLF);
		}
	}

}
