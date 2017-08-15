package com.lmt.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author ducx
 * @date 2017-08-14
 * ip获取工具类
 *
 */
public class IPUtil {
	/**
	 * 获取ip
	 * @param request
	 * @return
	 */
	public static String getRemoteIp(HttpServletRequest request){
		if(request.getHeader("x-forwarded-for") == null) {
             return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
	}
}
