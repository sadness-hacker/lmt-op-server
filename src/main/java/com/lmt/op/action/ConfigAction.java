package com.lmt.op.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lmt.common.action.BaseAction;
import com.lmt.common.util.MD5;
import com.lmt.op.model.Application;
import com.lmt.op.model.Config;
import com.lmt.op.model.ServerApplication;
import com.lmt.op.service.IApplicationService;
import com.lmt.op.service.IConfigService;
import com.lmt.op.service.IServerApplicationService;
import com.lmt.op.util.TypeUtil;
import com.lmt.orm.common.model.PaginationModel;

/**
 * 
 * @author ducx
 * @date 2017-07-30
 *
 */
@Controller
@RequestMapping(value="/op/config")
public class ConfigAction extends BaseAction {
	
	@Resource
	private IConfigService configService;
	
	@Resource
	private IApplicationService applicationService;
	
	@Resource
	private IServerApplicationService serverApplicationService;

	@RequestMapping(value="/list",name="后台获取配置列表")
	public String list(
			@RequestParam(value="currPage",defaultValue="1") int currPage,
			@RequestParam(value="limit",defaultValue="10") int limit,
			@RequestParam(value="appId",defaultValue="0") int appId,
			@RequestParam(value="envType",defaultValue="") String envType,
			@RequestParam(value="name",defaultValue="") String name,
			HttpServletRequest request,HttpServletResponse response){
		PaginationModel<Config> pageModel = new PaginationModel<Config>();
		pageModel.setCurrPage(currPage);
		pageModel.setLimit(limit);
		Config conf = new Config();
		if(appId > 0){
			conf.setAppId(appId);
		}
		if(StringUtils.isNotBlank(envType)){
			conf.setEnvType(envType);
		}
		if(StringUtils.isNotBlank(name)){
			conf.setName(name);
		}
		pageModel.setT(conf);
		configService.list(pageModel);
		request.setAttribute("pageModel", pageModel);
		request.setAttribute("envTypeMap", TypeUtil.getEnvTypeMap());
		request.setAttribute("appList", applicationService.listAll());
		return "config/list";
	}
	
	@RequestMapping(value="/edit",name="后台编辑配置")
	public String edit(
			@RequestParam(value="id",defaultValue="0") int id,
			HttpServletRequest request,HttpServletResponse response){
		if(id > 0){
			Config config = configService.get(id);
			request.setAttribute("config", config);
		}
		request.setAttribute("envTypeMap", TypeUtil.getEnvTypeMap());
		List<Application> list = applicationService.listAll();
		request.setAttribute("appList", list);
		return "config/edit";
	}
	
	@RequestMapping(value="/save",name="后台保存配置")
	public void save(
			Config config,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		String md5 = MD5.md5(config.getContent());
		config.setMd5(md5);
		if(config.getId() == null || config.getId() < 1){
			config.setStatus(1);
			config.setUpdateTime(new Date());
			configService.insert(config);
		}else{
			configService.update(config);
		}
		response.sendRedirect("list");
	}
	
	@RequestMapping(value="/load",name="客户端加载配置")
	@ResponseBody
	public Map<String, Object> load(
			@RequestParam(value="sign") String sign,
			HttpServletRequest request,HttpServletResponse response){
		ServerApplication sa = new ServerApplication();
		sa.setSign(sign);
		sa = serverApplicationService.load(sa);
		int appId = sa.getAppId();
		String envType = sa.getEnvType();
		Config conf = new Config();
		conf.setAppId(appId);
		conf.setEnvType(envType);
		conf.setStatus(1);
		List<Config> list = configService.query(conf);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("msg", "下载成功");
		map.put("root", list);
		return map;
	}
	
}
