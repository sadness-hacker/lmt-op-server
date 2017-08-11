<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
request.setAttribute("versionManageActive", "active");
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
        <small>添加版本</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="${ctx }/op/index"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx }/op/version/list"><i class="fa fa-upload"></i> 版本列表</a></li>
        <li class="active"><i class="fa fa-plus"></i> 版本添加</li>
      </ol>
    </section>
    <!-- Main content -->
    <section class="content">
      <div class="box box-danger">
         <!-- /.box-header -->
         <!-- form start -->
         <form class="form-horizontal" id="save-form" method="post" onsubmit="return checkForm()" data-toggle="validator" autocomplete="off" target="_self">
           <input type="hidden" name="status" value="1" />
           <input type="hidden" name="appName" value="" id="appName"/>
           <div class="box-body">
           	<div class="form-group">
               <label for="inputEmail3" class="col-sm-2 control-label">应用名</label>
               <div class="col-sm-10">
                 <select name="appId" class="form-control" id="appId">
               	   <c:forEach var="app" items="${appList }">
               	   		<option value="${app.id }">${app.name }</option>
               	   </c:forEach>
               	 </select>
               </div>
             </div>
             <div class="form-group">
               <label for="inputEmail3" class="col-sm-2 control-label">版本类型</label>
               <div class="col-sm-10">
                 <select name="type" class="form-control" id="inputEmail3">
               	   <c:forEach var="e" items="${envTypeMap }">
               	   		<option value="${e.key }" <c:if test="${e.key eq 'product' }">selected="selected"</c:if>>${e.value }</option>
               	   </c:forEach>
               	 </select>
               </div>
             </div>
             <div class="form-group">
			  <label for="restart" class="col-sm-2 control-label">是否重启</label>
			  <div class="col-sm-10">
			    <select name="restart" class="form-control" id="restart">
               	   <option value="1">是</option>
               	   <option value="0">否</option>
               	 </select>
			  </div>
		  	 </div>
             <div class="form-group">
			  <label for="imageUrl" class="col-sm-2 control-label">更新包</label>
			  <div class="col-sm-10">
			    <input name="filePath" type="text" class="form-control" id="imageUrl" value="" placeholder="请上传更新包" style="width:80%;display: inline-block;" required>
			    <input type="button" class="btn btn-default btn-flat" value="上传" style="width:9%;" onclick="autoUpload('upfile','${ctx}/upload',uploadCallback,'imageUrl','.zip,.jar')">
			    <input type="button" class="btn btn-default btn-flat" value="浏览" style="width:9%;" onclick="blowseFtp()">
			  </div>
		  	</div>
             <div class="form-group">
               <label for="inputPassword3" class="col-sm-2 control-label">描述</label>
               <div class="col-sm-10">
               	 <textarea name="description" class="form-control" id="inputPassword3" placeholder="请输入本次更新描述" required="required"></textarea>
               </div>
             </div>
		  	<div class="form-group">
			  	<label for="submit" class="col-sm-2 control-label"></label>
			  	<div class="col-sm-10" style="text-align: center;">
					<input value="保存" type="submit" class="btn btn-success btn-flat" style="width:15%;" onclick="$('#save-form').attr('action','${ctx}/op/version/save');">
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
<script type="text/javascript" src="http://shgres.oss-cn-shanghai.aliyuncs.com/js/layer/layer.js"></script>
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

String.prototype.endWith=function(str){
	if(str == null || str == "" || this.length == 0 || str.length > this.length)
	    return false;
	if(this.substring(this.length - str.length) == str)
	    return true;
	else
	    return false;
	return true;
}
	
function blowseFtp(){
	var url = "${ctx}/op/ftp/list?path=/";
	$.get(url,function(html){
		html = "<div id='ftp-div'>" + html + "</div>";
		layer.open({
			type: 1,
			title : 'FTP文件浏览',
			area: ['420px', '240px'], //宽高
			content: html,
			btn : ['确定','取消'],
			btn1:function(){
				$(".ftp-li-active").each(function(){
			    	var name = $(this).data('name');
			    	var path = $(this).data('path');
			    	if('/' == path){
			    		path = path + name;
			    	}else{
			    		path = path + "/" + name;
			    	}
			    	if(name.toLowerCase().endWith('.zip') || name.toLowerCase().endWith('.jar')){
				    	var url1 = "${ctx}/op/ftp/download?path=" + path;
				    	$.get(url1,function(data){
				    		$("#imageUrl").val(data.relativeUrl);
				    		layer.closeAll();
				    	},'json');
			    	}else{
			    		alert("不支持的文件类型，请选择.zip/.jar文件");
			    		return false;
			    	}
			    });
			},
			btn2:function(){
				layer.closeAll();
			}
		});
	});
	
}

function nextPath(o){
	var name = $(o).data('name');
	var path = $(o).data('path');
	var isDirectory = $(o).data('isdirectory');
	if('/' == path){
		path = path + name;
	}else{
		path = path + "/" + name;
	}
	if(isDirectory){
		var url = "${ctx}/op/ftp/list?path=" + path;
		$.get(url,function(html){
			$("#ftp-div").html(html);
		});
	}else{
		$('.ftp-file-li').removeClass('ftp-li-active');
		$(o).addClass('ftp-li-active');
	}
}

</script>
</body>
</html>
