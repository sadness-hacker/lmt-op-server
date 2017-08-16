<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
request.setAttribute("userManageActive", "active");
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
        用户管理
        <small>用户编辑</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="${ctx }/admin/index"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx }/admin/user/list"><i class="fa fa-user-secret"></i> 用户列表</a></li>
        <li class="active"><i class="fa fa-edit"></i> 用户编辑</li>
      </ol>
    </section>
    <!-- Main content -->
    <section class="content">
      <div class="box box-danger">
         <!-- /.box-header -->
         <!-- form start -->
         <form class="form-horizontal" id="save-form" method="post" onsubmit="return checkForm()" data-toggle="validator" autocomplete="off">
           <input type="hidden" name="id" value="${user.id }" />
           <input type="hidden" name="status" value="${user.status }" />
           <div class="box-body">
           	<div class="form-group">
               <label for="username" class="col-sm-2 control-label">用户名</label>
               <div class="col-sm-10">
                 <input type="text" class="form-control" value="${user.username }" name="username" id="username" placeholder="请输用户名" required="required" autocomplete="off">
               </div>
             </div>
             <div class="form-group">
               <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
               <div class="col-sm-10">
                 <input type="password" name="password" class="form-control" id="inputPassword3" placeholder="请输入密码" autocomplete="off">
               </div>
             </div>
           	 <div class="form-group">
               <label for="inputEmail3" class="col-sm-2 control-label">邮箱</label>
               <div class="col-sm-10">
                 <input type="email" name="email" class="form-control" id="inputEmail3" placeholder="请输入邮箱地址" value="${user.email }">
               </div>
             </div>
             <div class="form-group">
			  <label for="phone" class="col-sm-2 control-label">手机号</label>
			  <div class="col-sm-10">
			    <input name="phone" type="text" class="form-control" id="phone" value="${user.phone }" placeholder="请输入手机号" required="required">
			  </div>
		  	</div>
		  	<div class="form-group">
			  <label for="headImgUrl" class="col-sm-2 control-label">头像</label>
			  <div class="col-sm-10">
			    <input name="headImgUrl" type="text" class="form-control" id="headImgUrl" value="${user.headImgUrl }" placeholder="请上传用户头像" required="required" style="width:90%;display: inline-block;">
			    <input type="button" class="btn btn-default btn-flat" value="上传" style="width:9%;" onclick="autoUpload('upfile','${ctx}/upload',uploadCallback,'headImgUrl','.png,.jpeg,.jpg,.bmp,.gif')">
			  </div>
		  	</div>
		  	<div class="form-group">
			  <label for="brief" class="col-sm-2 control-label">用户简介</label>
			  <div class="col-sm-10">
			    <textarea name="brief" type="text" class="form-control" id="brief" value="${user.brief }" placeholder="请输用户简介" required="required">${user.brief}</textarea>
			  </div>
		  	</div>
		  	<c:if test="${user == null || fn:indexOf(curr_user.privileges,'userManageActive') > -1}">
		  	<div class="form-group">
			  <label for="brief" class="col-sm-2 control-label">用户权限</label>
			  <div class="col-sm-10">
			    <div class="form-group">
                  <div class="checkbox">
                    <label>
                      <input type="checkbox" name="privileges" value="dashboardManageActive"<c:if test="${fn:indexOf(user.privileges,'dashboardManageActive') > -1 }"> checked="checked"</c:if>>Dashboard
                    </label>
                    <label>
                      <input type="checkbox" name="privileges" value="serverManageActive"<c:if test="${fn:indexOf(user.privileges,'serverManageActive') > -1  }"> checked="checked"</c:if>>服务器
                    </label>
                    <label>
                      <input type="checkbox" name="privileges" value="applicationManageActive"<c:if test="${fn:indexOf(user.privileges,'applicationManageActive') > -1  }"> checked="checked"</c:if>>应用管理
                    </label>
                    <label>
                      <input type="checkbox" name="privileges" value="serverApplicationManageActive"<c:if test="${fn:indexOf(user.privileges,'serverApplicationManageActive') > -1  }"> checked="checked"</c:if>>应用服务
                    </label>
                    <label>
                      <input type="checkbox" name="privileges" value="configManageActive"<c:if test="${fn:indexOf(user.privileges,'configManageActive') > -1  }"> checked="checked"</c:if>>配置文件
                    </label>
                    <label>
                      <input type="checkbox" name="privileges" value="versionManageActive"<c:if test="${fn:indexOf(user.privileges,'versionManageActive') > -1  }"> checked="checked"</c:if>>版本管理
                    </label>
                    <label>
                      <input type="checkbox" name="privileges" value="deployLogManageActive"<c:if test="${fn:indexOf(user.privileges,'deployLogManageActive') > -1  }"> checked="checked"</c:if>>发布历史
                    </label>
                    <label>
                      <input type="checkbox" name="privileges" value="userManageActive"<c:if test="${fn:indexOf(user.privileges,'userManageActive') > -1 }"> checked="checked"</c:if>>用户管理
                    </label>
                  </div>
                </div>
			  </div>
		  	</div>
		  	</c:if>
		  	<div class="form-group">
			  	<label for="submit" class="col-sm-2 control-label"></label>
			  	<div class="col-sm-10" style="text-align: center;">
					<input value="保存" type="submit" class="btn btn-success btn-flat" style="width:15%;" onclick="$('#save-form').attr('action','${ctx}/op/user/save');$('#save-form').attr('target','_self');">
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

<script type="text/javascript" charset="utf-8" src="${ctx }/js/jquery.form.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx }/js/upload.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx }/js/validator.min.js"></script>
<script>
function uploadCallback(result,id){
	$("#" + id).val(result.relativeUrl);
}
function checkForm(){
	
	return true;
}
</script>
</body>
</html>
