package com.czp.opensrource.redis.cmd.handler;

import com.czp.opensrource.redis.cmd.BulkReply;
import com.czp.opensrource.redis.cmd.Replay;

/**
 * Function:处理Get命令<br>
 *
 * Date :2015年12月18日 <br>
 * Author :coder_czp@126.com<br>
 * Copyright (c) 2015,coder_czp@126.com All Rights Reserved.
 */
public class GetCmdHandler implements CmdHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.czp.opensrource.redis.cmd.handler.CmdHandler#getCmd()
	 */
	public String getCmd() {
		return "get";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.czp.opensrource.redis.cmd.handler.CmdHandler#handle(java.lang.String)
	 */
	public Replay handle(String strCmd) {
		return new BulkReply("testing".getBytes());
	}

}
