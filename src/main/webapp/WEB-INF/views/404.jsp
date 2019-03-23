<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>404--layui后台管理模板 2.0</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="<%=request.getContextPath() %>/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/public.css" media="all" />
</head>
<body class="childrenBody">
	<div class="noFind">
		<div class="ufo">
			<i class="seraph icon-test ufo_icon"></i>
			<i class="layui-icon page_icon">&#xe638;</i>
		</div>
		<div class="page404">
			<i class="layui-icon">&#xe61c;</i>
			<p>我勒个去，页面被外星人挟持了!</p>
		</div>
	</div>
	<script type="text/javascript" src="<%=request.getContextPath() %>/layui/layui.js"></script>
</body>
</html>