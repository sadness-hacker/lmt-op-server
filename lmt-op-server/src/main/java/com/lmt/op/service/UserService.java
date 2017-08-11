package com.lmt.op.service;

import org.springframework.stereotype.Service;

import com.lmt.op.dao.UserDao;
import com.lmt.op.model.User;
import com.lmt.orm.common.serivce.BaseService;

/**
 * 
 * @author ducx
 * @date 2017-07-29
 *
 */
@Service
public class UserService extends BaseService<UserDao, User, Integer> implements IUserService {

}
