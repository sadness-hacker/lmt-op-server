<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
if(request.getParameter("deplogId") != null){
	request.setAttribute("deployLogManageActive", "active");
}else{
	request.setAttribute("versionManageActive", "active");
}
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>运维发布平台-后台管理</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<%@include file="../top.jsp" %>
</head>
<body class="hold-transition skin-red-light sidebar-mini">
<div class="wrapper">
<%@include file="../header.jsp" %>
<%@include file="../aside.jsp" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        版本管理
        <small>发布日志</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="${ctx }/op/index"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx }/op/version/list"><i class="fa fa-upload"></i> 版本列表</a></li>
        <li class="active"><i class="fa fa-plus"></i> 版本日志</li>
      </ol>
    </section>
    <!-- Main content -->
    <section class="content">
      <div class="box box-danger">
         <!-- /.box-header -->
		<c:forEach var="log" items="${list }">
		  <div>
		  	<label>版本号：</label>${log.versionNum } 
		  	<label>版本类型：</label>${log.versionType } 
		  	<label>服务器名：</label>${log.serverName } 
		  	<label>应用名：</label>
		  	${log.appName }
		  </div>
		  <div>
		  	${log.description }
		  </div>
		</c:forEach>
       </div>
    </section>
    <!-- /.content -->
  </div>
<%@ include file="../footer.jsp" %>
</div>

<%@ include file="../bottom.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctx }/js/validator.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx }/js/jquery.form.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx }/js/upload.js"></script>
<script>
function uploadCallback(result,id){
	$("#" + id).val(result.relativeUrl);
}
function checkForm(){
	var appName = $("#appId").find("option:selected").text();
	$("#appName").val(appName);
	return true;
}
</script>
</body>
</html>
