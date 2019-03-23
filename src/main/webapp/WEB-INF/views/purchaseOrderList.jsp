<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>用户中心--layui后台管理模板 2.0</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/public.css" media="all" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/layui/layui.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/purchaseOrderList.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-2.1.0.js"></script>
</head>
<script>
    layui.use('upload', function() {
            var $ = layui.jquery,
             layer = layui.layer
                , upload = layui.upload;
        var beforeIndex;
        //选完文件后不自动上传
        upload.render({
            elem: '#test8'
            ,url: '<%=request.getContextPath()%>/purchaseExcel/import'
            ,auto: false
            ,method: 'post'
            ,field: 'excelFile'
            ,accept: 'file'
            ,exts: 'xls|xlsx'
            //,multiple: true
            ,bindAction: '#test9'
            ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                beforeIndex = layer.msg('文件导入中，请稍候',{icon: 16,time:false,shade:0.8});
            }
            ,done: function(res){
                setTimeout(function(){
                    layer.close(beforeIndex);
                    layer.msg("上传成功！",{icon:1});
                },1000);
                console.log(res)
            }
            ,error: function(index, upload){
                //当上传失败时，你可以生成一个“重新上传”的按钮，点击该按钮时，执行 upload() 方法即可实现重新上传
                setTimeout(function(){
                    layer.close(beforeIndex);
                    layer.msg("上传失败！",{icon:2});
                },1000);
            }
        });
    });
</script>
<script type="text/javascript">
    function aa() {
        window.location.href="<%=request.getContextPath()%>/purchaseExcel/export?purchaseCode="+$('#purchaseCode').val()+"&purchaseWareCode="+$('#purchaseWareCode').val()+"&purchaseOrder.status="+$('#rStates').val();
        /*$.ajax({
            type: 'post',
            url: '<%=request.getContextPath()%>/purchaseExcel/export',//请求导出文件接口
            dataType : 'json',
            data: {"purchaseCode":$('#purchaseCode').val(),"purchaseWareCode":$('#purchaseWareCode').val(),"purchaseOrder.status":$('#rStates').val()},
            error: function() {
                layer.msg("操作失败",{icon:2});
                /!*setTimeout(function(){
                    window.location.href = "<%=request.getContextPath() %>/requisition/requisitionTable";
                },1200);*!/
            },
            success: function(data){
                if("1" == data.code){
                    layer.msg(data.msg,{icon:1});
                    /!*setTimeout(function(){
                        window.location.href = "<%=request.getContextPath() %>/requisition/requisitionTable";
                    },1200);*!/
                }else{
                    layer.msg(data.msg,{icon:2});
                    /!*setTimeout(function(){
                        window.location.href = "<%=request.getContextPath() %>/requisition/requisitionTable";
                    },1200);*!/
                }
            }
        });*/
    }
</script>
<body class="childrenBody">
<form class="layui-form">
    <blockquote class="layui-elem-quote quoteBox">
        <form class="layui-form" id="searchFormId">
            <div class="layui-inline">
                <div class="layui-input-inline" style="width: 500px;">
                    <div class="layui-input-inline" style='width:35%;heigth:50%,padding:0;margin:0;float:left;box-sizing:border-box;'>
                        <input type="text" class="layui-input searchVal" name="" id="purchaseCode" value="" placeholder="请输入订单名称/编号：" />
                    </div>
                    <div class="layui-input-inline" style='width:30%;heigth:50%,padding:0;margin:0;float:left;box-sizing:border-box;'>
                        <input type="text" class="layui-input searchVal" name="" id="purchaseWareCode" placeholder="请输入商品名称：" />
                    </div>
                    <div class="layui-input-inline" style='width:30%;heigth:50%,padding:0;margin:0;float:left;box-sizing:border-box;'>
                    <select name="" id="rStates" lay-verify="">
                        <option value="">请选择订单类型</option>
                        <option value="0">驳回</option>
                        <option value="1">交易成功</option>
                        <option value="2">待审核</option>
                        <option value="3">申请退货</option>
                        <option value="4">退货成功</option>
                    </select>
                    </div>
                </div>
                <a class="layui-btn search_btn" data-type="reload">搜索</a>
            </div>
            <shiro:hasPermission name="/requisition/requisitionTable/add/GET">
                <div class="layui-inline">
                    <a class="layui-btn layui-btn-normal addNews_btn">采购申请</a>
                </div>
            </shiro:hasPermission>
            <shiro:hasPermission name="/requisition/requisitionTable/*/DELETE">
                <div class="layui-inline">
                       <a class="layui-btn layui-btn-danger layui-btn-normal delAll_btn">批量删除</a>
                </div>
            </shiro:hasPermission>
        </form>
        <div class="layui-upload">
            <div class="layui-inline" style="width: 200px;">
                <button type="button" class="layui-btn" onclick="aa()" id="export">导出数据</button>
                    <%--<form action="<%=request.getContextPath()%>/purchaseExcel/export" method="POST">
                        <input type="submit" style="background-color: #d3a4ff; border: 1px solid #d3a4ff" value="导出采购表" />
                    </form>--%>

            </div>
            <div class="layui-inline">
            <button type="button" class="layui-btn layui-btn-normal" id="test8">选择文件</button>
            <button type="button" class="layui-btn" id="test9">开始上传</button>
            </div>
        </div>

        <div class="layui-inline">

        </div>
    </blockquote>
    <table id="purchaseList" lay-filter="purchaseList"></table>
    <!--操作-->
    <script type="text/html" id="purchaseBar">
        <shiro:hasPermission name="/requisition/requisitionTable/*/GET">
        <a class="layui-btn layui-btn-xs" lay-event="edit"><i class="layui-icon">&#xe642;</i>编辑</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="/requisition/requisitionTable/*/DELETE">
        <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del"><i class="layui-icon">&#xe640;</i>删除</a>
        </shiro:hasPermission>
    </script>
</form>
</body>
</html>