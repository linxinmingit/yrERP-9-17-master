<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户中心--layui后台管理模板 2.0</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="<%=request.getContextPath() %>/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/public.css" media="all" />
</head>
<style>
	.layui-form-checkbox[lay-skin=primary] i{top:5px;}
</style>
<body class="childrenBody">
<form class="layui-form">
	<blockquote class="layui-elem-quote quoteBox">
		<form class="layui-form">
			<div class="layui-inline">
				<div class="layui-input-inline">
					<input type="text" id="rName" class="layui-input searchVal" placeholder="请输入角色的名字" />
				</div>
				<a class="layui-btn search_btn" data-type="reload">搜索</a>
			</div>
			<shiro:hasPermission name="/u_role/roleTable/add/GET">
				<div class="layui-inline">
					<a class="layui-btn layui-btn-normal addNews_btn">添加角色</a>
				</div>
			</shiro:hasPermission>
			<shiro:hasPermission name="/u_role/roleTable/*/DELETE">
				<div class="layui-inline">
					<a class="layui-btn layui-btn-danger layui-btn-normal delAll_btn">批量删除</a>
				</div>
			</shiro:hasPermission>
		</form>
	</blockquote>
	<table id="roleList" lay-filter="roleList"></table>

	<!-- <a class="layui-btn layui-btn-xs layui-btn-warm" lay-event="usable">已启用</a>-->
	<!--操作-->
	<script type="text/html" id="roleListBar">
		<shiro:hasPermission name="/u_role/roleTable/getPermission/GET">
			<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="auth"><i class="layui-icon"></i>角色权限</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="/u_role/roleTable/*/GET">
			<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="/u_role/roleTable/*/DELETE">
			<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
		</shiro:hasPermission>
	</script>
</form>
<script type="text/javascript" src="<%=request.getContextPath() %>/layui/layui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/roleList.js"></script>
</body>
</html>