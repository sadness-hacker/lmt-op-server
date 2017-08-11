package com.lmt.op.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lmt.common.action.BaseAction;
import com.lmt.op.model.Application;
import com.lmt.op.model.DeployLog;
import com.lmt.op.model.Server;
import com.lmt.op.model.ServerApplication;
import com.lmt.op.service.IApplicationService;
import com.lmt.op.service.IDeployLogService;
import com.lmt.op.service.IServerApplicationService;
import com.lmt.op.service.IServerService;

/**
 * 
 * @author ducx
 * @date 2017-08-01
 *
 */
@Controller
@RequestMapping(value="/op/center")
public class CenterAction extends BaseAction {
	
	@Resource
	private IServerService serverService;
	
	@Resource
	private IApplicationService applicationService;
	
	@Resource
	private IServerApplicationService serverApplicationService;
	
	@Resource
	private IDeployLogService deployLogService;

	
	/**
	 * 服务注册
	 * @param sign
	 * @param serverName
	 * @param appName
	 * @param envType
	 * @param basePath
	 * @param port
	 * @param description
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/regist",name="服务注册")
	@ResponseBody
	public Map<String,Object> regist(
			@RequestParam(value="sign") String sign,
			@RequestParam(value="serverName") String serverName,
			@RequestParam(value="appName") String appName,
			@RequestParam(value="envType") String envType,
			@RequestParam(value="basePath") String basePath,
			@RequestParam(value="port") String port,
			@RequestParam(value="description") String description,
			HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		ServerApplication sa = new ServerApplication();
		sa.setSign(sign);
		sa = serverApplicationService.load(sa);
		if(sa == null){
			Server server = new Server();
			server.setName(serverName);
			server = serverService.load(server);
			if(server == null){
				map.put("success", false);
				map.put("code", 201);
				map.put("msg", "服务器不存在！");
				return map;
			}
			Application app = new Application();
			app.setName(appName);
			app = applicationService.load(app);
			if(app == null){
				map.put("success", false);
				map.put("code", 202);
				map.put("msg", "应用不存在！");
				return map;
			}
			sa = new ServerApplication();
			sa.setAddTime(new Date());
			sa.setAppId(app.getId());
			sa.setAppName(appName);
			sa.setAppType(app.getType());
			sa.setBasePath(basePath);
			sa.setDescription(description);
			sa.setPort(port);
			sa.setServerId(server.getId());
			sa.setServerName(serverName);
			sa.setSign(sign);
			sa.setStatus(1);
			sa.setEnvType(envType);
			sa.setUpdateTime(new Date());
			sa.setMark("注册成功");
			serverApplicationService.insert(sa);
			map.put("success", true);
			map.put("code", 200);
			map.put("msg", "注册成功");
			return map;
		}
		if(!sa.getServerName().equals(serverName)
			|| !sa.getAppName().equals(appName)
			|| !sa.getEnvType().equals(envType)
			|| !sa.getBasePath().equals(basePath)
			|| !sa.getPort().equals(port)){
			map.put("success", false);
			map.put("code", 203);
			map.put("msg", "注册失败，已存在相同的sign,但是serverName,appName,envType,basePath,port不同！");
			return map;
		}
		map.put("success", true);
		map.put("code", 200);
		map.put("msg", "注册成功");
		return map;
	}
	
	/**
	 * 汇报状态
	 * @param sign
	 * @param status
	 * @param mark
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/report",name="汇报状态")
	@ResponseBody
	public Map<String,Object> report(
			@RequestParam(value="sign") String sign,
			@RequestParam(value="status") int status,
			@RequestParam(value="mark") String mark,
			HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		ServerApplication sa = new ServerApplication();
		sa.setSign(sign);
		sa = serverApplicationService.load(sa);
		if(sa == null){
			map.put("success", false);
			map.put("code", 201);
			map.put("msg", "服务不存在，请重新注册！");
			return map;
		}
		ServerApplication sa1 = new ServerApplication();
		sa1.setId(sa.getId());
		sa1.setStatus(status);
		sa1.setMark(mark);
		sa.setUpdateTime(new Date());
		serverApplicationService.update(sa);
		DeployLog log = deployLogService.queryLeastLog(sign);
		if(log != null && log.getStatus() != null && (log.getStatus().intValue() == 2 || log.getStatus().intValue() == 4)){
			map.put("success", true);
			map.put("code", 200);
			map.put("msg", "execute");
			map.put("data", log);
			return map;
		}
		map.put("success", true);
		map.put("code", 200);
		map.put("msg", "success");
		return map;
	}
	
}
