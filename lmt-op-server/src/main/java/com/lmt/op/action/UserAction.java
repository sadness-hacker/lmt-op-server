package com.lmt.op.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmt.common.util.MD5;
import com.lmt.op.model.User;
import com.lmt.op.service.IUserService;
import com.lmt.orm.common.model.PaginationModel;

/**
 * 
 * @author ducx
 * @date 2017-07-29
 *
 */
@Controller
@RequestMapping(value="/op/user")
public class UserAction {
	
	@Resource
	private IUserService userService;

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/list",name="后台获取用户列表")
	public String list(
			@RequestParam(value="currPage",defaultValue="1") int currPage,
			@RequestParam(value="limit",defaultValue="10") int limit,
			HttpServletRequest request,HttpServletResponse response){
		PaginationModel<User> pageModel = new PaginationModel<User>();
		pageModel.setCurrPage(currPage);
		pageModel.setLimit(limit);
		pageModel = userService.list(pageModel);
		request.setAttribute("pageModel", pageModel);
		return "user/list";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/edit",name="后台获取修改、添加用户页面")
	public String add(
			@RequestParam(value="id",defaultValue="0") int id,
			HttpServletRequest request,HttpServletResponse response){
		if(id > 0){
			User user = userService.get(id);
			request.setAttribute("user", user);
		}
		return "user/edit";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/save",name="后台添加、修改用户")
	public void save(
			User user,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		user.setSalt("0809");
		String password = user.getPassword();
		if(password != null && !"".equals(password.trim())){
			password = MD5.md5(password);
			user.setPassword(password);
		}
		if(user.getStatus() == null){
			user.setStatus(1);
		}
		if(user.getId() != null && user.getId().intValue() > 0){
			if(password != null && !"".equals(password.trim())){
				User u = userService.get(user.getId());
				user.setPassword(u.getPassword());
			}
			userService.update(user);
		}else{
			userService.insert(user);
		}
		user = (User) request.getSession().getAttribute("curr_user");
		if(user.getPrivileges() != null && user.getPrivileges().indexOf("userManageActive") > -1){
			response.sendRedirect("list");
		}else{
			response.sendRedirect("../index");
		}
	}
	
}
