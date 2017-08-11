package com.lmt.op.action;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmt.common.action.BaseAction;
import com.lmt.op.model.DeployLog;
import com.lmt.op.model.ServerApplication;
import com.lmt.op.service.IDeployLogService;
import com.lmt.op.service.IServerApplicationService;
import com.lmt.op.util.TypeUtil;
import com.lmt.orm.common.model.PaginationModel;

/**
 * 
 * @author ducx
 * @date 2017-07-31
 *
 */
@Controller
@RequestMapping(value="/op/serverApplication")
public class ServerApplicationAction extends BaseAction {
	
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
	@RequestMapping(value="/list",name="获取服务器应用列表")
	public String list(
			@RequestParam(value="currPage",defaultValue="1") int currPage,
			@RequestParam(value="limit",defaultValue="10") int limit,
			HttpServletRequest request,HttpServletResponse response){
		PaginationModel<ServerApplication> pageModel = new PaginationModel<ServerApplication>();
		pageModel.setCurrPage(currPage);
		pageModel.setLimit(limit);
		serverApplicationService.list(pageModel);
		request.setAttribute("pageModel", pageModel);
		request.setAttribute("envTypeMap", TypeUtil.getEnvTypeMap());
		request.setAttribute("appTypeMap", TypeUtil.getAppTypeMap());
		return "serverApplication/list";
	}
	
	@RequestMapping(value="/restart",name="重启应用")
	public void restart(
			@RequestParam(value="id") int id,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		ServerApplication sa = serverApplicationService.get(id);
		DeployLog dl = new DeployLog();
		dl.setAddTime(new Date());
		dl.setAppId(sa.getAppId());
		dl.setAppName(sa.getAppName());
		dl.setAppType(sa.getAppType());
		dl.setBakVersion("");
		dl.setCmd("restart");
		dl.setDescription("");
		dl.setRestart(1);
		dl.setRollbackStatus(0);
		dl.setRollbackUser("");
		dl.setServerId(sa.getServerId());
		dl.setServerName(sa.getServerName());
		dl.setSign(sa.getSign());
		dl.setStatus(2);
		dl.setUpdateTime(dl.getAddTime());
		dl.setVersionId(0);
		dl.setVersionNum("");
		dl.setVersionType(sa.getEnvType());
		deployLogService.insert(dl);
		response.sendRedirect("../deployLog/list");
	}
	
}
