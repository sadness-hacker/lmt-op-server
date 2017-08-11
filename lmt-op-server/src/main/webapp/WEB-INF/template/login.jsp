<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>运维平台-登录</title>
	<link rel="stylesheet" type="text/css" href="${ctx }/css/login.css" />
</head>
<body>
	<div class ="box1">
			<img class="bg" src="${ctx }/images/background.jpg" />
			<div class="title">运维平台</div>
			<div id="log_center">
				<div id="log_contain">
					<div id="log1">
						<span>登录</span>
					</div>
					<br/>
					<form id="login" action="login" method="post">
						<div id="login_form">
							<div id="log_name">
								<input type="text" id="log_user" name="username" value="" placeholder="请输入用户名"/>
							</div>
							<div id="log_pwdd">
								<input type="password" id="log_pwd" name="password" value=""  placeholder="请输入密码"/>
							</div>
							
						</div>
						<br />
						<div id="login_bottom">
							<span style="color:red;">${error_msg }</span>
						</div>
						<div id="log_btnn">
							<input type="submit" id="log_btn" value="登录"  />
						</div>
					</form>
			</div>
		</div>
		<div class="footer">
			<span><a href="http://www.duchengxian.com" target="_blank">悲情黑客</a> 版权所有@2013-2017 </span>
		</div>
	</div>
</body>
</html>