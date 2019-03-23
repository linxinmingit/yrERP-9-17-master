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

<%--@elvariable id="purchaseOrderBO" type="aj"--%>
<form:form class="layui-form" style="width:80%;" id="form2" method="POST" modelAttribute="purchaseOrderBO">
    <form:errors path="*"></form:errors>
    <c:if test="${purchaseOrderBO.purchaseOrder.id != null }">
        <input type="hidden" name="_method" value="PUT"/>
        <input type="hidden" name="id" value="${purchaseOrderBO.purchaseOrder.id }">
    </c:if>

    <%--编号，总价you由后台复值--%>
 <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
        <label class="layui-form-label">申请人姓名</label>
        <div class="layui-input-inline">
            <form:select path="user.jobNum" id="userName" items="${userList}" itemLabel="name" itemValue="jobNum" />
        </div>
    </div>
    <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
        <label class="layui-form-label">申请人电话</label>
        <div class="layui-input-inline">
            <form:select path="user.phoneNumber" id="uPhoneNumber" items="${userList }" itemLabel="phoneNumber" itemValue="jobNum" />
        </div>
    </div>

    <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
        <label class="layui-form-label">采购部门</label>
        <div class="layui-input-inline">
            <form:select path="purchaseOrder.departmentCode" id="depCode" items="${departmentList}" cssStyle="width:80px;height: 40px;"></form:select>
        </div>
    </div>
    <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
        <label class="layui-form-label">供应商</label>
        <div class="layui-input-inline">
               <form:select path="purchaseOrder.supplierCode" id="suppCode" items="${supplierList}" cssStyle="width:80px;height: 40px;"></form:select>
        </div>
    </div>
    <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
        <label class="layui-form-label">商品名称</label>
        <div class="layui-input-inline">
            <form:select path="supplierWares.name" id="suppWares" items="${supplierWareList }" itemValue="code" itemLabel="name"></form:select>
        </div>
    </div>
    <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
        <label class="layui-form-label">商品类型</label>
        <div class="layui-input-inline">
            <form:select path="supplierWares.type" id="suppWareType" items="${supplierWareList }" itemValue="code" itemLabel="type"></form:select>
        </div>
    </div>
    <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
        <label class="layui-form-label">商品品牌 </label>
        <div class="layui-input-inline">
            <form:select path="supplierWares.brand" id="suppWareBrand" items="${supplierWareList }" itemValue="code" itemLabel="brand"></form:select>
        </div>
    </div>
    <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
        <label class="layui-form-label">采购数量</label>
        <div class="layui-input-inline">
            <form:input path="purchaseOrder.purchaseNumber" class="layui-input"  lay-verify="required" placeholder="请输入商品数量"/>
        </div>
    </div>
    <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
        <label class="layui-form-label">单价</label>
        <div class="layui-input-inline">
            <form:input path="purchaseOrder.unitPrice" class="layui-input"  lay-verify="required" placeholder="请输入商品单价"/>
        </div>
    </div>
    <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
        <label class="layui-form-label">收货人</label>
        <div class="layui-input-inline">
            <form:select path="purchaseOrder.consignee" id="consignee" items="${userList }" itemLabel="name" itemValue="name" />
        </div>
    </div>
    <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
        <label class="layui-form-label">收货仓库</label>
        <div class="layui-input-inline">
             <form:select path="purchaseOrder.depotCode" id="depotCode" items="${depotList }" cssStyle="width:80px;height: 40px;"></form:select>
        </div>
    </div>
    <c:if test="${purchaseOrderBO.purchaseOrder.id != null }">
        <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
            <label class="layui-form-label">订单类型</label>
            <div class="layui-input-inline">
                <form:select path="purchaseOrder.status" id="orderStatus" items="${status }" cssStyle="width:80px;height: 40px;"></form:select>
            </div>
        </div>
    </c:if>
    <div class="layui-form-item layui-row layui-col-xs12">
        <div class="layui-input-block">
            <c:if test="${purchaseOrderBO.purchaseOrder.id == null }">
                <button class="layui-btn layui-btn-sm" lay-submit lay-filter="addOrder">立即添加</button>
                <button type="button" id="resetAdd" class="layui-btn layui-btn-primary">重置</button>
            </c:if>
            <c:if test="${purchaseOrderBO.purchaseOrder.id != null }">
                <button class="layui-btn layui-btn-sm" lay-submit lay-filter="updateOrder">确认修改</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </c:if>
           <%-- <button type="reset" class="layui-btn layui-btn-sm layui-btn-primary">取消</button>--%>
        </div>
    </div>
 </form:form>
<script type="text/javascript" src="<%=request.getContextPath() %>/layui/layui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/purchaseOrderAU.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-2.1.0.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        if (${purchaseOrderBO.purchaseOrder.id == null }){
            alert($("#userName").val());
            $("#userName").val("");
            $("#uPhoneNumber").val("");
            $("#depCode").val("");
            $("#suppCode").val("");
            $("#suppWares").val("");
            $("#suppWareType").val("");
            $("#suppWareBrand").val("");
            $("#consignee").val("");
            $("#depotCode").val("");
            $("#orderStatus").val("");
        }
    })
</script>
</body>
</html>