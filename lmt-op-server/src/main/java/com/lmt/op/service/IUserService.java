package com.lmt.op.service;

import com.lmt.op.dao.UserDao;
import com.lmt.op.model.User;
import com.lmt.orm.common.serivce.IBaseService;

/**
 * 
 * @author ducx
 * @date 2017-07-29
 *
 */
public interface IUserService extends IBaseService<UserDao, User, Integer> {

}
