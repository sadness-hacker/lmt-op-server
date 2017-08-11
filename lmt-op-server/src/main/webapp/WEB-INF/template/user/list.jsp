<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
request.setAttribute("userManageActive", "active");
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>悲情黑客的博客-后台管理</title>
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
        用户列表
        <small>后台管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="${ctx }/admin/index"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">用户列表</li>
      </ol>
    </section>
    <!-- Main content -->
    <section class="content">
      <div class="box box-danger">
      	<div class="box-header with-border">
         	<a href="${ctx }/op/user/edit" class="btn btn-info btn-flat">
         		<i class="fa fa-plus-circle"></i> 添加
         	</a>
         </div>
         <!-- /.box-header -->
         <div class="box-body">
           <table class="table table-bordered">
             <tbody>
             <tr>
               <th style="width: 45px">编号</th>
               <th>用户名</th>
               <th>邮箱</th>
               <th>手机号</th>
               <th>状态</th>
               <th>操作</th>
             </tr>
           <c:forEach var="u" items="${pageModel.list }">
             <tr>
               <td>${u.id }</td>
               <td><label>${u.username }</label>
               <td>${u.email }</td>
               <td>${u.phone }</td>
               <td>
               	<c:choose>
               	  <c:when test="${u.status == 1 }">
               	  	<span style="color:green;">正常</span>
               	  </c:when>
               	  <c:when test="${u.status == 0 }">
               	  	<span style="color:red;">已删除</span>
               	  </c:when>
               	  <c:otherwise>
               	  	未知
               	  </c:otherwise>
               	</c:choose>
               	</td>
               <td>
               	 <a href="${ctx }/op/user/edit?id=${u.id}" class="btn btn-xs btn-info btn-flat">修改</a>
               </td>
             </tr>
           </c:forEach>
           </tbody>
          </table>
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
