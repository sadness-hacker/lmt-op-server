<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
request.setAttribute("configManageActive", "active");
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
        配置管理
        <small>配置编辑</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="${ctx }/op/index"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx }/op/config/list"><i class="fa fa-file"></i> 配置列表</a></li>
        <li class="active"><i class="fa fa-edit"></i> 配置编辑</li>
      </ol>
    </section>
    <!-- Main content -->
    <section class="content">
      <div class="box box-danger">
         <!-- /.box-header -->
         <!-- form start -->
         <form class="form-horizontal" id="save-form" method="post" onsubmit="return checkForm()" data-toggle="validator" autocomplete="off" target="_self">
           <input type="hidden" name="id" value="${config.id }" />
           <input type="hidden" name="status" value="${config.status }" />
           <input type="hidden" name="appName" value="${config.appName }" id="appName"/>
           <div class="box-body">
           	<div class="form-group">
               <label for="username" class="col-sm-2 control-label">配置名</label>
               <div class="col-sm-10">
                 <input type="text" class="form-control" value="${config.name }" name="name" id="username" placeholder="请输配置名" required="required">
               </div>
             </div>
             <div class="form-group">
               <label for="inputEmail3" class="col-sm-2 control-label">配置类型</label>
               <div class="col-sm-10">
                 <select name="envType" class="form-control" id="inputEmail3">
               	   <c:forEach var="e" items="${envTypeMap }">
               	   		<option value="${e.key }" <c:if test="${config.envType eq e.key }">selected="selected"</c:if>>${e.value }</option>
               	   </c:forEach>
               	 </select>
               </div>
             </div>
             <div class="form-group">
               <label for="inputEmail3" class="col-sm-2 control-label">应用名</label>
               <div class="col-sm-10">
                 <select name="appId" class="form-control" id="appId">
               	   <c:forEach var="app" items="${appList }">
               	   		<option value="${app.id }" <c:if test="${config.appId eq app.id }">selected="selected"</c:if>>${app.name }</option>
               	   </c:forEach>
               	 </select>
               </div>
             </div>
             <div class="form-group">
               <label for="path" class="col-sm-2 control-label">文件路径</label>
               <div class="col-sm-10">
                 <input type="text" class="form-control" value="${config.path }" name="path" id="path" placeholder="请输配置文件相对WebContent路径" required="required">
               </div>
             </div>
             <div class="form-group">
               <label for="inputPassword3" class="col-sm-2 control-label">配置内容</label>
               <div class="col-sm-10">
               	 <textarea name="content" class="form-control" id="inputPassword3" placeholder="请输入配置文件内容" required="required">${config.content }</textarea>
               </div>
             </div>
		  	<div class="form-group">
			  	<label for="submit" class="col-sm-2 control-label"></label>
			  	<div class="col-sm-10" style="text-align: center;">
					<input value="保存" type="submit" class="btn btn-success btn-flat" style="width:15%;" onclick="$('#save-form').attr('action','${ctx}/op/config/save');">
				</div>
			</div>
         </form>
       </div>
    </section>
    <!-- /.content -->
  </div>
<%@ include file="../footer.jsp" %>
</div>

<%@ include file="../bottom.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctx }/js/validator.min.js"></script>
<script>
function checkForm(){
	var appName = $("#appId").find("option:selected").text();
	$("#appName").val(appName);
	return true;
}
</script>
</body>
</html>
