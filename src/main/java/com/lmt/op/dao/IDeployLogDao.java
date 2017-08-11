package com.lmt.op.dao;

import com.lmt.op.model.DeployLog;
import com.lmt.orm.common.dao.IBaseDao;

/**
 * 
 * @author ducx
 * @date 2017-07-29
 *
 */
public interface IDeployLogDao extends IBaseDao<DeployLog, Integer>{

	/**
	 * 查寻最新操作命令
	 * @param sign
	 * @return
	 */
	public DeployLog queryLeastLog(String sign);

}
