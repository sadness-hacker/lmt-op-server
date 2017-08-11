package com.lmt.op.dao;

import org.springframework.stereotype.Repository;

import com.lmt.op.model.ServerApplication;
import com.lmt.orm.common.dao.BaseDao;

/**
 * 
 * @author ducx
 * @date 2017-07-31
 *
 */
@Repository
public class ServerApplicationDao extends BaseDao<ServerApplication, Integer> implements IServerApplicationDao {

}
