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

    //用户列表
    var tableIns = table.render({
        elem: '#supplierList',
        url :path+'supplier/supplierTable/list',
        request: {
            pageName: 'currentPage' //页码的参数名称，默认：page
            ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },where:{//需要传入的值
            "name": $("#supplierName").val(),  //搜索的关键字
        },

        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,25,50,100],
        limit : 10,
        id : "supplierListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            /*		对应实体类的属性			表头x*/
            {type:'numbers',title:'编号',width:50},
            {field: 'name', title: '供应商名称', align:"center",unresize: true},
            {field: 'code', title: '编号', align:"center",unresize: true},
            {field: 'phoneNumber', title: '联系电话', align:"center", unresize: true},
            {field: 'addr', title: '地址', align:"center", unresize: true},
            {field: 'rank', title: '级别', align:"center", unresize: true},
            {title: '操作', minWidth:150,width:150, templet:'#supplierListBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        //if($(".searchVal").val() != ''){
            table.reload("supplierListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    "name": $("#supplierName").val(),  //搜索的关键字
                }
            })
        //}
    });

    //添加用户
    function addDepot(){
        var index = layui.layer.open({
            title : "添加用户",
            type : 2,
            area : ['800px' , '520px'],
            content : path+"supplier/supplierTable/add",//发送请求
            end: function(){
                window.location.href= path+"supplier/supplierTable";
            }
        })
        /*layui.layer.full(index);
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
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('supplierListTable'),
            data = checkStatus.data,
            newsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].newsId);
            }
            layer.confirm('确定删除选中的用户？', {icon: 3, title: '提示信息'}, function (index) {
                // $.get("删除文章接口",{
                //     newsId : newsId  //将需要删除的newsId作为参数传入
                // },function(data){
                tableIns.reload();
                layer.close(index);
                // })
            })
        }else{
            layer.msg("请选择需要删除的用户");
        }
    })

    //列表操作
    table.on('tool(supplierList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
            layer.open({
                type: 2,
                title: '添加菜单',
                maxmin: true,
                shadeClose: true, //点击遮罩关闭层
                area : ['800px' , '520px'],
                content: path+'supplier/supplierTable/'+data.id,
                end: function(){
                    window.location.href = path+"supplier/supplierTable";
                }
            });

        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此用户？',{icon:3, title:'提示信息'},function(index){
                tableIns.reload();
                layer.close(index);
                $.ajax({
                    type: 'post',
                    url: path+'supplier/supplierTable/'+data.id,//请求登录验证接口
                    dataType : 'json',
                    data: {_method:'DELETE'},
                    error: function() {
                        layer.msg("操作失败",{icon:2});
                        setTimeout(function(){
                            window.location.href = path+"supplier/supplierTable";
                        },1200);
                    },
                    success: function(data){
                        if("1" == data.code){
                            layer.msg(data.msg,{icon:1});
                            setTimeout(function(){
                                window.location.href = path+"supplier/supplierTable";
                            },1200);
                        }else{
                            layer.msg(data.msg,{icon:2});
                            setTimeout(function(){
                                window.location.href = path+"supplier/supplierTable";
                            },1200);
                        }
                    }
                });
                return false;
            });
        }
    });

})