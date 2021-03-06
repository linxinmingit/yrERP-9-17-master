<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/public.css" media="all"/>
</head>
<body class="childrenBody">
<form class="layui-form">
    <blockquote class="layui-elem-quote quoteBox">
        <form class="layui-form" id="searchFormId">
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <input type="text" class="layui-input searchVal" id="type" placeholder="仓库商品类型："/>
                </div>
                <a class="layui-btn search_btn" data-type="reload">搜索</a>
            </div>
            <shiro:hasPermission name="/ware_type/ware_typeTable/add/GET">
                <div class="layui-inline">
                    <a class="layui-btn layui-btn-normal addNews_btn">添加仓库商品类型</a>
                </div>
            </shiro:hasPermission>
            <shiro:hasPermission name="/ware_type/ware_typeTable/*/DELETE">
                <div class="layui-inline">
                    <a class="layui-btn layui-btn-danger layui-btn-normal delAll_btn">批量删除</a>
                </div>
            </shiro:hasPermission>
        </form>
    </blockquote>
    <table id="supplierList" lay-filter="supplierList"></table>
    <!--操作-->
    <script type="text/html" id="supplierListBar">
        <shiro:hasPermission name="/ware_type/ware_typeTable/*/GET">
            <a class="layui-btn layui-btn-xs" lay-event="edit"><i class="layui-icon">&#xe642;</i>编辑</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="/ware_type/ware_typeTable/*/DELETE">
            <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del"><i class="layui-icon">&#xe640;</i>删除</a>
        </shiro:hasPermission>
    </script>
</form>
<script type="text/javascript" src="<%=request.getContextPath() %>/layui/layui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/wareTypeList.js?" +new Date().getTime()></script>
</body>
</html>