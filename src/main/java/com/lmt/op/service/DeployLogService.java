package com.lmt.op.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lmt.op.dao.DeployLogDao;
import com.lmt.op.dao.IDeployLogDao;
import com.lmt.op.model.DeployLog;
import com.lmt.orm.common.serivce.BaseService;

/**
 * 
 * @author ducx
 * @date 2017-07-29
 *
 */
@Service
public class DeployLogService extends BaseService<DeployLogDao, DeployLog, Integer> implements IDeployLogService {

	@Resource
	private IDeployLogDao deployLogDao;
	@Override
	public DeployLog queryLeastLog(String sign) {
		return deployLogDao.queryLeastLog(sign);
	}

}
