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
        elem: '#suppWaresList',
        url :path+ 'supp_wares/supplierTable/list',
        request: {
            pageName: 'currentPage' //页码的参数名称，默认：page
            ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
        where : {//需要传入的值
            "name": $("#wareName").val(),  //搜索的关键字
            "type": $("#wareType").val(),  //搜索的关键字
            /* "minUnitPrice": $("#minUnitPrice").val(),  //搜索的关键字
             "maxUnitPrice": $("#maxUnitPrice").val()  //搜索的关键字*/
        },
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,25,50,100],
        limit : 10,
        id : "suppWaresListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            /*		对应实体类的属性			表头x*/
            {type:'numbers',title:'编号',width:50},
            {field: 'name', title: '商品名', align:"center",unresize: true},
            /*{field: 'supplierWare.supp_code', title: '供应商编号', align:"center",unresize: true},*/
            /*这里获取的只是头像的路径，但是在前台是需要显示图片的，所以对headUrl进行处理，如果返回的数据需要处理都是用templet:function(d){ return '处理的数据' } */
            {field: 'supplierWare.suppPhoto', title: '图片',  align:'center',templet:function(d){
                    return '<img style="width: 28px;height: 28px;"  src="'+path+"/supp_wares/supplierTable/icons/"+d.id+'"  class="layui-upload-img layui-circle userFaceBtn userAvatar"/>';
                }},

            {field: 'code', title: '商品编号', align:"center", unresize: true},
            {field: 'type', title: '商品类型', align:"center", unresize: true},
            {field: 'brand', title: '品牌', align:"center", unresize: true},
            {field: 'unitPrice', title: '商品单价', align:"center", unresize: true},
            {field: 'totalInventory', title: '供应商库存', align:"center", unresize: true},
            {field: 'addr', title: '产地', align:"center", unresize: true},
            {title: '操作', minWidth:150,width:150, templet:'#suppWaresListBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        table.reload("suppWaresListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                "name": $("#wareName").val(),  //搜索的关键字
                /* "type": $("#wareType").val(),  //搜索的关键字
                 "minUnitPrice": $("#minUnitPrice").val(),  //搜索的关键字
                 "maxUnitPrice": $("#maxUnitPrice").val() //搜索的关键字*/
            }
        })
    });

    //添加用户
    function addUser(){
        //window.location.href = "user/add";
        var index = layui.layer.open({
            title : "添加商品",
            type : 2,
            area : ['800px' , '520px'],
            content : path+"supp_wares/supplierTable/add",//发送请求
            end: function(){
                window.location.href= path+"supp_wares/supplierTable";
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
        addUser();
    })

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('suppWaresListTable'),
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
    table.on('tool(suppWaresList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
            layer.open({
                type: 2,
                title: '修改用户',
                maxmin: true,
                shadeClose: true, //点击遮罩关闭层
                area: ['800px', '520px'],
                content: path+"supp_wares/supplierTable/"+data.id,
                end: function(){
                    window.location.href =  path+"supp_wares/supplierTable";
                }
            });
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此用户？',{icon:3, title:'提示信息'},function(index){
                layer.close(index);
                $.ajax({
                    type: 'post',
                    url: path+'supp_wares/supplierTable/'+data.id,//删除请求后台的接口
                    dataType : 'json',
                    data: {_method:'delete'},
                    error: function() {
                        layer.msg("操作失败",{icon:2});
                        tableIns.reload();
                    },
                    success: function(data){
                        if("1" == data.code){
                            layer.msg(data.msg,{icon:1});
                            tableIns.reload();
                        }else{
                            layer.msg(data.msg,{icon:2});
                            tableIns.reload();
                        }
                    }
                });
                return false;
            });
        }
    });

})