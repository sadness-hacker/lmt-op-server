package com.lmt.op.cache;

import java.util.HashSet;
import java.util.Set;


/**
 * 
 * @author ducx
 * @date 2017-08-14
 * 可信任ip缓存
 *
 */
public class TrustIPCache {
	
	private static Set<String> ipSet = new HashSet<String>(){

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * 默认本机ip可信
		 */
		{
			add("127.0.0.1");
			add("0:0:0:0:0:0:0:1");
			add("::1");
		}
		
	};
	
	/**
	 * 添加可信任ip
	 * @param ip
	 */
	public static void addTrustIp(String ip){
		ipSet.add(ip);
	}
	
	/**
	 * 判断ip是否可信
	 * @param ip
	 * @return
	 */
	public static boolean isTrustIp(String ip){
		return ipSet.contains(ip);
	}

}
