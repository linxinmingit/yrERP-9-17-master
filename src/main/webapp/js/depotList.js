layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table,

        //获得项目名字path，在js中可以直接用path调用
        strFullPath = window.document.location.href,
        strPath = window.document.location.pathname,
        pos = strFullPath.indexOf(strPath),
        prePath = strFullPath.substring(0, pos),
        path = strPath.substring(0, strPath.substr(1).indexOf('/') + 1)+"/";


    ;

    //仓库列表
    var tableIns = table.render({
        elem: '#depotList',
        url :path+ 'depot/depotTable/list',
        request: {
            pageName: 'currentPage' //页码的参数名称，默认：page
            ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
        where:{//需要传入的值
            "name": $("#name").val(),  //搜索的关键字
            "code": $("#code").val(),  //搜索的关键字
            "addr": $("#addr").val()   //搜索的关键字

        },
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,25,50,100],
        limit : 10,
        id : "depotListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            /*		对应实体类的属性			表头x*/
            {type:'numbers',title:'编号',width:50},
            {field: 'code', title: '仓库编号', align:"center",unresize: true},
            {field: 'name', title: '仓库名称', align:"center",unresize: true},
            {field: 'addr', title: '仓库地址', align:"center", unresize: true},
            {field: 'jobnum', title: '负责人', align:"center", unresize: true},
            {field: 'createTime', title: '创建时间', align:"center", unresize: true},
            {field: 'createEmp', title: '创建人', align:"center", unresize: true},
            {title: '操作', minWidth:150,width:150, templet:'#depotListBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        table.reload("depotListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                "name": $("#name").val(),  //搜索的关键字
                "code": $("#code").val(),  //搜索的关键字
                "addr": $("#addr").val()   //搜索的关键字
            }
        })
    });

    //添加用户
    function addDepot(){
        var index = layui.layer.open({
            title : "添加仓库",
            type : 2,
            area: ['390px', '320px'],
            content : path+"depot/depotTable/add",//发送请求
            end: function(){
                window.location.href=path+"depot/depotTable";
            }
        })
       /* layui.layer.full(index);
        window.sessionStorage.setItem("index",index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(window.sessionStorage.getItem("index"));
        })*/
    }
    $(".addNews_btn").click(function(){
        addDepot();
    })

    //批量删除
/*    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('depotListTable'),
            data = checkStatus.data,
            newsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].newsId);
            }
            layer.confirm('确定删除选中的用户？', {icon: 3, title: '提示信息'}, function (index) {
                tableIns.reload();
                layer.close(index);
            })
        }else{
            layer.msg("请选择需要删除的用户");
        }
    })*/
    //批量删除
    $(".delAll_btn").click(function(){

        var checkStatus = table.checkStatus('depotListTable'),
            data = checkStatus.data,
            newsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].id);
            }
            layer.confirm('确定删除选中的用户？', {icon: 3, title: '提示信息'}, function (index) {
                $.ajax({//删除用户
                    type : "post",
                    url : path+"depot/depotTable/"+newsId,
                    async : false,
                    data : {
                        "_method" : "DELETE"
                    },
                    traditional:true,//用传统的方式来序列化数据，那么就设置为 true	加上这个属性数组才能被识别,否则后台接受不到
                    dataType : "json",
                    success : function(data) {
                        if("0" == data.code){
                            layer.msg("删除用户失败",{icon:2});
                        }else if("1" == data.code){
                            layer.msg("删除成功",{icon:2});
                            window.location.href = path+"depot/depotTable";
                        }else{
                            layer.msg("未知错误，请联系管理员",{icon:2});
                        }
                    },
                    error : function(XMLHttpRequest, textStatus, errorThrown) {
                        alert(XMLHttpRequest.status);
                        alert(XMLHttpRequest.readyState);
                        alert(textStatus);
                    }
                });
            })
        }else{
            layer.msg("请选择需要删除的用户");
        }
    })

    //列表操作
    table.on('tool(depotList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
            layer.open({
                type: 2,
                title: '修改仓库',
                maxmin: true,
                shadeClose: true, //点击遮罩关闭层
                area : ['390px' , '340px'],
                content: path+'depot/depotTable/'+data.id,
                end: function(){
                    window.location.href = path+"depot/depotTable";
                }
            });

        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此仓库？',{icon:3, title:'提示信息'},function(index){
                tableIns.reload();
                layer.close(index);
                $.ajax({
                    type: 'post',
                    url: path+'depot/depotTable/'+data.id,//请求登录验证接口
                    dataType : 'json',
                    data: {_method:'delete'},
                    success: function(data){
                        if("1" == data.code){
                            layer.msg("删除成功",{icon:1});
                            window.location.href = path+"depot/depotTable";

                        }else{
                            layer.msg("删除仓库失败",{icon:2});
                            window.location.href = path+"depot/depotTable";
                        }
                    }
                });
                return false;
            });
        }
    });

})