package com.lmt.op.service;

import org.springframework.stereotype.Service;
import com.lmt.op.dao.ServerDao;
import com.lmt.op.model.Server;
import com.lmt.orm.common.serivce.BaseService;

/**
 * 
 * @author ducx
 * @date 2017-07-29
 *
 */
@Service
public class ServerService extends BaseService<ServerDao, Server, Integer> implements IServerService {

}
