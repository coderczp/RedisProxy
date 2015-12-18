package com.czp.opensource.redis.cmd.manger;

import java.util.List;

/**
 * Function:命令记录,用于恢复数据或调试<br>
 *
 * Date :2015年12月18日 <br>
 * Author :coder_czp@126.com<br>
 * Copyright (c) 2015,coder_czp@126.com All Rights Reserved.
 */
public interface CmdRecoder {

	void recode(String cmd, Object client);

	List<String> getAllCmds();
}
