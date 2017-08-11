package com.lmt.op.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author ducx
 * @date 2017-07-30
 *
 */
public class TypeUtil {

	/**
	 * 应用类型，当前有：1.纯Java应用，2：Tomcat Web应用
	 */
	private static Map<String,String> appTypeMap = new HashMap<String, String>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("tomcat", "Tomcat Web应用");
			put("java", "Java应用");
		}
	};
	private static Map<String, String> envTypeMap = new HashMap<String, String>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("test", "测试环境");
			put("qa", "QA环境");
			put("product", "生产环境");
		}
	};
	
	/**
	 * 获取应用类型map
	 * @return
	 */
	public static Map<String,String> getAppTypeMap(){
		return appTypeMap;
	}
	/**
	 * 获取环境类型map
	 * @return
	 */
	public static Map<String,String> getEnvTypeMap(){
		return envTypeMap;
	}
	
}
