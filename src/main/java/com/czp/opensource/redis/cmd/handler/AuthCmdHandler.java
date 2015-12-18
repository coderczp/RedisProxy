package com.czp.opensource.redis.cmd.handler;

import com.czp.opensource.redis.cmd.Replay;

/**
 * Function:密码验证<br>
 *
 * Date :2015年12月18日 <br>
 * Author :coder_czp@126.com<br>
 * Copyright (c) 2015,coder_czp@126.com All Rights Reserved.
 */
public class AuthCmdHandler implements CmdHandler {

	public String getCmd() {
		return "auth";
	}

	public Replay handle(byte[][] args) {
		if (new String(args[0]).equals("pwd")) {
			return Replay.OK;
		}
		return Replay.PWD_ERR;
	}

}
