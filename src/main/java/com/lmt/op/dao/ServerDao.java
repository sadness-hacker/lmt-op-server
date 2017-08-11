package com.lmt.op.dao;

import org.springframework.stereotype.Repository;

import com.lmt.op.model.Server;
import com.lmt.orm.common.dao.BaseDao;
/**
 * 
 * @author ducx
 * @date 2017-07-29
 *
 */
@Repository
public class ServerDao extends BaseDao<Server, Integer> implements IServerDao {

}
