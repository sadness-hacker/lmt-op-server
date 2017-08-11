package com.lmt.op.dao;

import org.springframework.stereotype.Repository;

import com.lmt.op.model.DeployLog;
import com.lmt.orm.builder.SqlBuilder;
import com.lmt.orm.common.dao.BaseDao;
/**
 * 
 * @author ducx
 * @date 2017-07-29
 *
 */
@Repository
public class DeployLogDao extends BaseDao<DeployLog, Integer> implements IDeployLogDao {

	@Override
	public DeployLog queryLeastLog(String sign) {
		String sql = "select " + SqlBuilder.buildResultSql(DeployLog.class) + " from lmt_deploy_log where sign=? order by update_time desc limit 1";
		return executeQueryOne(sql, sign);
	}

}
