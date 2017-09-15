<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <small>版本列表</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="${ctx }/op/index"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">版本列表</li>
      </ol>
    </section>
    <!-- Main content -->
    <section class="content">
      <div class="box box-danger">
      	<div class="box-header with-border">
	      	<form action="" id="ducx-query-form">
	         	<input type="hidden" name="currPage" value="1" id="ducx-query-curr-page">
	         	<input type="hidden" name="pageSize" value="10">
	            <label>环境：</label>
	            <select name="envType">
	            	<option></option>
	            	<c:forEach var="e" items="${envTypeMap }">
	            	<option value="${e.key }"<c:if test="${param.envType eq e.key }"> selected="selected"</c:if>>${e.value }</option>
	            	</c:forEach>
	            </select>
	            <label>应用：</label>
	            <select name="appId">
	            	<option></option>
	            	<c:forEach var="a" items="${appList }">
	            	<option value="${a.id }"<c:if test="${param.appId eq a.id }"> selected="selected"</c:if>>${a.name }</option>
	            	</c:forEach>
	            </select>
	            <button type="submit" class="btn btn-default btn-flat" style="margin-right: 10px;"><i class="fa fa-search"></i> 查寻</button>
		         	<a href="${ctx }/op/version/add" class="btn btn-info btn-flat">
	         		<i class="fa fa-plus-circle"></i> 添加
	         	</a>
	         </form>
         </div>
         <!-- /.box-header -->
         <div class="box-body">
           <table class="table table-bordered">
             <tbody>
             <tr>
               <th style="width: 45px">编号</th>
               <th>版本号</th>
               <th>应用</th>
               <th>版本类型</th>
               <th>文件大小</th>
               <th>更新内容</th>
               <th>添加时间</th>
               <th>添加人</th>
               <th>状态</th>
               <th>操作</th>
             </tr>
           <c:forEach var="a" items="${pageModel.list }">
             <tr>
               <td>${a.id }</td>
               <td><label>${a.versionNum }</label></td>
               <td>${a.appName }</td>
               <td>${envTypeMap[a.type] }</td>
               <td>
               <fmt:parseNumber integerOnly="true" value="${a.fileSize / 1024 / 1024 }" var="size" />
               <c:if test="${size > 0 }">
               	 <fmt:formatNumber value="${a.fileSize / 1024 / 1024 }" maxFractionDigits="2" />MB
               </c:if>
               <c:if test="${size == 0 }">
               	<fmt:parseNumber integerOnly="true" value="${a.fileSize / 1024 }" />KB
               </c:if>
               </td>
               <td>${a.description }</td>
               <td><fmt:formatDate value="${a.addTime }" pattern="yyyy-MM-dd"/><br/><fmt:formatDate value="${a.addTime }" pattern="HH:mm:ss"/></td>
               <td>${a.addUser }</td>
               <td>
               	<c:choose>
               	  <c:when test="${a.status == 1 }">
               	  	<span>待发布</span>
               	  </c:when>
               	  <c:when test="${a.status == 0 }">
               	  	<span style="color:red;">已删除</span>
               	  </c:when>
               	  <c:when test="${a.status == 2 }">
               	  	<span style="color:green;">已发布</span>
               	  </c:when>
               	  <c:otherwise>
               	  	未知
               	  </c:otherwise>
               	</c:choose>
               </td>
               <td>
                <c:choose>
               	  <c:when test="${a.status == 1 }">
               	  	<a href="${ctx }/op/version/deploy?id=${a.id}&type=batchDeploy" class="btn btn-xs btn-success btn-flat">批量发布</a><br/>
               	 	<a href="${ctx }/op/version/deploy?id=${a.id}&type=signleDeploy" class="btn btn-xs btn-info btn-flat">单个发布</a>
               	  </c:when>
               	  <c:when test="${a.status == 0 }">
               	  	<span style="color:red;">已删除</span>
               	  </c:when>
               	  <c:when test="${a.status == 2 }">
               	  	<a href="${ctx }/op/version/logPage?id=${a.id}" class="btn btn-xs btn-success btn-flat">查看日志</a>
               	  </c:when>
               	  <c:otherwise>
               	  	未知
               	  </c:otherwise>
               	</c:choose>
               </td>
             </tr>
           </c:forEach>
           </tbody>
          </table>
         </div>
         <!-- /.box-body -->
         <div class="box-footer clearfix">
           <ul class="pagination pagination-sm no-margin pull-right">
           <c:if test="${pageModel.currPage > 1 }">
           	 <li><a href="javascript:void(0);" onclick="$('#ducx-query-curr-page').val(1);$('#ducx-query-form').submit();">首页</a></li>
             <li><a href="javascript:void(0);" onclick="$('#ducx-query-curr-page').val(${pageModel.currPage - 1});$('#ducx-query-form').submit();">«</a></li>
           </c:if>
           <c:forEach var="page" begin="1" end="${pageModel.totalPage}" varStatus="status">
           	  <li <c:if test="${page == pageModel.currPage }">class="active"</c:if>><a href="javascript:void(0);" onclick="$('#ducx-query-curr-page').val(${page});$('#ducx-query-form').submit();">${page}</a></li>
           </c:forEach>
           <c:if test="${pageModel.currPage < pageModel.totalPage }">
             <li><a href="javascript:void(0)" onclick="$('#ducx-query-curr-page').val(${pageModel.currPage + 1});$('#ducx-query-form').submit();">»</a></li>
             <li><a href="javascript:void(0)" onclick="$('#ducx-query-curr-page').val(${pageModel.totalPage});$('#ducx-query-form').submit();">尾页</a></li>
           </c:if>
           </ul>
         </div>
       </div>
    </section>
    <!-- /.content -->
  </div>
<%@ include file="../footer.jsp" %>
</div>

<%@ include file="../bottom.jsp" %>
</body>
</html>
