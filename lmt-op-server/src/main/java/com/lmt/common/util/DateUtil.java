package com.lmt.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author ducx
 * @date 2017-07-31
 *
 */
public class DateUtil {

	private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 格式化成yyyy-MM-dd格式
	 * @param date
	 * @return
	 */
	public static String formatYYYYMMDD(Date date){
		return sdf1.format(date);
	}
	
	/**
	 * 格式化成yyyy-MM-dd HH:mm:ss格式
	 * @param date
	 * @return
	 */
	public static String format(Date date){
		return sdf2.format(date);
	}
	
}
