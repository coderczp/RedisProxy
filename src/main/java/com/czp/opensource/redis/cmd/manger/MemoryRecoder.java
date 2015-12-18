package com.czp.opensource.redis.cmd.manger;

import java.util.LinkedList;
import java.util.List;

/**
 * Function:基于内存的命令记录器<br>
 *
 * Date :2015年12月18日 <br>
 * Author :coder_czp@126.com<br>
 * Copyright (c) 2015,coder_czp@126.com All Rights Reserved.
 */
public class MemoryRecoder implements CmdRecoder {

	private List<String> cmds = new LinkedList<String>();

	public List<String> getAllCmds() {
		return cmds;
	}

	// 1378822099.421623 [0 127.0.0.1:56604] "PING"
	public void recode(String cmd, Object client) {
		long time = System.currentTimeMillis();
		cmds.add(String.format("%s [0 %s] %s", time, client, cmd));
	}

}
