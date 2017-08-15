package com.lmt.op.action;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmt.common.action.BaseAction;
import com.lmt.op.model.Server;
import com.lmt.op.service.IServerService;
import com.lmt.orm.common.model.PaginationModel;

/**
 * 
 * @author ducx
 * @date 2017-07-29
 *
 */
@Controller
@RequestMapping(value="/op/server")
public class ServerAction extends BaseAction {
	
	@Resource
	private IServerService serverService;

	@RequestMapping(value="/list",name="获取服务器列表")
	public String list(
			@RequestParam(value="currPage",defaultValue="1") int currPage,
			@RequestParam(value="limit",defaultValue="10") int limit,
			HttpServletRequest request,HttpServletResponse response){
		PaginationModel<Server> pageModel = new PaginationModel<Server>();
		pageModel.setCurrPage(currPage);
		pageModel.setLimit(limit);
		Server t = new Server();
		pageModel.setT(t);
		serverService.list(pageModel);
		request.setAttribute("pageModel", pageModel);
		return "server/list";
	}
	
	@RequestMapping(value="/edit",name="修改服务器信息")
	public String edit(
			@RequestParam(value="id",defaultValue="0") int id,
			HttpServletRequest request,HttpServletResponse response){
		if(id > 0){
			Server server = serverService.get(id);
			request.setAttribute("server", server);
		}
		return "server/edit";
	}
	
	@RequestMapping(value="/save",name="保存服务器信息")
	public void save(
			Server server,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		if(server.getId() == null || server.getId().intValue() <= 0){
			server.setStatus(1);
			serverService.insert(server);
		}else{
			serverService.update(server);
		}
		serverService.flushIpCache();
		response.sendRedirect("list");
	}
	
}
