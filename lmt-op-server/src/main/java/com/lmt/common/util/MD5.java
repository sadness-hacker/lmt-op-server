package com.lmt.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author ducx
 * @date 2017-07-31
 *
 */
public class MD5 {

	/**
	 * 密码md5加密
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String encryptPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
        //加密后的字符串
	    byte[] result =  md5.digest(password.getBytes("utf-8"));
	    StringBuffer sb = new StringBuffer();
	    for (int j = 0; j < result.length; ++j) {
            int b = result[j] & 0xFF;
            if (b < 0x10) sb.append('0');
            sb.append(Integer.toHexString(b));
        }  
		return sb.toString();
	}
	/**
	 * md5加密，异常时返回空字符串
	 * @param msg 加密字符串
	 * @return
	 */
	public static String md5(String msg){
		String s  = "";
		try {
			s = encryptPassword(msg);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * 计算文件md5值
	 * @param file
	 * @return
	 */
	public static String getFileMd5(File file){
		if(file.isDirectory()){
			return null;
		}
		String value = null;
		FileInputStream in = null;
	    try {
	        in = new FileInputStream(file);
	        MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
	        MessageDigest md5 = MessageDigest.getInstance("MD5");
	        md5.update(byteBuffer);
	        BigInteger bi = new BigInteger(1, md5.digest());
	        value = bi.toString(16);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	       if(null != in) {
	            try {
	                in.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return value;
	}
	/**
	 * 根据文件路径计算文件md5值
	 * @param file
	 * @return
	 */
	public static String getFileMd5(String path){
		File file = new File(path);
		return getFileMd5(file);
	}
	
}
