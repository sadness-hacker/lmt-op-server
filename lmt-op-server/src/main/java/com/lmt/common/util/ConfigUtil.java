package com.lmt.common.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * 
 * @author ducx
 * @date 2016-11-27
 * 读取配置文件的工具类
 *
 */
public class ConfigUtil {
	/**
	 * 配置文件缓存
	 */
	private static Map<String, Properties> propertiesMap = new HashMap<String, Properties>();
	
	public static String get(String fileName,String key){
		fileName = fileName + ".properties";
		Properties p = propertiesMap.get(fileName);
		if(p == null){
			p = new Properties();
			try {
				p.load(new InputStreamReader(ConfigUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
				propertiesMap.put(fileName, p);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return p.getProperty(key);
	}
	
}
