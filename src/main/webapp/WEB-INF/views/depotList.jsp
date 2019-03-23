<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>仓库管理</title>
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
<form class="layui-form">
    <blockquote class="layui-elem-quote quoteBox">
        <form class="layui-form" id="searchFormId">
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <input type="text" class="layui-input searchVal" id="name" value="" placeholder="请输入仓库名称：" />
                </div>
                <div class="layui-input-inline">
                    <input type="text" class="layui-input searchVal" id="code" value="" placeholder="请输入仓库编号：" />
                </div>
                <div class="layui-input-inline">
                    <input type="text" class="layui-input searchVal" id="addr" value="" placeholder="请输入地址：" />
                </div>
                <a class="layui-btn search_btn" data-type="reload">搜索</a>
            </div>
            <div class="layui-inline">
                <a class="layui-btn layui-btn-normal addNews_btn">添加仓库</a>
            </div>
            <div class="layui-inline">
                <a class="layui-btn layui-btn-danger layui-btn-normal delAll_btn">批量删除</a>
            </div>
        </form>
    </blockquote>
    <table id="depotList" lay-filter="depotList"></table>

    <!-- <a class="layui-btn layui-btn-xs layui-btn-warm" lay-event="usable">已启用</a>-->
    <!--操作-->
    <script type="text/html" id="depotListBar">
        <a class="layui-btn layui-btn-xs" lay-event="edit"><i class="layui-icon">&#xe642;</i>编辑</a>
        <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del"><i class="layui-icon">&#xe640;</i>删除</a>
    </script>
</form>
<script type="text/javascript" src="<%=request.getContextPath() %>/layui/layui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/depotList.js"></script>
</body>
</html>