package com.czp.opensrource.redis.cmd.handler;

import com.czp.opensrource.redis.cmd.Replay;

/**
 * Function:处理Redis的各种命令<br>
 *
 * Date :2015年12月18日 <br>
 * Author :coder_czp@126.com<br>
 * Copyright (c) 2015,coder_czp@126.com All Rights Reserved.
 */
public interface CmdHandler {

	/**
	 * 处理的命令
	 * 
	 * @return
	 */
	String getCmd();

	/**
	 * 处理命令
	 * 
	 * @param strCmd
	 * @return
	 */
	Replay handle(String strCmd);

}
