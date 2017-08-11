<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
request.setAttribute("serverManageActive", "active");
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
        服务器
        <small>服务器编辑</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="${ctx }/admin/index"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx }/admin/user/list"><i class="fa fa-server"></i> 服务器</a></li>
        <li class="active"><i class="fa fa-edit"></i> 服务器编辑</li>
      </ol>
    </section>
    <!-- Main content -->
    <section class="content">
      <div class="box box-danger">
         <!-- /.box-header -->
         <!-- form start -->
         <form class="form-horizontal" id="save-form" method="post" onsubmit="return checkForm()" data-toggle="validator" autocomplete="off" target="_self">
           <input type="hidden" name="id" value="${server.id }" />
           <input type="hidden" name="status" value="1" />
           <div class="box-body">
           	<div class="form-group">
               <label for="username" class="col-sm-2 control-label">服务器名</label>
               <div class="col-sm-10">
                 <input type="text" class="form-control" value="${server.name }" name="name" id="name" placeholder="请输服务器名" required="required">
               </div>
             </div>
             <div class="form-group">
               <label for="inputPassword3" class="col-sm-2 control-label">外网ip</label>
               <div class="col-sm-10">
                 <input type="text" name="outIp" class="form-control" id="inputPassword3" placeholder="请输入外网ip" value="${server.outIp }">
               </div>
             </div>
           	 <div class="form-group">
               <label for="inputEmail3" class="col-sm-2 control-label">内网ip</label>
               <div class="col-sm-10">
                 <input type="text" name="innerIp" class="form-control" id="inputEmail3" placeholder="请输入内网ip" value="${server.innerIp }">
               </div>
             </div>
             <div class="form-group">
			  <label for="onlineTime" class="col-sm-2 control-label">上线时间</label>
			  <div class="col-sm-10">
			    <input name="onlineTime" type="date" class="form-control" id="onlineTime" value="<fmt:formatDate value="${server.onlineTime }" pattern="yyyy-MM-dd"/>" placeholder="请输入上线时间" required="required">
			  </div>
		  	</div>
             <div class="form-group">
               <label for="nickname" class="col-sm-2 control-label">到期时间</label>
               <div class="col-sm-10">
                 <input type="date" class="form-control" value="<fmt:formatDate value="${server.endTime }" pattern="yyyy-MM-dd"/>" name="endTime" id="nickname" placeholder="请输入到期时间" required="required">
               </div>
             </div>
            <div class="form-group">
			  <label for="signName" class="col-sm-2 control-label">配置情况</label>
			  <div class="col-sm-10">
			  	<textarea name="config" type="text" class="form-control" id="signName" placeholder="请输入服务器配置" required="required">${server.config }</textarea>
			  </div>
		  	</div>
		  	<div class="form-group">
			  <label for="qq" class="col-sm-2 control-label">描述</label>
			  <div class="col-sm-10">
			  	<textarea name="description" type="text" class="form-control" id="qq" placeholder="请输入服务器描述" required="required">${server.description }</textarea>
			  </div>
		  	</div>
		  	<div class="form-group">
			  	<label for="submit" class="col-sm-2 control-label"></label>
			  	<div class="col-sm-10" style="text-align: center;">
					<input value="保存" type="submit" class="btn btn-success btn-flat" style="width:15%;" onclick="$('#save-form').attr('action','${ctx}/op/server/save');">
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
<!-- <script type="text/javascript" src="http://shgres.oss-cn-shanghai.aliyuncs.com/js/My97DatePicker/WdatePicker.js"></script> -->
<!-- <script type="text/javascript" src="http://shgres.oss-cn-shanghai.aliyuncs.com/js/layer/layer.js"></script> -->
<script type="text/javascript" charset="utf-8" src="${ctx }/js/validator.min.js"></script>
<script>
function checkForm(){
	
	return true;
}
</script>
</body>
</html>
