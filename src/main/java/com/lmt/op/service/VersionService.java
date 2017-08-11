package com.lmt.op.service;

import org.springframework.stereotype.Service;

import com.lmt.op.dao.VersionDao;
import com.lmt.op.model.Version;
import com.lmt.orm.common.serivce.BaseService;

/**
 * 
 * @author ducx
 * @date 2017-07-29
 *
 */
@Service
public class VersionService extends BaseService<VersionDao, Version, Integer> implements IVersionService{

	@Override
	public long countByAppIdTypeDate(int appId, String type, String versionDate) {
		String sql = "select count(*) from lmt_version where app_id=? and type=? and version_date=?";
		return count(sql, appId,type,versionDate);
	}

}
