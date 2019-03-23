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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
</head>
<script type="text/javascript">
    $(function(){
        $("#userFace").click(function(){//点击头像
            $("#files").click();//触发文件上传事件
        });

        $("#fileUpload").change(function(){//实现图片上传参数配置
            changes();//调用ajax
        });

        function changes(){//用ajax实现图片上传预览
            $.ajaxFileUpload({
                type: "POST",
                url:"${pageContext.request.contextPath}/supp_wares/supplierTable/upload",
                dataType: "json",
                fileElementId:"files",  // 文件的id
                success: function(d){//?????d是什么
                    alert(1111);
                    $("#userFace").attr("src",d.url);
                    $("#filesCopy").val(d.url);//将路径显示到隐藏框中上传到后台
                    $("#file1").empty();
                    $("#file1").append("<input type='file' id='files' name='files' accept='image/*'>");
                    $("#files").change(function(){//必须重新绑定事件，否则原来绑定的事件会失效，因为是由ajaxFileUpload插件造成的，每次提交后都会被file新元素覆盖file旧元素，而绑定的change事件则就失效了，需要重新绑定
                        changes();
                    });
                },
                error: function () {
                    alert("上传失败");
                },
            });
        }
    });



</script>
<body class="childrenBody">

<form:form class="layui-form" style="width:80%;" id="form2" method="POST" modelAttribute="supplierWareBo">
    <form:errors path="*"></form:errors>
    <c:if test="${supplierWareBo.supplierWare.id != null }">
        <input type="hidden" name="_method" value="PUT"/>
        <input type="hidden" name="id" value="${supplierWareBo.supplierWare.getId() }">
    </c:if>
    <!-- 上传头像 -->               <!-- div居中 -->
    <div class="layui-upload" align="center">
        <c:if test="${supplierWareBo.supplierWare.id != null }">
            <div class="layui-upload-list">
                <!-- 头像回显的样式，这里是圆形 -->
                <img src="${pageContext.request.contextPath}/supp_wares/supplierTable/icons/${supplierWareBo.supplierWare.id }" class="layui-upload-img layui-circle userFaceBtn userAvatar" style="width:100px;height:100px;" id="userFace">
                <p id="demoText"></p>
            </div>
            <input type="text" id="filesCopy" name="filesCopy" value="${supplierWareBo.supplierWare.suppPhoto }"><!-- 隐藏框是存图片路径 -->
            <button type="button" class="layui-btn" id="test2">修改头像</button>
        </c:if>
        <c:if test="${supplierWareBo.supplierWare.id == null }">
            <div class="layui-upload-list">
                <img src="${pageContext.request.contextPath }/images/587c589d26802.jpg" class="layui-upload-img layui-circle userFaceBtn userAvatar" style="width:100px;height:100px;" id="userFace">
                <p id="demoText"></p>
            </div>
            <input type="hidden" id="filesCopy" name="filesCopy" value="E:\idea\yrERP\yrERP-9-17\src\main\webapp\images\587c589d26802.jpg">
            <!-- 上传头像成功后保存的隐藏框 -->
            <button type="button" class="layui-btn" id="test1">上传头像</button>
        </c:if>
        <div id="fileUpload" style="display: none;">
            <input type="file" id="files" name="files" accept="image/*">
        </div>
    </div>


    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">商品名：</label>
        <div class="layui-input-block">
            <form:input path="supplierWare.name" class="layui-input"  lay-verify="required" placeholder="请输入商品名"/>
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">商品编号</label>
        <div class="layui-input-block">
            <form:input path="supplierWare.code" class="layui-input"  lay-verify="required" placeholder="请输入商品编号"/>
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">供应商编号</label>
        <div class="layui-input-block">
            <form:input path="supplierWare.suppCode" class="layui-input"  lay-verify="required" placeholder="请输入商品编号"/>
        </div>
    </div>
    <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
        <label class="layui-form-label">商品类型</label>
        <div class="layui-input-inline">
            <form:select path="supplierWare.type" id="suppWareType" items="${typeList}" itemLabel="name" itemValue="name"></form:select>
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">商品品牌</label>
        <div class="layui-input-block">
            <form:input path="supplierWare.brand" class="layui-input"  lay-verify="required" placeholder="请输入商品品牌"/>
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">商品库存</label>
        <div class="layui-input-block">
            <form:input path="supplierWare.totalInventory" class="layui-input"  lay-verify="required" placeholder="请输入商品库存"/>
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">采购单价</label>
        <div class="layui-input-block">
            <form:input path="supplierWare.unitPrice" class="layui-input"  lay-verify="required" placeholder="请输入单价"/>
        </div>
    </div>
    <div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
        <label class="layui-form-label">产地</label>
        <div class="layui-input-inline">
            <form:input path="supplierWare.addr" class="layui-input" lay-verify="required" placeholder="请输入地址"/>
        </div>
    </div>
    <input type="hidden" name="supplierWare.createEmp" value="${sessionScope.user.getName()}">

    <div class="layui-form-item layui-row layui-col-xs12">
        <div class="layui-input-block">
            <c:if test="${supplierWareBo.supplierWare.id == null }">
                <button class="layui-btn layui-btn-sm" lay-submit lay-filter="addUser">立即添加</button>
                <button type="button" id="resetAdd" class="layui-btn layui-btn-primary">重置</button>
            </c:if>
            <c:if test="${supplierWareBo.supplierWare.id != null }">
                <button class="layui-btn layui-btn-sm" lay-submit lay-filter="updateUser">确认修改</button>
                <button type="reset" class="layui-btn layui-btn-sm layui-btn-primary">重置</button>
            </c:if>
        </div>
    </div>
</form:form>
<script type="text/javascript" src="<%=request.getContextPath() %>/layui/layui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/supp_waresAU.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        if (${supplierWareBo.supplierWare.id == null }){
            $("#filesCopy").val("");
            $("#suppWareType").val("");
        }
    })
</script>

</body>
</html>