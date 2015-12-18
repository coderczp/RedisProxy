package com.czp.opensrource.redis.cmd.handler;

import com.czp.opensrource.redis.cmd.MultiBulkReply;
import com.czp.opensrource.redis.cmd.Replay;

/**
 * Function:处理Set命令<br>
 *
 * Date :2015年12月18日 <br>
 * Author :coder_czp@126.com<br>
 * Copyright (c) 2015,coder_czp@126.com All Rights Reserved.
 */
public class SetCmdHandler implements CmdHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.czp.opensrource.redis.cmd.handler.CmdHandler#getCmd()
	 */
	public String getCmd() {
		return "set";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.czp.opensrource.redis.cmd.handler.CmdHandler#handle(java.lang.String)
	 */
	public Replay handle(String strCmd) {
		MultiBulkReply b = new MultiBulkReply();
		b.add("ddd".getBytes());
		b.add("ccc".getBytes());
		return b;
	}

}
