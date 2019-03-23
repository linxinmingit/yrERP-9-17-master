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
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-2.1.0.js"></script>

</head>
<script type="text/javascript">
    $(document).ready(function() {
        /*  给输入名字的文本框绑定一个失去焦点事件，jquery中为focusout事件，同时在文本框后定义一个显示检查结果的span。
		 2、当文本框失去焦点时，调用ajax异步向后台发送请求。
		 3、将请求结果显示在文本框后面提示用户,避免所有信息填写完毕表单提交时才发现名字重复。
		 示例：
	    */
        //给文本框绑定一个失去焦点事件
        $("#code").focusout(function() {
            var typeCode = $("#code").val();
            if(typeCode != null && typeCode != ''){
                checkTypeCode(typeCode);
            }
        });
        //发ajax请求到后台判断email格式是否错误
        function checkTypeCode(typeCode){
            $.ajax({
                url : "<%=request.getContextPath()%>/ware_type/ware_typeTable/checkTypeCode",
                type : "get",
                dataType : 'json',
                data : {"code":typeCode},
                success : function(result) {
                    //已经存在该名字提示用户
                    if(result==true){
                        $("#errorCode").html("商品类型编号已存在");
                    }else{
                        $("#errorCode").html("");
                    }
                },
                error : function() {
                    alert('检查用户是否存在发生错误');
                }
            });
        }
    });

</script>

<body class="childrenBody">

<form:form class="layui-form" style="width:80%;" id="form2" method="POST" modelAttribute="wareType">
    <form:errors path="*"></form:errors>
    <c:if test="${wareType.id != null }">
        <input type="hidden" name="_method" value="PUT"/>
        <input type="hidden" name="id" value="${wareType.getId() }">
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
            <span id="errorCode" style="color: #cc0000"></span>
        </div>
    </div>

    <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
        <label class="layui-form-label">上级类型</label>
        <div class="layui-input-inline">
            <form:select path="supCode" id="pidSelect" items="${supCode}" itemLabel="name" itemValue="code"></form:select>
        </div>
    </div>

    <div class="layui-form-item layui-row layui-col-xs12">
        <div class="layui-input-block">
            <c:if test="${wareType.id == null }">
                <button class="layui-btn layui-btn-sm" lay-submit lay-filter="addMenu">立即添加</button>
                <button type="button" id="resetAdd" class="layui-btn layui-btn-primary">重置</button>
            </c:if>
            <c:if test="${wareType.id != null }">
                <button class="layui-btn layui-btn-sm" lay-submit lay-filter="updateMenu">确认修改</button>
                <button type="reset" class="layui-btn layui-btn-sm layui-btn-primary">重置</button>
            </c:if>
        </div>
    </div>
</form:form>
<script type="text/javascript" src="<%=request.getContextPath() %>/layui/layui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/wareTypeAU.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-2.1.0.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        if (${wareTypeBO.wareType.id == null }){
            $("#pidSelect").val("");
        }
    })
</script>
</body>
</html>