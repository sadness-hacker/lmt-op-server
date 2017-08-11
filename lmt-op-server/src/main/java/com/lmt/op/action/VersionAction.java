package com.lmt.op.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmt.common.action.BaseAction;
import com.lmt.common.util.DateUtil;
import com.lmt.common.util.MD5;
import com.lmt.op.model.Application;
import com.lmt.op.model.DeployLog;
import com.lmt.op.model.ServerApplication;
import com.lmt.op.model.Version;
import com.lmt.op.service.IApplicationService;
import com.lmt.op.service.IDeployLogService;
import com.lmt.op.service.IServerApplicationService;
import com.lmt.op.service.IServerService;
import com.lmt.op.service.IVersionService;
import com.lmt.op.util.GlobalVar;
import com.lmt.op.util.TypeUtil;
import com.lmt.orm.common.model.PaginationModel;

/**
 * 
 * @author ducx
 * @date 2017-07-31
 *
 */
@Controller
@RequestMapping(value="/op/version")
public class VersionAction extends BaseAction {
	
	@Resource
	private IVersionService versionService;
	
	@Resource
	private IApplicationService applicationService;
	
	@Resource
	private IServerService serverService;
	
	@Resource
	private IServerApplicationService serverApplicationService;
	
	@Resource
	private IDeployLogService deployLogService;

	/**
	 * 
	 * @param currPage
	 * @param limit
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/list",name="后台管理，版本列表")
	public String list(
			@RequestParam(value="currPage",defaultValue="1") int currPage,
			@RequestParam(value="limit",defaultValue="10") int limit,
			HttpServletRequest request,HttpServletResponse response){
		PaginationModel<Version> pageModel = new PaginationModel<Version>();
		pageModel.setCurrPage(currPage);
		pageModel.setLimit(limit);
		versionService.list(pageModel);
		request.setAttribute("pageModel", pageModel);
		request.setAttribute("envTypeMap", TypeUtil.getEnvTypeMap());
		return "version/list";
	}
	
	@RequestMapping(value="/add",name="版本管理，获取版本添加页面")
	public String add(
			HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("envTypeMap", TypeUtil.getEnvTypeMap());
		List<Application> list = applicationService.listAll();
		request.setAttribute("appList", list);
		return "version/edit";
	}
	
	@RequestMapping(value="/save",name="版本管理，保存添加、修改")
	public void save(
			Version version,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		String path = GlobalVar.UPLOAD_PATH + "/" + version.getFilePath();
		File file = new File(path);
		long fileSize = file.length();
		String md5 = MD5.getFileMd5(file);
		version.setFileMd5(md5);
		version.setFileSize(fileSize);
		version.setAddTime(new Date());
		version.setAddUser("ducx");
		version.setDeployMark("");
		version.setDeployStatus(0);
		String date = DateUtil.formatYYYYMMDD(new Date());
		long num = versionService.countByAppIdTypeDate(version.getAppId(), version.getType(), date);
		num++;
		if(num < 10){
			version.setVersionNum(date.replace("-", "") + "-0" + num);
		}else{
			version.setVersionNum(date.replace("-", "") + "-" + num);
		}
		version.setVersionDate(date);
		versionService.insert(version);
		response.sendRedirect("list");
	}
	
	/**
	 * 版本发布
	 * @param id
	 * @param type
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="/deploy",name="发布版本")
	public void deploy(
			@RequestParam(value="id") int id,
			@RequestParam(value="type",defaultValue="batchDeploy") String type,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		Version version = versionService.get(id);
		if(version == null || version.getStatus() != 1){
			response.sendRedirect("list");
			return;
		}
		String envType = version.getType();
		int appId = version.getAppId();
		Application app = applicationService.get(appId);
		
		DeployLog dl = new DeployLog();
		Date date = new Date();
		dl.setAddTime(date);
		dl.setAppId(appId);
		dl.setAppName(app.getName());
		dl.setAppType(app.getType());
		dl.setBakVersion("");
		dl.setCmd("deploy");
		dl.setDeployTime(date);
		dl.setDescription(version.getDescription());
		dl.setRestart(version.getRestart());
		dl.setRollbackStatus(0);
		dl.setRollbackUser("");
		dl.setUpdateTime(date);
		dl.setVersionId(version.getId());
		dl.setVersionNum(version.getVersionNum());
		dl.setVersionType(envType);
		dl.setStatus(2);//批量发布
		if("batchDeploy".equals(type)){
			dl.setStatus(3);//需要单个发布
		}
		
		ServerApplication sa = new ServerApplication();
		sa.setAppId(appId);
		sa.setEnvType(envType);
		List<ServerApplication> saList = serverApplicationService.query(sa);
		for(ServerApplication a : saList){
			dl.setId(null);
			dl.setServerId(a.getServerId());
			dl.setServerName(a.getServerName());
			dl.setSign(a.getSign());
			deployLogService.insert(dl);
		}
		if("batchDeploy".equals(type)){
			response.sendRedirect("logPage?id=" + id);
		}else{
			response.sendRedirect("../deployLog/list");
		}
		
		version.setStatus(2);
		versionService.update(version);
	}
	
	/**
	 * 获取页面
	 * @param id
	 * @param request
	 * @param resonse
	 */
	@RequestMapping(value="/logPage",name="获取页面")
	public String logPage(
			@RequestParam(value="id",required=false) Integer id,
			@RequestParam(value="deplogId",required=false) Integer deplogId,
			HttpServletRequest request,HttpServletResponse resonse){
		Version version = versionService.get(id);
		request.setAttribute("version", version);
		DeployLog dl = new DeployLog();
		if(id != null){
			dl.setVersionId(id);
		}
		if(deplogId != null){
			dl.setId(deplogId);
		}
		List<DeployLog> list = deployLogService.query(dl);
		request.setAttribute("list", list);
		return "version/log";
	}
	
	/**
	 * 获取日志
	 * @param id
	 * @param log
	 * @param request
	 * @param resonse
	 */
	@RequestMapping(value="/log",name="获取日志数据")
	public Map<String,Object> log(
			@RequestParam(value="id") int id,
			HttpServletRequest request,HttpServletResponse resonse){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("success", true);
		DeployLog dl = new DeployLog();
		dl.setVersionId(id);
		List<DeployLog> list = deployLogService.query(dl);
		map.put("root", list);
		return map;
	}
	
	/**
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="/download",name="下载更新包")
	public void download(
			@RequestParam(value="id") int id,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		Version version = versionService.get(id);
		String path = GlobalVar.UPLOAD_PATH + "/" + version.getFilePath();
		File file = new File(path);
		String suffix = path.substring(path.lastIndexOf("."), path.length());
		String filename = version.getVersionNum() + suffix;
		filename = URLEncoder.encode(filename, "UTF-8");
        response.reset();  
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.addHeader("Content-Length", String.valueOf(file.length()));
        response.setContentType("application/octet-stream;charset=UTF-8");
        InputStream is = new FileInputStream(file);
        OutputStream os = response.getOutputStream();
        int i = -1;
        byte [] buff = new byte[4096];
        while((i = is.read(buff)) > -1){
        	os.write(buff, 0, i);
        }
        is.close();
        os.close();
	}
	
	/**
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="/md5",name="获取md5值")
	public void md5(
			@RequestParam(value="id") int id,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		Version version = versionService.get(id);
		response.getWriter().print(version.getFileMd5());
	}
}
