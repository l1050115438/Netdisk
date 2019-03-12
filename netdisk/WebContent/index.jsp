<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	hello
	<!-- 用户名：admin 密码  admin   
		控件：<input />
		<form/>
		submit提交按钮
		｛
			1，控件，和用户  交互
			2、提交动作
			3、提交的服务器地址。 Servlet服务 多线程的。
		｝
	 -->
	<form action="loginServlet">
		用户名：<input type="text" name="username" /> <br/>
		密码：<input type="password" name="pwd"/> <br/>
		<input type="submit" value="登录"/>
	</form>
</body>
</html>