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
	<script type="text/javascript" src="<%=request.getContextPath() %>/layui/layui.js"></script>
</head>
<body class="childrenBody">
<div class="layui-container">
	<div class="layui-row">
		<%--<div class="layui-col-md11 layui-col-md-offset1">
			<fieldset class="layui-elem-field layui-field-title"><legend>权限操作演示</legend></fieldset>
			<div class="layui-form">
				<div class="layui-form-item">
					<div class="layui-form-label">权限操作</div>
					<div class="layui-form-block">
						<button type="button" class="layui-btn layui-btn-primary" onclick="getMaxDept('#LAY-auth-tree-index')">获取树的深度</button>
						<button type="button" class="layui-btn layui-btn-primary" onclick="checkAll('#LAY-auth-tree-index')">全选</button>
						<button type="button" class="layui-btn layui-btn-primary" onclick="uncheckAll('#LAY-auth-tree-index')">全不选</button>
						<button type="button" class="layui-btn layui-btn-primary" onclick="showAll('#LAY-auth-tree-index')">全部展开</button>
						<button type="button" class="layui-btn layui-btn-primary" onclick="closeAll('#LAY-auth-tree-index')">全部隐藏</button>
						<button type="button" class="layui-btn layui-btn-primary" onclick="getNodeStatus('#LAY-auth-tree-index')">获取节点状态</button>
					</div>
				</div>
			</div>
		</div>--%>
		<%--<div class="layui-col-md6 layui-col-md-offset1">
			<fieldset class="layui-elem-field layui-field-title"><legend>权限树</legend></fieldset>--%>
			<!-- 此扩展能递归渲染一个权限树，点击深层次节点，父级节点中没有被选中的节点会被自动选中，单独点击父节点，子节点会全部 选中/去选中 -->
			<br/>
			<form:form class="layui-form" style="width:80%;" id="form2" method="POST" modelAttribute="roleBo">
			<c:if test="${roleBo.role.id != null }">
				<input type="hidden" name="_method" value="PUT"/>
				<input type="hidden" name="role.id" value="${roleBo.role.id }">
			</c:if>
			<div class="layui-form-item layui-row layui-col-xs12">
				<label class="layui-form-label">角色名称</label>
				<div class="layui-input-block">
					<form:input path="role.name" class="layui-input"  lay-verify="required" placeholder="请输入角色名称："/>
				</div>
			</div>
			<div class="layui-form-item layui-row layui-col-xs12">
				<label class="layui-form-label">角色编号</label>
				<div class="layui-input-block">
					<form:input path="role.code" class="layui-input"  lay-verify="required" placeholder="请输入角色编号："/>
				</div>
			</div>
			<%--<div class="layui-form-item">
				<label class="layui-form-label">选择权限</label>
				<div class="layui-input-block">
					<div id="LAY-auth-tree-index"></div>
				</div>
			</div>--%>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<c:if test="${roleBo.role.id == null }">
						<button id="submits" type="button" class="layui-btn" lay-submit lay-filter="addRole">立即添加</button>
					</c:if>
					<c:if test="${roleBo.role.id != null }">
						<button id="submits" type="button" class="layui-btn" lay-submit lay-filter="updateRole">确认修改</button>
					</c:if>
					<button class="layui-btn layui-btn-primary" type="reset">重置</button>
				</div>
			</div>
		</form:form>
		</div>
	</div>
</div>
<script type="text/javascript" src="<%=request.getContextPath() %>/layui/layui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/roleAU.js"></script>
</body>
<%--<script type="text/javascript">
    layui.config({
        base: '<%=request.getContextPath()%>/js/',
    }).extend({
        authtree: 'authtree',
    }).use(['jquery', 'authtree', 'form', 'layer'], function(){
        var $ = layui.jquery;
        var authtree = layui.authtree;
        var form = layui.form;
        var layer = layui.layer;
        // 初始化
        $.ajax({
            url: '<%=request.getContextPath()%>/tree.json',//这里传入id到后台获取角色的所有权限，返回固定的权限字符串
            dataType: 'json',
            success: function(data){
                // 渲染时传入渲染目标ID，树形结构数据（具体结构看样例，checked表示默认选中），以及input表单的名字
                authtree.render('#LAY-auth-tree-index', data.data.trees, {
                    inputname: 'authids[]',
                    layfilter: 'lay-check-auth',
                    // openall: true,
                    autowidth: true,
                });

                // 监听自定义lay-filter选中状态，PS:layui现在不支持多次监听，所以扩展里边只能改变触发逻辑，然后引起了事件冒泡延迟的BUG，要是谁有好的建议可以反馈我
                form.on('checkbox(lay-check-auth)', function(data){
                    // 注意这里：需要等待事件冒泡完成，不然获取叶子节点不准确。
                    setTimeout(function(){
                        /*// 获取所有节点
                        var all = authtree.getAll('#LAY-auth-tree-index');
                        console.log('all', all);
                        // 获取所有已选中节点
                        var checked = authtree.getChecked('#LAY-auth-tree-index');
                        console.log('checked', checked);
                        // 获取所有未选中节点
                        var notchecked = authtree.getNotChecked('#LAY-auth-tree-index');
                        console.log('notchecked', notchecked);
                        // 获取选中的叶子节点
                        var leaf = authtree.getLeaf('#LAY-auth-tree-index');
                        console.log('leaf', leaf);
                        // 获取最新选中
                        var lastChecked = authtree.getLastChecked('#LAY-auth-tree-index');
                        console.log('lastChecked', lastChecked);
                        // 获取最新取消
                        var lastNotChecked = authtree.getLastNotChecked('#LAY-auth-tree-index');
                        console.log('lastNotChecked', lastNotChecked);*/
                        var aaaaaaaaaaaaaaa = authtree.getChecked('#LAY-auth-tree-index');
                        console.log('aaaaaaaaaaaaaaa', aaaaaaaaaaaaaaa);
                    }, 100);
                });
            }
        });
        form.on('submit(LAY-auth-tree-submit)', function(obj){
            var authids = authtree.getAll('#LAY-auth-tree-index');
            //获得选中的权限的id数组
            var idArr = authtree.getChecked('#LAY-auth-tree-index');
            obj.field.authids = authids;
            console.log("obj.field.authids",obj.field.authids)
            console.log("obj.field",obj.field);
            $.ajax({
                url: 'tree.json',//后台接收数据的接口
                dataType: 'json',
                data: obj.field,
                success: function(res){
                    layer.alert('提交成功！');
                }
            });
            return false;
        });
    });
