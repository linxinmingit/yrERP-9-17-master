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
        if (${user.id == null }){
            $("#depaCode").val("");
        }
	    $("#password").change(function(){
            var oldPassword = $("#oldPassword").val();//获得第一次输入的密码
            var newPassword = $(this).val();//获得
			if(oldPassword != newPassword){
                layer.msg("两次输入密码不正确",{icon:2});
                $("#submits").attr("disabled",true);
			}else{
                $("#submits").attr("disabled",false);
			}
        });


        $("#userFace,#test1").click(function(){//点击头像
            $("#files").click();//触发文件上传事件
        });

        $("#fileUpload").change(function(){//实现图片上传参数配置
            changes();//调用ajax
        });

        function changes(){//用ajax实现图片上传预览
            $.ajaxFileUpload({
                type: "POST",
                url:"${pageContext.request.contextPath}/u_user/userTable/upload",
                dataType: "json",
                fileElementId:"files",  // 文件的id
                success: function(d){
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
        $("#oldPassword").val($("#password").val());//旧密码等于新密码，实现回显
	});


</script>
<body class="childrenBody">
<form:form class="layui-form" style="width:80%;" id="form2" method="POST" modelAttribute="user">
	<c:if test="${user.id != null }">
		<input type="hidden" name="_method" value="PUT"/>
		<input type="hidden" id="id" name="id" value="${user.getId() }">
	</c:if>	
 	<!-- 上传头像 -->
	<div class="layui-upload" align="center">
		<c:if test="${user.id != null }">
			<div class="layui-upload-list">
				<!-- 头像回显的样式，这里是圆形 -->
				<img src="${user.photo}" class="layui-upload-img layui-circle userFaceBtn userAvatar" style="width:100px;height:100px;" id="userFace">
				<p id="demoText"> </p>
			</div>
			<input type="hidden" id="filesCopy" name="filesCopy" value="${user.photo }"><!-- 隐藏框是存图片路径 -->
			<button type="button" class="layui-btn" id="test1">修改头像</button>
		</c:if>
		<c:if test="${user.id == null }">
			<div class="layui-upload-list">
				<img src="http://192.168.1.77/static/b.jpg" class="layui-upload-img layui-circle userFaceBtn userAvatar" style="width:100px;height:100px;" id="userFace">
				<p id="demoText"></p>
			</div>
			<input type="hidden" id="filesCopy" name="filesCopy" value="http://192.168.1.77/static/b.jpg">
			<!-- 上传头像成功后保存的隐藏框 -->
			<button type="button" class="layui-btn" id="test1">上传头像</button>
		</c:if>
		<div id="fileUpload" style="display: none;">
			<input type="file" id="files" name="files" accept="image/*">
		</div>
	</div>
	<br/>
	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">姓名</label>
		<div class="layui-input-block">
			<form:input path="name" class="layui-input"  lay-verify="required" placeholder="请输入姓名"/>
		</div>
	</div>
	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">密码</label>
		<div class="layui-input-block">
			<input id="oldPassword" name="oldPassword" type="password" class="layui-input"  lay-verify="required" placeholder="请输入密码"/>
		</div>
	</div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">确认密码</label>
        <div class="layui-input-block">
            <form:input path="password" type="password" class="layui-input"  lay-verify="required" placeholder="请输入密码"/>
        </div>
    </div>
	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">工号</label>
		<div class="layui-input-block">
			<form:input path="jobNum" class="layui-input"  lay-verify="required" placeholder="请输入工号"/>
		</div>
	</div>
	<div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
		<label class="layui-form-label">所属部门</label>
		<div class="layui-input-inline">
			<form:select path="depaCode" id="depaCode" items="${depaList }"></form:select>
		</div>
	</div>
	<div class="layui-inline">
		<label class="layui-form-label">生日</label>
		<div class="layui-input-inline">
			<form:input class="layui-input" path="birthday" placeholder="请选择生日" />
		</div>
	</div>
	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">电话</label>
		<div class="layui-input-block">
			<form:input path="phoneNumber" class="layui-input"  lay-verify="required" placeholder="请输入手机号码"/>
		</div>
	</div>
	<div class="magb15 layui-col-md4 layui-col-xs12">
		<label class="layui-form-label">性别</label>
		<div class="layui-input-block">
			<form:radiobuttons path="sex" items="${sexs }"/>
		</div>
	</div>
	</div>
		<div class="magb15 layui-col-md4 layui-form-item layui-col-xs12">
		<label class="layui-form-label">地址</label>
		<div class="layui-input-inline">
			<form:input path="addr" class="layui-input" lay-verify="required" placeholder="请输入地址"/>
		</div>
	</div>
	
	<div class="layui-form-item layui-row layui-col-xs12">
		<div class="layui-input-block">
			<c:if test="${user.id == null }">
				<button id="submits" type="button" class="layui-btn" lay-submit lay-filter="addUser">立即添加</button>
				<button type="button" id="resets" class="layui-btn layui-btn-primary">重置</button>
			</c:if>
			<c:if test="${user.id != null }">
				<button id="submits" type="button" class="layui-btn" lay-submit lay-filter="updateUser">确认修改</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
			</c:if>
		</div>
	</div>
</form:form>

<script type="text/javascript" src="<%=request.getContextPath() %>/layui/layui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/userAU.js"></script>
<script>
	layui.use('laydate', function() {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#birthday'
        });
    })
</script>
</body>
</html>