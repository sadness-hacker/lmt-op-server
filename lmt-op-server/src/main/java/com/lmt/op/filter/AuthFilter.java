package com.lmt.op.filter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.lmt.op.model.User;


/**
 * 
 * @author ducx
 * @date 2017-07-08
 *
 */
@WebFilter(filterName="auth-filter",urlPatterns="/*")
public class AuthFilter implements Filter {
	
	/**
	 * 不需要验证的连接
	 */
	private Map<String,Object> UN_AUTH_URL = new ConcurrentHashMap<>();
	/**
	 * 需要验证的连接
	 */
	private Map<String,Object> AUTH_URL = new ConcurrentHashMap<>();
	
	private Object o = new Object();
	
	@Override
	public void destroy() {
		System.out.println("auth filter destory...");
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String uri = request.getRequestURI();
		String path = request.getContextPath();
		if(!StringUtils.isBlank(path) && !path.endsWith("/")){
			uri = uri.replaceFirst(path, "");
		}
		if(isAuthUri(uri)){
			User user = (User) request.getSession().getAttribute("curr_user");
			if(user == null){
				response.sendRedirect(path + "/login");
				return;
			}
		}
		chain.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("auth filter destory...");
	}
	
	private String [] unAuthSuffix = {
			".css",
			"css/css",
			".js",
			".css.map",
			".ico",
			".png",
			".jpg",
			".jpeg",
			".gif",
			".bmp",
			".svg",
			".xls",
			".xlsx",
			".doc",
			".docx",
			".html",
			".htm",
			".woff",
			".woff2",
			".eot",
			".ttf",
			".txt",
			".zip",
			".tar",
			".gz",
			".bz2",
			".bz",
			"rar",
			".json",
			".7z"
	};

	private String [] unAuthRegex = {
			"/op/deployLog/log",
			"/op/config/load",
			"/op/deployLog/reportRollbackStatus",
			"/op/center/report",
			"/op/center/regist",
			"/op/version/download",
			"/op/version/md5",
			"/op/deployLog/bakVersion",
			"/logout",
			"/login"
	};
	
	public boolean isAuthUri(String uri){
		if(UN_AUTH_URL.containsKey(uri)){
			return false;
		}
		if(AUTH_URL.containsKey(uri)){
			return true;
		}
		if(StringUtils.isBlank(uri)){
			UN_AUTH_URL.put(uri, o);
			return false;
		}
		for(String s : unAuthSuffix){
			if(uri.toLowerCase().endsWith(s)){
				UN_AUTH_URL.put(uri, o);
				return false;
			}
		}
		for(String s : unAuthRegex){
			if(uri.matches(s)){
				UN_AUTH_URL.put(uri, o);
				return false;
			}
		}
		AUTH_URL.put(uri, o);
		return true;
	}
	
}
