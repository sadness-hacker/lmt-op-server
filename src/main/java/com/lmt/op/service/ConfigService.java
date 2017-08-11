package com.lmt.op.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lmt.op.dao.ConfigDao;
import com.lmt.op.dao.IConfigHistroyDao;
import com.lmt.op.model.Config;
import com.lmt.op.model.ConfigHistroy;
import com.lmt.orm.common.serivce.BaseService;

/**
 * 
 * @author ducx
 * @date 2017-07-30
 *
 */
@Service
public class ConfigService extends BaseService<ConfigDao, Config, Integer> implements IConfigService {

	@Resource
	private IConfigHistroyDao configHistroyDao;
	
	@Override
	public int update(Config t) {
		ConfigHistroy ch = new ConfigHistroy(get(t.getId()));
		configHistroyDao.insert(ch);
		return super.update(t);
	}
	
}
