package com.lmt.op.service;

import org.springframework.stereotype.Service;

import com.lmt.op.dao.ServerApplicationDao;
import com.lmt.op.model.ServerApplication;
import com.lmt.orm.common.serivce.BaseService;

/**
 * 
 * @author ducx
 * @date 2017-07-31
 *
 */
@Service
public class ServerApplicationService extends BaseService<ServerApplicationDao, ServerApplication, Integer> implements IServerApplicationService{

}