</script>--%>
<%--<script type="text/javascript">
    // 获取最大深度样例
    function getMaxDept(dst){
        layui.use(['jquery', 'layer', 'authtree'], function(){
            var layer = layui.layer;
            var authtree = layui.authtree;

            layer.alert('树'+dst+'的最大深度为：'+authtree.getMaxDept(dst));
        });
    }
    // 全选样例
    function checkAll(dst){
        layui.use(['jquery', 'layer', 'authtree'], function(){
            var layer = layui.layer;
            var authtree = layui.authtree;

            authtree.checkAll(dst);
        });
    }
    // 全不选样例
    function uncheckAll(dst){
        layui.use(['jquery', 'layer', 'authtree'], function(){
            var layer = layui.layer;
            var authtree = layui.authtree;

            authtree.uncheckAll(dst);
        });
    }
    // 显示全部
    function showAll(dst){
        layui.use(['jquery', 'layer', 'authtree'], function(){
            var layer = layui.layer;
            var authtree = layui.authtree;

            authtree.showAll(dst);
        });
    }
    // 隐藏全部
    function closeAll(dst){
        layui.use(['jquery', 'layer', 'authtree'], function(){
            var layer = layui.layer;
            var authtree = layui.authtree;

            authtree.closeAll(dst);
        });
    }
    // 获取节点状态
    function getNodeStatus(dst){
        layui.use(['jquery', 'layer', 'authtree', 'laytpl'], function(){
            var layer = layui.layer;
            var authtree = layui.authtree;
            var laytpl = layui.laytpl;

            // 获取所有节点
            var all = authtree.getAll('#LAY-auth-tree-index');
            // 获取所有已选中节点
            var checked = authtree.getChecked('#LAY-auth-tree-index');
            // 获取所有未选中节点
            var notchecked = authtree.getNotChecked('#LAY-auth-tree-index');
            // 获取选中的叶子节点
            var leaf = authtree.getLeaf('#LAY-auth-tree-index');
            // 获取最新选中
            var lastChecked = authtree.getLastChecked('#LAY-auth-tree-index');
            // 获取最新取消
            var lastNotChecked = authtree.getLastNotChecked('#LAY-auth-tree-index');

            var data = [
                {func: 'getAll', desc: '获取所有节点', data: all},
                {func: 'getChecked', desc: '获取所有已选中节点', data: checked},
                {func: 'getNotChecked', desc: '获取所有未选中节点', data: notchecked},
                {func: 'getLeaf', desc: '获取选中的叶子节点', data: leaf},
                {func: 'getLastChecked', desc: '获取最新选中', data: lastChecked},
                {func: 'getLastNotChecked', desc: '获取最新取消', data: lastNotChecked},
            ];

            var string =  laytpl($('#LAY-auth-tree-nodes').html()).render({
                data: data,
            });
            layer.open({
                title: '节点状态'
                ,content: string
                ,area: '800px'
                ,tipsMore: true
            });
            $('body').on('click', '.LAY-auth-tree-show-detail', function(){
                layer.open({
                    title: '节点详情',
                    content: '['+$(this).data('content')+']',
                    tipsMore: true
                });
            });
        });
    }
</script>--%>
<!-- 状态模板 -->
<%--<script type="text/html" id="LAY-auth-tree-nodes">
	<table class="layui-table">
		<thead>
		<th>方法名</th>
		<th>描述</th>
		<th>节点</th>
		</thead>
		<tbody>
		{{# layui.each(d.data, function(index, item) { }}
		<tr>
			<td>{{item.func}}</td>
			<td>{{item.desc}}</td>
			<td><a class="LAY-auth-tree-show-detail" href="javascript:;" data-content="{{item.data.join(']<br>[')}}">查看详情</a>({{item.data.length}})</td>
		</tr>
		{{# });}}
		</tbody>
	</table>
</script>--%>
</html>
