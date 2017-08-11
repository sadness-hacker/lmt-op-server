package com.lmt.op.action;

import java.io.IOException;
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
import com.lmt.common.util.DateUtil;
import com.lmt.op.model.DeployLog;
import com.lmt.op.service.IDeployLogService;
import com.lmt.op.util.TypeUtil;
import com.lmt.orm.common.model.PaginationModel;

/**
 * 
 * @author ducx
 * @date 2017-07-31
 *
 */
@Controller
@RequestMapping(value="/op/deployLog")
public class DeployLogAction extends BaseAction {
	
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
	@RequestMapping(value="/list",name="后台获取发布历史记录")
	public String list(
			@RequestParam(value="currPage",defaultValue="1") int currPage,
			@RequestParam(value="limit",defaultValue="10") int limit,
			HttpServletRequest request,HttpServletResponse response){
		PaginationModel<DeployLog> pageModel = new PaginationModel<DeployLog>();
		pageModel.setCurrPage(currPage);
		pageModel.setLimit(limit);
		deployLogService.list(pageModel);
		request.setAttribute("pageModel", pageModel);
		request.setAttribute("envTypeMap", TypeUtil.getEnvTypeMap());
		return "deployLog/list";
	}
	
	@RequestMapping(value="/rollback",name="后台回滚历史记录")
	public void rollback(
			@RequestParam(value="id") int id,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		DeployLog dl = deployLogService.get(id);
		dl.setStatus(4);
		dl.setCmd("rollback");
		dl.setRollbackTime(new Date());
		dl.setRollbackUser("");
		dl.setUpdateTime(dl.getRollbackTime());
		dl.setDescription(dl.getDescription() + "<br/>" + DateUtil.format(new Date()) + "开始回滚...");
		deployLogService.update(dl);
		response.sendRedirect("list");
	}
	
	@RequestMapping(value="/reportRollbackStatus",name="报告回滚状态")
	public void reportRollbackStatus(
			@RequestParam(value="id") int id,
			@RequestParam(value="rollbackStatus") int rollbackStatus,
			@RequestParam(value="log") String log,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		DeployLog dl = deployLogService.get(id);
		dl.setRollbackStatus(rollbackStatus);
		dl.setDescription(dl.getDescription() + "<br/>" + log);
		deployLogService.update(dl);
		response.sendRedirect("list");
	}
	
	@RequestMapping(value="/getRollbackLog",name="后台获取回滚日志")
	@ResponseBody
	public Map<String, Object> getRollbackLog(
			@RequestParam(value="id") int id,
			HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String, Object>();
		
		return map;
	}
	
	/**
	 * 
	 * id=$id&status=2&log=$time下载更新包结束！
	 * @return
	 */
	@RequestMapping(value="/log",name="记录发布日志")
	@ResponseBody
	public Map<String,Object> log(
			@RequestParam(value="id") int id,
			@RequestParam(value="status") int status,
			@RequestParam(value="log") String log,
			HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String, Object>();
		DeployLog dl = deployLogService.get(id);
		String s = dl.getDescription() == null ? "" : dl.getDescription();
		if(!s.endsWith(log)){
			dl.setStatus(status);
			dl.setUpdateTime(new Date());
			dl.setDescription(s + "<br/>" + log);
			deployLogService.update(dl);
		}
		map.put("success", true);
		map.put("msg", "日志添加成功");
		return map;
	}
	
	@RequestMapping(value="/bakVersion",name="记录备份版本")
	@ResponseBody
	public Map<String, Object> bakVersion(
			@RequestParam(value="id") int id,
			@RequestParam(value="bakVersion") String bakVersion,
			HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String, Object>();
		DeployLog dl = deployLogService.get(id);
		dl.setBakVersion(bakVersion);
		dl.setUpdateTime(new Date());
		deployLogService.update(dl);
		map.put("success", true);
		map.put("msg", "备份记录成功");
		return map;
	}
	
	@RequestMapping(value="/deploy",name="开发发布")
	public void deploy(
			@RequestParam(value="id") int id,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		DeployLog dl = deployLogService.get(id);
		dl.setStatus(2);
		dl.setDescription("开始发布...");
		deployLogService.update(dl);
		response.sendRedirect("list");
	}
	
}
