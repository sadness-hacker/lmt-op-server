package com.lmt.op.service;

import com.lmt.op.dao.VersionDao;
import com.lmt.op.model.Version;
import com.lmt.orm.common.serivce.IBaseService;

/**
 * 
 * @author ducx
 * @date 2017-07-29
 *
 */
public interface IVersionService extends IBaseService<VersionDao, Version, Integer> {

	/**
	 * 统计指定应用、环境类型、日期发布的版本数
	 * @param appId
	 * @param type
	 * @param versionDate
	 * @return
	 */
	public long countByAppIdTypeDate(int appId,String type,String versionDate);
	
}
