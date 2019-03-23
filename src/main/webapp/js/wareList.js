layui.use(['form', 'layer', 'table', 'laytpl'], function () {
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
        path = strPath.substring(0, strPath.substr(1).indexOf('/') + 1) + "/";


    ;

    //用户列表
    var tableIns = table.render({
        elem: '#userList',
        url: path + 'wares/waresTable/list',
        request: {
            pageName: 'currentPage' //页码的参数名称，默认：page
            , limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
        where: {
            "name": $("#name").val(),  //搜索的关键字
            "type": $("#type").val(),  //搜索的关键字
        },
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 25, 50, 100],
        limit: 10,
        id: "userListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            /*		对应实体类的属性			表头x*/
            {field: 'type', title: '商品类别', align: "center", unresize: true},
            {field: 'name', title: '商品名', align: "center", unresize: true},
            /*这里获取的只是头像的路径，但是在前台是需要显示图片的，所以对headUrl进行处理，如果返回的数据需要处理都是用templet:function(d){ return '处理的数据' } */
            {
                field: 'warePhoto', title: '头像', align: 'center', templet: function (d) {
                    return '<img style="width: 28px;height: 28px;"  src="' + path + "/wares/waresTable/icons/" + d.id + '"  class="layui-upload-img layui-circle userFaceBtn userAvatar"/>';
                }
            },

            {field: 'brand', title: '品牌', align: "center", unresize: true},
            {field: 'inUnitPrice', title: '采购单价', align: "center", unresize: true},
            {field: 'outUnitPrice', title: '销售单价', align: "center", unresize: true},
            {field: 'barCode', title: '条码', align: "center", unresize: true},
            {field: 'totalInventory', title: '库存总量', align: "center", unresize: true},
            {field: 'addr', title: '产地', align: "center", unresize: true},
            {field: 'createEmp', title: '创建人', align: "center", unresize: true},
            /* {field: 'remark', title: '备注', align:"center", unresize: true},*/
            //{field: 'status', title: '状态', align:"center", unresize: true},
            /*{field: 'createEmp', title: '用户状态',  align:'center',templet:function(d){
                    d.status == "0" ? $("#abc").text("禁用") : $("#abc").text("启用");
                    return d.status == "0" ? "限制使用" : "正常使用";
                }},*/
            {title: '操作', minWidth: 150, width: 150, templet: '#userListBar', fixed: "right", align: "center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click", function () {
        if ($(".searchVal").val() != '') {
            table.reload("userListTable", {
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    "name": $("#name").val(),  //搜索的关键字
                    "type": $("#type").val(),  //搜索的关键字
                }
            })
        } else {
            window.location.href = path + "wares/waresTable";
        }
    });

    //添加商品
    function addUser() {
        var index = layui.layer.open({
            title: "添加商品",
            type: 2,
            area: ['800px', '520px'],
            content: path + "wares/waresTable/add",//发送请求
            end: function () {
                window.location.href = path + 'wares/waresTable';
            }
        })
        /* layui.layer.full(index);
         window.sessionStorage.setItem("index",index);
         //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
         $(window).on("resize",function(){
             layui.layer.full(window.sessionStorage.getItem("index"));
         })*/
    }

    $(".addNews_btn").click(function () {
        addUser();
    })

    //批量删除
    $(".delAll_btn").click(function () {
        var checkStatus = table.checkStatus('userListTable'),
            data = checkStatus.data,
            newsId = [];
        if (data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].newsId);
            }
            layer.confirm('确定删除选中的商品？', {icon: 3, title: '提示信息'}, function (index) {
                $.ajax({//删除用户
                    type: "post",
                    url: path + "wares/waresTable/" + newsId,
                    async: false,
                    data: {
                        "_method": "DELETE"
                    },
                    traditional: true,//用传统的方式来序列化数据，那么就设置为 true	加上这个属性数组才能被识别,否则后台接受不到
                    dataType: "json",
                    success: function (data) {
                        if ("2" == data.code) {
                            layer.msg("删除商品失败", {icon: 2});
                        } else if ("1" == data.code) {
                            layer.msg("删除商品成功", {icon: 1});
                            window.location.href = path + "u_user/userTable";
                        } else {
                            layer.msg("未知错误，请联系管理员", {icon: 2});
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        alert(XMLHttpRequest.status);
                        alert(XMLHttpRequest.readyState);
                        alert(textStatus);
                    }
                });
            })
        } else {
            layer.msg("请选择需要删除的商品");
        }
    })

    //列表操作
    table.on('tool(userList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;
        if (layEvent === 'edit') { //编辑
            layer.open({
                type: 2,
                title: '修改商品',
                maxmin: true,
                shadeClose: true, //点击遮罩关闭层
                area: ['800px', '520px'],
                content: path + "wares/waresTable/" + data.id,
                end: function () {
                    window.location.href = path + 'wares/waresTable';
                }
            });
        } else if (layEvent === 'del') { //删除
            layer.confirm('确定删除此商品？', {icon: 3, title: '提示信息'}, function (index) {
                tableIns.reload();
                layer.close(index);
                $.ajax({
                    type: 'post',
                    url: path + 'wares/waresTable/' + data.id,//请求登录验证接口
                    dataType: 'json',
                    data: {_method: 'delete'},
                    error: function () {
                        layer.msg("操作失败", {icon: 2});
                        setTimeout(function () {
                            window.location.href = path + "wares/waresTable";
                        }, 1200);
                    },
                    success: function (data) {
                        if ("1" == data.code) {
                            layer.msg(data.msg, {icon: 1});
                            setTimeout(function () {
                                window.location.href = path + "wares/waresTable";
                            }, 1200);
                        } else {
                            layer.msg(data.msg, {icon: 2});
                            setTimeout(function () {
                                window.location.href = path + "wares/waresTable";
                            }, 1200);
                        }
                    }
                });

                return false;
            });
        }
    });

})