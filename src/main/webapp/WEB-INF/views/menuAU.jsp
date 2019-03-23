<%@ page language="java" contentType="text/html; charset=utf-8"
				pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %><!-- 引入form标签的标签库 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>文章列表--layui后台管理模板 2.0</title>
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

<form:form class="layui-form" style="width:80%;" id="form2" method="POST" modelAttribute="menuBO">
	<form:errors path="*"></form:errors>
	<c:if test="${menuBO.menu.id != null }">
		<input type="hidden" name="_method" value="PUT"/>
		<input type="hidden" name="id" value="${menuBO.menu.getId() }">
	</c:if>
	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">菜单名称</label>
		<div class="layui-input-block">
			<form:input path="menu.name" id="nameId" class="layui-input"  lay-verify="required" placeholder="请输入菜单名"/>
			<!-- <input type="text" name="name" class="layui-input"  lay-verify="required" placeholder="请输入登录名"> -->
		</div>
	</div>
	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">菜单图标:</label>
		<div class="layui-input-inline">
			<input type="text" id="iconPicker" lay-filter="iconPicker" class="layui-input">
			<form:input path="menu.pic" type="hidden" id="pic"/>
		</div>
	</div>
	<div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
		<label class="layui-form-label">父级菜单</label>
		<div class="layui-input-inline">
			<form:select path="menu.pid" id="pidSelect" items="${supMenu }" itemLabel="name" itemValue="id"></form:select>
		</div>
	</div>

	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">菜单URL</label>
		<div class="layui-input-block">
			<form:input path="menu.url" class="layui-input"  lay-verify="required" placeholder="请输入菜单url"/>
		</div>
	</div>



	<div class="layui-form-item layui-row layui-col-xs12">
		<div class="layui-input-block">
			<c:if test="${menuBO.menu.id == null }">
				<button class="layui-btn layui-btn-sm" lay-submit lay-filter="addMenu">立即添加</button>
				<button type="button" id="reset" class="layui-btn layui-btn-sm layui-btn-primary">重置</button>
			</c:if>
			<c:if test="${menuBO.menu.id != null }">
				<button class="layui-btn layui-btn-sm" lay-submit lay-filter="updateMenu">确认修改</button>
				<button type="reset" id="reset2" class="layui-btn layui-btn-sm layui-btn-primary">重置</button>
			</c:if>
		</div>
	</div>
</form:form>
<script type="text/javascript" src="<%=request.getContextPath() %>/layui/layui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/menuAU.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-2.1.0.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        if (${menuBO.menu.id == null }){
            $("#pidSelect").val("");
        }
    })

    //重置事件
	$("#reset").click(function () {
        $("#resetIcon").attr("class","layui-icon");
		$("#resetIcon").html("");
		$("#pic").val("");
		$(".layui-input").val("");
    })



    layui.config({
        base: '<%=request.getContextPath() %>/js/',
    })
    layui.use(['iconPicker'], function () {
        var iconPicker = layui.iconPicker;

        iconPicker.render({
            // 选择器，推荐使用input
            elem: '#iconPicker',
            // 数据类型：fontClass/unicode，推荐使用fontClass
            type: 'fontClass',
            // 是否开启搜索：true/false
            search: true,
            // 点击回调
            click: function (data) {
                $("#pic").val(data.icon);
                console.log(data);
            }
        });

        /**
         * 选中图标 （常用于更新时默认选中图标）
         * @param filter lay-filter
         * @param iconName 图标名称，自动识别fontClass/unicode
         */
        iconPicker.checkIcon('iconPicker', '${menuBO.menu.pic }');

        $("#reset2").click(function () {
            $("#resetIcon").attr("class","layui-icon");
            iconPicker.checkIcon('iconPicker', '${menuBO.menu.pic }');
            $("#pic").val('${menuBO.menu.pic }');
        })

    });

</script>
</body>
</html>