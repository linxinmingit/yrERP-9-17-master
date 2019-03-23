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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.0.js"></script>
</head>
<body class="childrenBody">
<script type="text/javascript">
    $(function(){
    });
</script>
<form:form class="layui-form" style="width:80%;" id="form2" method="POST" modelAttribute="permissionBo">
    <c:if test="${permissionBo.permission.id != null }">
        <input type="hidden" name="_method" value="PUT"/>
        <input type="hidden" name="permission.id" value="${permissionBo.permission.id }">
    </c:if>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">权限名称</label>
        <div class="layui-input-block">
            <form:input path="permission.name" class="layui-input"  lay-verify="required" placeholder="请输入权限名："/>
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">权限url</label>
        <div class="layui-input-block">
            <form:input path="permission.url" class="layui-input"  lay-verify="required" placeholder="请输入权限url："/>
        </div>
    </div>

    <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
        <label class="layui-form-label">父级权限</label>
        <div class="layui-input-inline">
            <form:select path="permission.supId" id="supPerm" items="${permissions}"></form:select>
        </div>
    </div>

    <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
        <label class="layui-form-label">请求方式</label>
        <div class="layui-input-inline">
            <form:select path="permission.method" id="methodSelect" items="${methodMap}"></form:select>
        </div>
    </div>

    <div class="layui-form-item layui-row layui-col-xs12">
        <div class="layui-input-block">
            <c:if test="${permissionBo.permission.id == null }">
                <button type="button" class="layui-btn" lay-submit lay-filter="addMenu">立即添加</button>
                <button type="button" id="resetAdd" class="layui-btn layui-btn-primary">重置</button>
            </c:if>
            <c:if test="${permissionBo.permission.id != null }">
                <button type="button" class="layui-btn" lay-submit lay-filter="updateMenu">确认修改</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </c:if>
        </div>
    </div>
</form:form>
<script type="text/javascript" src="<%=request.getContextPath() %>/layui/layui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/permissionAU.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-2.1.0.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        if (${permissionBo.permission.id == null }){
            $("#supPerm").val("");
            $("#methodSelect").val("");
        }
    })
</script>
</body>
</html>
