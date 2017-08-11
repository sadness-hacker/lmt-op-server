package com.lmt.op.service;

import com.lmt.op.dao.DeployLogDao;
import com.lmt.op.model.DeployLog;
import com.lmt.orm.common.serivce.IBaseService;

/**
 * 
 * @author ducx
 * @date 2017-07-29
 *
 */
public interface IDeployLogService extends IBaseService<DeployLogDao, DeployLog, Integer> {

	/**
	 * 查寻最新的一条记录
	 * @param sign
	 * @return
	 */
	public DeployLog queryLeastLog(String sign);

}
