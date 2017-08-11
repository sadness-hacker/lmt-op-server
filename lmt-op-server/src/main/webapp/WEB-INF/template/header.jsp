<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header class="main-header">
  <!-- Logo -->
  <a href="index" class="logo">
    <!-- mini logo for sidebar mini 50x50 pixels -->
    <span class="logo-mini"><b>运</b>维</span>
    <!-- logo for regular state and mobile devices -->
    <span class="logo-lg"><b>运维</b>发布平台</span>
  </a>
  <!-- Header Navbar: style can be found in header.less -->
  <nav class="navbar navbar-static-top">
    <!-- Sidebar toggle button-->
    <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
      <span class="sr-only">Toggle navigation</span>
    </a>

    <div class="navbar-custom-menu">
      <ul class="nav navbar-nav">
      	<li class="dropdown">
      		<a class="dropdown-toggle" href="${ctx }/logout" target="_blank"><i class="fa fa-sign-out"></i>退出</a>
      	</li>
        <!-- User Account: style can be found in dropdown.less -->
        <li class="dropdown user user-menu">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">
            <img src="${ctx }/${curr_user.headImgUrl}" class="user-image" alt="用户头像">
            <span class="hidden-xs">${curr_user.username }</span>
          </a>
          <ul class="dropdown-menu">
          	<!-- User image -->
            <li class="user-header">
              <img src="${ctx }/${curr_user.headImgUrl}" class="img-circle" alt="用户头像">

              <p>
                ${curr_user.username } - 管理员
                <small>注册时间： 2011.01.01</small>
              </p>
            </li>
            <!-- Menu Footer-->
            <li class="user-footer">
              <div class="pull-left">
                <a href="${ctx }/op/user/edit?id=${curr_user.id}" class="btn btn-default btn-flat">个人资料</a>
              </div>
              <div class="pull-right">
                <a href="${ctx }/logout" class="btn btn-default btn-flat">退出</a>
              </div>
            </li>
          </ul>
        </li>
        <!-- Control Sidebar Toggle Button -->
        <li>
          <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
        </li>
      </ul>
    </div>
  </nav>
</header>