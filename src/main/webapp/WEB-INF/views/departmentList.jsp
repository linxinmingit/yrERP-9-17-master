<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/layui/css/layui.css"/>
    <script type="text/javascript" src="<%=request.getContextPath() %>/layui/layui.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-2.1.0.js"></script>
</head>
<body class="layui-layout-body" style="overflow:auto; padding: 10px">
<a class="layui-btn layui-btn-primary"  onclick="window.location.href='<%=request.getContextPath() %>/';">刷新</a>
<a class="layui-btn layui-btn-primary"  onclick="add();">新增部门</a>
<a class="layui-btn layui-btn-primary"  onclick="print();">打印缓存对象</a>
<div>
    <table class="layui-hidden" id="treeTable" lay-filter="treeTable"></table>
</div>
<script>
    var editObj=null,ptable=null,treeGrid=null,tableId='treeTable',layer=null;
    layui.config({
        base: '<%=request.getContextPath() %>/layui/extend/'
    }).extend({
        treeGrid:'treeGrid'
    }).use(['jquery','treeGrid','layer'], function(){
        var $=layui.jquery;
        treeGrid = layui.treeGrid;//很重要
        layer=layui.layer;
        ptable=treeGrid.render({
            id:tableId
            ,elem: '#'+tableId
            ,idField:'id'
            ,url:'<%=request.getContextPath() %>/department/departmentTable/list'
            ,cellMinWidth: 100
            ,treeId:'code'//树形id字段名称
            ,treeUpId:'supCode'//树形父id字段名称
            ,treeShowName:'name'//以树形式显示的字段
            ,cols: [[
                {field:'name', edit:'name',width:'15%', title: '部门名称'},
                {field:'code',width:'15%', title: '部门编号'},
                {field:'supCode',width:'20%', title: 'supCode'},
                {field:'createTime',width:'20%', title: '创建时间'},
                {field:'createEmp',width:'15%', title: '创建人'},
                /*{field:'id',width:100, title: 'id'},
                {field:'pid', title: 'pid'},*/
                {width:'15%',title: '操作', align:'center'/*toolbar: '#barDemo'*/
                    ,templet: function(d){
                        var html='';
                        var addBtn='<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="upd">编辑</a>';
                        var delBtn='<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>';
                        return addBtn+delBtn;
                    }
                }
            ]]
            ,page:false
        });

        treeGrid.on('tool('+tableId+')',function (obj) {
            if(obj.event === 'del'){//删除行
                // layer.msg(obj.data);
                //console.log(obj.data);
                del(obj.data);
            }else if(obj.event==="upd"){//添加行
                upd(obj.data);
            }
        });
    });

    function del(obj) {
        layer.confirm("你确定删除数据吗？如果存在下级节点则一并删除，此操作不能撤销！", {icon: 3, title:'提示'},
            function(index){//确定回调
                layer.close(index);
                $.ajax({
                    type: "post",
                    url: "<%=request.getContextPath()%>/department/departmentTable/"+obj.id,
                    data: {"_method": "delete"},
                    error: function () {
                        layer.msg("操作失败",{icon:2});
                        setTimeout(function(){
                            window.location.href='<%=request.getContextPath() %>/department/departmentTable';
                        },2000);
                    },
                    success: function (data) {
                        if(data.code==1){
                            layer.msg(data.msg,{icon:1});
                            setTimeout(function(){
                                window.location.href='<%=request.getContextPath() %>/department/departmentTable';
                            },2000);
                        }else{
                            layer.msg("删除失败",{icon:2});
                            setTimeout(function(){
                                window.location.href='<%=request.getContextPath() %>/department/departmentTable';
                            },2000);
                        }
                    }
                })
            },function (index) {//取消回调
                alert("删除取消了");
                layer.close(index);
            }
        );
    }

    function upd(uObj) {
        layer.open({
            type: 2,
            title: '修改部门',
            maxmin: true,
            shadeClose: true, //点击遮罩关闭层
            area : ['800px' , '520px'],
            content: '<%=request.getContextPath() %>/department/departmentTable/'+uObj.id,
            end: function(){
                window.location.href='<%=request.getContextPath() %>/department/departmentTable';
            }
        });
    }

    var i=1000;
    //添加
    function add(pObj) {
        layer.open({
            type: 2,
            title: '添加部门',
            maxmin: true,
            shadeClose: true, //点击遮罩关闭层
            area : ['800px' , '520px'],
            content: '<%=request.getContextPath() %>/department/departmentTable/add',
            end: function(){
                window.location.href='<%=request.getContextPath() %>/department/departmentTable';
            }
        });
    }

    function print() {
        console.log(treeGrid.cache[tableId]);
        var loadIndex=layer.msg("对象已打印，按F12，在控制台查看！", {
            time:3000
            ,offset: 'auto'//顶部
            ,shade: 0
        });
    }
</script>
</body>
</html>