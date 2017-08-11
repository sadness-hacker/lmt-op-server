<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
  <!-- sidebar: style can be found in sidebar.less -->
  <section class="sidebar">
    <!-- sidebar menu: : style can be found in sidebar.less -->
    <ul class="sidebar-menu">
      <li class="header">主菜单</li>
      <c:if test="${fn:indexOf(curr_user.privileges,'dashboardManageActive') > -1 }">
      <li class="${dashboardManageActive } treeview">
          <a href="${ctx }/op/index"><i class="fa fa-dashboard"></i> <span>Dashboard</span></a>
      </li>
      </c:if>
      <c:if test="${fn:indexOf(curr_user.privileges,'serverManageActive') > -1 }">
      <li class="${serverManageActive } treeview">
        <a href="${ctx }/op/server/list"><i class="fa fa-server"></i> <span>服务器</span></a>
      </li>
      </c:if>
      <c:if test="${fn:indexOf(curr_user.privileges,'applicationManageActive') > -1 }">
      <li class="${applicationManageActive } treeview">
        <a href="${ctx }/op/application/list"><i class="fa fa-font"></i> <span>应用管理</span></a>
      </li>
      </c:if>
      <c:if test="${fn:indexOf(curr_user.privileges,'serverApplicationManageActive') > -1 }">
      <li class="${serverApplicationManageActive } treeview">
        <a href="${ctx }/op/serverApplication/list"><i class="fa fa-rocket"></i> <span>应用服务</span></a>
      </li>
      </c:if>
      <c:if test="${fn:indexOf(curr_user.privileges,'configManageActive') > -1 }">
      <li class="${configManageActive } treeview">
        <a href="${ctx }/op/config/list"><i class="fa fa-file"></i> <span>配置文件</span></a>
      </li>
      </c:if>
      <c:if test="${fn:indexOf(curr_user.privileges,'versionManageActive') > -1 }">
      <li class="${versionManageActive } treeview">
        <a href="${ctx }/op/version/list"><i class="fa fa-upload"></i> <span>版本管理</span></a>
      </li>
      </c:if>
      <c:if test="${fn:indexOf(curr_user.privileges,'deployLogManageActive') > -1 }">
      <li class="${deployLogManageActive } treeview">
        <a href="${ctx }/op/deployLog/list"><i class="fa fa-list"></i> <span>发布历史</span></a>
      </li>
      </c:if>
      <c:if test="${fn:indexOf(curr_user.privileges,'userManageActive') > -1 }">
      <li class="${userManageActive } treeview">
        <a href="${ctx }/op/user/list"><i class="fa fa-user-secret"></i> <span>用户管理</span></a>
      </li>
      </c:if>
    </ul>
  </section>
  <!-- /.sidebar -->
</aside>