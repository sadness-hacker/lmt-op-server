package com.lmt.op.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.lmt.common.util.MD5;
import com.lmt.op.util.GlobalVar;
import com.lmt.op.util.JSONUtil;

/**
 * 
 * @author ducx
 * @date 2016-11-27
 * 文件上传servlet
 *
 */
@WebServlet(name="upload-servlet",value="/upload")
@MultipartConfig(maxFileSize=10485760,fileSizeThreshold=2097152)
public class UploadServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * UEditor配置文件json字符串
	 */
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
	/**
	 * {
	 * "original":"demo.jpg",
	 * "name":"demo.jpg",
	 * "url":"\/server\/ueditor\/upload\/image\/demo.jpg",
	 * "size":"99697",
	 * "type":".jpg",
	 * "state":"SUCCESS"
	 * }
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		Part part = request.getPart("upfile");
		long size = part.getSize();
		String fileName = part.getSubmittedFileName();
		String suffix = getSuffix(fileName);
		String date = sdf.format(new Date());
		String path = "upload/images/" + date + "/" + getRandomFileName(fileName,suffix);
		String savePath = GlobalVar.UPLOAD_PATH + "/" + path;
		File file = new File(savePath);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		OutputStream os = new FileOutputStream(file);
		InputStream is = part.getInputStream();
		byte [] buff = new byte[1024];
		int i = -1;
		while((i = is.read(buff)) > -1){
			os.write(buff,0,i);
		}
		os.close();
		is.close();
		String url = GlobalVar.CDN_URL + "/" + path;
		Map<String,Object> json = new HashMap<String, Object>();
		json.put("url", url);
		json.put("relativeUrl", path);
		json.put("orginal", fileName);
		json.put("name", fileName);
		json.put("size", String.valueOf(size));
		json.put("type", suffix);
		json.put("state", "SUCCESS");
		response.getWriter().print(JSONUtil.toJsonString(json));
	}
	
	private String getRandomFileName(String fileName,String suffix){
		return MD5.md5(System.currentTimeMillis() + fileName) + suffix;
	}
	
	private String getSuffix(String fileName){
		int i = fileName.lastIndexOf(".");
		String suffix = "";
		if(i>-1){
			suffix = fileName.substring(i, fileName.length());
		}
		return suffix;
	}
}
