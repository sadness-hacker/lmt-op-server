package com.lmt.op.dao;

import org.springframework.stereotype.Repository;

import com.lmt.op.model.Config;
import com.lmt.orm.common.dao.BaseDao;

/**
 * 
 * @author ducx
 * @date 2017-07-30
 *
 */
@Repository
public class ConfigDao extends BaseDao<Config, Integer> implements IConfigDao{

}
