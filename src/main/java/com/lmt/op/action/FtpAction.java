package com.lmt.op.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lmt.common.util.FTPUtil;

/**
 * 
 * @author ducx
 * @date 2017-08-09
 * ftp操作
 *
 */
@Controller
@RequestMapping(value="/op/ftp")
public class FtpAction {

	@RequestMapping(value="/list",name="获取ftp目录下的所有文件")
	public String list(
			@RequestParam(value="path",defaultValue="/") String path,
			HttpServletRequest request,HttpServletResponse response){
		List<Map<String,Object>> list = FTPUtil.listAllFiles(path);
		request.setAttribute("list", list);
		return "ftp/list";
	}
	
	@RequestMapping(value="/download",name="通过ftp下载指定路径的文件")
	@ResponseBody
	public Map<String,Object> download(
			@RequestParam(value="path") String path,
			HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = FTPUtil.download(path);
		return map;
	}
	
}
