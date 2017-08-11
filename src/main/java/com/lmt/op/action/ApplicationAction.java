package com.lmt.op.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmt.common.action.BaseAction;
import com.lmt.op.model.Application;
import com.lmt.op.service.IApplicationService;
import com.lmt.op.util.TypeUtil;
import com.lmt.orm.common.model.PaginationModel;

/**
 * 
 * @author ducx
 * @date 2017-07-30
 *
 */
@Controller
@RequestMapping(value="/op/application")
public class ApplicationAction extends BaseAction{
	
	@Resource
	private IApplicationService applicationService;

	@RequestMapping(value="/list",name="后台获取应用列表")
	public String list(
			@RequestParam(value="currPage",defaultValue="1") int currPage,
			@RequestParam(value="limit",defaultValue="10") int limit,
			HttpServletRequest request,HttpServletResponse response){
		PaginationModel<Application> pageModel = new PaginationModel<Application>();
		pageModel.setCurrPage(currPage);
		pageModel.setLimit(limit);
		applicationService.list(pageModel);
		request.setAttribute("pageModel", pageModel);
		request.setAttribute("appTypeMap", TypeUtil.getAppTypeMap());
		return "app/list";
	}
	
	@RequestMapping(value="/edit",name="获取编辑应用页面")
	public String edit(
			@RequestParam(value="id",defaultValue="0") int id,
			HttpServletRequest request,HttpServletResponse response){
		if(id > 0){
			Application app = applicationService.get(id);
			request.setAttribute("app", app);
		}
		request.setAttribute("appTypeMap", TypeUtil.getAppTypeMap());
		return "app/edit";
	}
	
	@RequestMapping(value="/save",name="后台添加、修改保存应用")
	public void save(
			Application app,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		if(app.getId() == null || app.getId() < 1){
			if(app.getStatus() == null){
				app.setStatus(1);
			}
			applicationService.insert(app);
		}else{
			applicationService.update(app);
		}
		response.sendRedirect("list");
	}
	
}
