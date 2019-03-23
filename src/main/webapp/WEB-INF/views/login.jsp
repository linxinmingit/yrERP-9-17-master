<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="loginHtml" >
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>登录--layui后台管理模板 2.0</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="icon" href="<%=request.getContextPath() %>/erp.ico">
	<link rel="stylesheet" href="<%=request.getContextPath() %>/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/public.css" media="all" />
</head>
<body class="loginBody" style="background: url(<%=request.getContextPath()%>/images/002.jpg)">
	<form id="form1" class="layui-form">
		<div class="login_face"><img src="<%=request.getContextPath() %>/favicon.ico" class="userAvatar"></div>
		<div class="layui-form-item input-item">
			<label for="userName">用户名</label>
			<input type="text" placeholder="请输入用户名" autocomplete="off" id="userName" name="name" class="layui-input" lay-verify="required">
		</div>
		<div class="layui-form-item input-item">
			<label for="password">密码</label>
			<input type="password" placeholder="请输入密码" autocomplete="off" id="password" name="password" class="layui-input" lay-verify="required">
		</div>
		<div class="layui-form-item input-item" id="imgCode">
			<label for="code">验证码</label>
			<input type="text" placeholder="请输入验证码" autocomplete="off" id="code" name="loginVerifyCode" class="layui-input">
			<img src="<%=request.getContextPath() %>/u_user/userTable/getVerifyCode" id="vimg" title="点击更换" onclick="changeCode();">
		</div>
		<div class="layui-form-item" pane="">
		<label>7天免登录</label>
		<div class="layui-input-block">
			<input type="checkbox" name="" lay-skin="switch" lay-text="开启|关闭">
		</div>
	</div>
		<div class="layui-form-item">
			<button class="layui-btn layui-block" lay-filter="login" lay-submit>登录</button>
		</div>
		<div class="layui-form-item layui-row">
			<a href="javascript:;" class="seraph icon-qq layui-col-xs4 layui-col-sm4 layui-col-md4 layui-col-lg4"></a>
			<a href="javascript:;" class="seraph icon-wechat layui-col-xs4 layui-col-sm4 layui-col-md4 layui-col-lg4"></a>
			<a href="javascript:;" class="seraph icon-sina layui-col-xs4 layui-col-sm4 layui-col-md4 layui-col-lg4"></a>
		</div>
	</form>
	<script type="text/javascript" src="<%=request.getContextPath() %>/layui/layui.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/login.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/cache.js"></script>
	<script type="text/javascript">

	function changeCode() {
		var imgNode = document.getElementById("vimg");
		imgNode.src = "u_user/userTable/getVerifyCode?t=" + Math.random();
	}
	</script>
</body>
</html>