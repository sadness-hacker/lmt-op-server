package com.lmt.op.service;

import org.springframework.stereotype.Service;

import com.lmt.op.dao.ApplicationDao;
import com.lmt.op.model.Application;
import com.lmt.orm.common.serivce.BaseService;

/**
 * 
 * @author ducx
 * @date 2017-07-29
 *
 */
@Service
public class ApplicationService extends BaseService<ApplicationDao, Application, Integer> implements IApplicationService{

}
