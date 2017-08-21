package com.lmt.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import com.lmt.op.util.GlobalVar;

/**
 * 
 * @author ducx
 * ftp工具类，下载，上传文件等
 *
 */
public class FTPUtil {
	
	private static Logger log = Logger.getLogger(FTPUtil.class);
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
	private static String host = "db01";
	
	private static int port = 21;
	
	private static String username = "ftp01";
	
	private static String password = "123456";
	
	private static String rootPath = "/";
	
	/**
	 * 下载zip包
	 * @param path
	 * @param site
	 * @param version
	 * @return
	 */
	public static Map<String,Object> download(String filePath){
		FTPClient client = new FTPClient();
		try {
			client.connect(host, port);
			client.login(username, password);
			client.enterLocalPassiveMode();
			client.setBufferSize(1024);
			client.setControlEncoding("UTF-8");
			client.changeWorkingDirectory(rootPath);
			while(filePath.startsWith("/")){
				filePath = filePath.replaceFirst("/", "");
			}
			String [] arr = filePath.split("/");
			if(arr.length >= 2){
				for(int i=0;i<arr.length-1;i++){
					client.changeWorkingDirectory(arr[i]);
				}
			}
			String filename = arr[arr.length-1];
			String date = sdf.format(new Date());
			String savePath = "upload/images/" + date + "/" + MD5.md5(System.currentTimeMillis() + filename) + getSuffix(filename);
			String realPath = GlobalVar.UPLOAD_PATH + "/" + savePath;
			File file = new File(realPath);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			client.setFileType(FTPClient.BINARY_FILE_TYPE);
			boolean b  = client.retrieveFile(filename, new FileOutputStream(file));
			if(b){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("code", 200);
				map.put("success", true);
				map.put("relativeUrl", savePath);
				map.put("msg", "下载成功");
				return map;
			}
		} catch (SocketException e) {
			log.error("ftp socket异常", e);
		} catch (IOException e) {
			log.error("ftp io异常", e);
		} finally {
			try {
				client.disconnect();
			} catch (IOException e) {
				log.error("关闭ftp连接异常", e);
			}
		}
		return null;
	}
	
	public static List<Map<String,Object>> listAllFiles(String path){
		FTPClient client = new FTPClient();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			client.connect(host, port);
			client.login(username, password);
			client.enterLocalPassiveMode();
			client.setBufferSize(1024);
			client.setControlEncoding("UTF-8");
			client.changeWorkingDirectory(path);
			FTPFile [] files = client.listFiles();
			for(FTPFile file : files){
				Map<String,Object> map = new HashMap<String, Object>();
				String name = file.getName();
				map.put("name", name);
				map.put("path", path);
				map.put("isDirectory", file.isDirectory());
				list.add(map);
			}
		} catch (SocketException e) {
			log.error("ftp socket异常", e);
		} catch (IOException e) {
			log.error("ftp io异常", e);
		} finally {
			try {
				client.disconnect();
			} catch (IOException e) {
				log.error("关闭ftp连接异常", e);
			}
		}
		return list;
	}
	
	private static String getSuffix(String fileName){
		int i = fileName.lastIndexOf(".");
		String suffix = "";
		if(i>-1){
			suffix = fileName.substring(i, fileName.length());
		}
		return suffix;
	}
	
	public static void main(String [] args){
//		downloadPkg(bean, "/tmp", "site-mobile", "a");
//		downloadMd5(bean, "/tmp", "site-mobile", "a");
		List<Map<String,Object>> list = listAllFiles(rootPath);
		for(Map<String,Object> map : list){
			System.out.println(map.get("path") + " " + map.get("name") + " " + map.get("isDirectory"));
		}
	}
	
}
