package com.czp.opensource.redis.cmd.handler;

import com.czp.opensource.redis.cmd.Replay;

/**
 * 
 * Function:处理客户端退出<br>
 *
 * Date :2015年12月18日 <br>
 * Author :coder_czp@126.com<br>
 * Copyright (c) 2015,coder_czp@126.com All Rights Reserved.
 */
public class QuitCmdHandler implements CmdHandler {

	public String getCmd() {
		return "quit";
	}

	public Replay handle(byte[][] args) {
		return Replay.OK;
	}

}
