package com.lmt.op.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmt.common.util.MD5;
import com.lmt.op.model.User;
import com.lmt.op.service.IUserService;

/**
 * 
 * @author ducx
 * @date 2017-07-29
 *
 */
@Controller
@RequestMapping
public class LoginAction {
	
	@Resource
	private IUserService userService;

	@RequestMapping(value="/login",method=RequestMethod.GET,name="用户登录")
	public String login(
			HttpServletRequest request,HttpServletResponse response){
		
		return "login";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST,name="用户登录")
	public String login(
			@RequestParam(value="username") String username,
			@RequestParam(value="password") String password,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		User user = new User();
		user.setUsername(username);
		user = userService.load(user);
		if(user == null){
			request.setAttribute("error_msg", "用户不存在");
		}else{
			password = MD5.md5(password);
			if(!password.equals(user.getPassword())){
				request.setAttribute("error_msg", "密码错误");
			}else{
				request.getSession().setAttribute("curr_user", user);
				response.sendRedirect("op/index");
				return null;
			}
		}
		return "login";
	}
	
	@RequestMapping(value="/logout",name="用户退出")
	public void logout(
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.getSession().invalidate();
		response.sendRedirect("login");
	}
	
}
