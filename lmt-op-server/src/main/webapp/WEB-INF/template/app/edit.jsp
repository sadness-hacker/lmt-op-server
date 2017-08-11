<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
request.setAttribute("applicationManageActive", "active");
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
        应用管理
        <small>应用编辑</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="${ctx }/op/index"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx }/op/app/list"><i class="fa fa-font"></i> 应用列表</a></li>
        <li class="active"><i class="fa fa-edit"></i> 应用编辑</li>
      </ol>
    </section>
    <!-- Main content -->
    <section class="content">
      <div class="box box-danger">
         <!-- /.box-header -->
         <!-- form start -->
         <form class="form-horizontal" id="save-form" method="post" onsubmit="return checkForm()" data-toggle="validator" autocomplete="off" target="_self">
           <input type="hidden" name="id" value="${app.id }" />
           <input type="hidden" name="status" value="${app.status }" />
           <div class="box-body">
           	<div class="form-group">
               <label for="username" class="col-sm-2 control-label">应用名</label>
               <div class="col-sm-10">
                 <input type="text" class="form-control" value="${app.name }" name="name" id="username" placeholder="请输应用名" required="required">
               </div>
             </div>
             <div class="form-group">
               <label for="inputEmail3" class="col-sm-2 control-label">应用类型</label>
               <div class="col-sm-10">
                 <select name="type" class="form-control" id="inputEmail3">
               	   <c:forEach var="e" items="${appTypeMap }">
               	   		<option value="${e.key }" <c:if test="${app.type eq e.key }">selected="selected"</c:if>>${e.value }</option>
               	   </c:forEach>
               	 </select>
               </div>
             </div>
             <div class="form-group">
               <label for="inputPassword3" class="col-sm-2 control-label">描述</label>
               <div class="col-sm-10">
               	 <textarea name="description" class="form-control" id="inputPassword3" placeholder="请输入应用描述" required="required">${app.description }</textarea>
               </div>
             </div>
		  	<div class="form-group">
			  	<label for="submit" class="col-sm-2 control-label"></label>
			  	<div class="col-sm-10" style="text-align: center;">
					<input value="保存" type="submit" class="btn btn-success btn-flat" style="width:15%;" onclick="$('#save-form').attr('action','${ctx}/op/application/save');">
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
	
	return true;
}
</script>
</body>
</html>
