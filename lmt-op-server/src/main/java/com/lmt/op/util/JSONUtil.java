package com.lmt.op.util;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.lmt.common.serializer.CustomLongSerializer;

/**
 * 
 * @author ducx
 * @date 2017-07-31
 *
 */
public class JSONUtil {
	
	private static final ObjectMapper mapper = getObjectMapper();
	
	/**
	 * 获取一个ObjectMapper，日期序列化为yyyy-MM-dd HH:mm:ss格式，long型序列化为字符串
	 * @return
	 */
	public static ObjectMapper getObjectMapper(){
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		SimpleModule module = new SimpleModule();
		module.addSerializer(Long.class,new CustomLongSerializer());
		mapper.registerModule(module);
		return mapper;
	}

	/**
	 * 对象转为json字符串
	 * @param object
	 * @return
	 */
	public static String toJsonString(Object object){
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
