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

<form:form class="layui-form" style="width:80%;" id="form2" method="POST" modelAttribute="supplierBo">
    <form:errors path="*"></form:errors>
    <c:if test="${supplierBo.supplier.id != null }">
        <input type="hidden" name="_method" value="PUT"/>
        <input type="hidden" name="id" value="${supplierBo.supplier.getId() }">
    </c:if>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">供应商名称</label>
        <div class="layui-input-block">
            <form:input path="supplier.name" class="layui-input" lay-verify="required" placeholder="请输入供应商名称："/>
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">供应商编号</label>
        <div class="layui-input-block">
            <form:input path="supplier.code" class="layui-input"  lay-verify="required" placeholder="请输入供应商编号："/>
        </div>
    </div>

    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">联系电话</label>
        <div class="layui-input-block">
            <form:input path="supplier.phoneNumber" class="layui-input"  lay-verify="required" placeholder="请输入联系电话："/>
        </div>
    </div>

    <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
        <label class="layui-form-label">地址</label>
        <div class="layui-input-inline">
            <form:input path="supplier.addr" class="layui-input" lay-verify="required" placeholder="请输入地址"/>
        </div>
    </div>
    <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
        <label class="layui-form-label">级别</label>
        <div class="layui-input-inline">
            <form:select path="supplier.rank" id="rankId" items="${rankList}"></form:select>
        </div>
    </div>
    <input type="hidden" name="supplier.createEmp" value="${sessionScope.user.getName()}">

    <div class="layui-form-item layui-row layui-col-xs12">
        <div class="layui-input-block">
            <c:if test="${supplierBo.supplier.id == null }">
                <button class="layui-btn layui-btn-sm" lay-submit lay-filter="addMenu">立即添加</button>
                <button type="button" id="resetAdd" class="layui-btn layui-btn-primary">重置</button>
            </c:if>
            <c:if test="${supplierBo.supplier.id != null }">
                <button class="layui-btn layui-btn-sm" lay-submit lay-filter="updateMenu">确认修改</button>
                <button type="reset" class="layui-btn layui-btn-sm layui-btn-primary">重置</button>
            </c:if>
        </div>
    </div>
 </form:form>
<script type="text/javascript" src="<%=request.getContextPath() %>/layui/layui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/supplierAU.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-2.1.0.js"></script>
<script type="text/javascript">
if(${supplierBo.supplier.id == null }){
    $("#rankId").val("");
}
</script>
</body>
</html>
