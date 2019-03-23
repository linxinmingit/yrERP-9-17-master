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

<form:form class="layui-form" style="width:80%;" id="form2" method="POST" modelAttribute="suppWareType">
    <form:errors path="*"></form:errors>
    <c:if test="${suppWareType.id != null }">
        <input type="hidden" name="_method" value="PUT"/>
        <input type="hidden" name="id" value="${suppWareType.getId() }">
    </c:if>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">商品类型名称</label>
        <div class="layui-input-block">
            <form:input path="name" class="layui-input"  lay-verify="required" placeholder="请输入商品类型名："/>
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">商品类型编号</label>
        <div class="layui-input-block">
            <form:input path="code" class="layui-input"  lay-verify="required" placeholder="请输入商品类型编号："/>
        </div>
    </div>

    <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
        <label class="layui-form-label">上级类型</label>
        <div class="layui-input-inline">
            <form:select path="supCode" id="pidSelect" items="${suppWareTypes }" itemLabel="name" itemValue="code"></form:select>
        </div>
    </div>

    <div class="layui-form-item layui-row layui-col-xs12">
        <div class="layui-input-block">
            <c:if test="${suppWareType.id == null }">
                <button class="layui-btn layui-btn-sm" lay-submit lay-filter="addMenu">立即添加</button>
                <button type="button" id="resetAdd" class="layui-btn layui-btn-primary">重置</button>
            </c:if>
            <c:if test="${suppWareType.id != null }">
                <button class="layui-btn layui-btn-sm" lay-submit lay-filter="updateMenu">确认修改</button>
                <button type="reset" class="layui-btn layui-btn-sm layui-btn-primary">重置</button>
            </c:if>
        </div>
    </div>
</form:form>
<script type="text/javascript" src="<%=request.getContextPath() %>/layui/layui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/supp_ware_typeAU.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-2.1.0.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        if (${suppWareType.id == null }){
            $("#pidSelect").val("");
        }
    })

</script>
</body>
</html>