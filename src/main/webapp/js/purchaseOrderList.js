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
        path = strPath.substring(0, strPath.substr(1).indexOf('/') + 1) + "/";
    ;

    //用户列表
    var tableIns = table.render({
        elem: '#purchaseList',
        url: path + 'requisition/requisitionTable/list',
        request: {//request下面是请求后台的参数的别名,response是响应的别名
            pageName: 'currentPage' //页码的参数名称，默认：page
            , limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
        where: {//需要传入的值
            "purchaseCode": $("#purchaseCode").val(),
            "purchaseWareCode": $("#purchaseWareCode").val(),
            "purchaseOrder.status": $("#rStates").val()
        },
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 25, 50, 100],
        limit: 10,
        id: "purchaseTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            /*		对应实体类的属性			表头x*/
            {type: 'numbers', title: '编号', width: 50},
            {
                title: '申请人姓名', minWidth: 100, align: "center", unresize: true, templet: function (d) {
                    return d.user.name;
                }
            },
            {
                title: '申请人电话', minWidth: 130, align: "center", unresize: true, templet: function (d) {
                    return d.user.phoneNumber;
                }
            },
            {
                title: '订单编号', align: "center", minWidth: 150, unresize: true, templet: function (d) {
                    return d.purchaseOrder.code;
                }
            },
            {
                title: '采购部门', align: "center", unresize: true, templet: function (d) {
                    return d.department.name;
                }
            },
            {
                title: '供应商', align: "center", unresize: true, templet: function (d) {
                    return d.supplier.name;
                }
            },
            {
                title: '商品名称', align: "center", unresize: true, templet: function (d) {
                    return d.supplierWares.name;
                }
            },
            {
                title: '商品类型', align: "center", unresize: true, templet: function (d) {
                    return d.supplierWares.type;
                }
            },
            {
                title: '商品品牌', align: "center", unresize: true, templet: function (d) {
                    return d.supplierWares.brand;
                }
            },
            {
                title: '采购数量', align: "center", unresize: true, templet: function (d) {
                    return d.purchaseOrder.purchaseNumber;
                }
            },
            {
                title: '单价', align: "center", unresize: true, templet: function (d) {
                    return d.purchaseOrder.unitPrice;
                }
            },
            {
                title: '总价', align: "center", unresize: true, templet: function (d) {
                    return d.purchaseOrder.totalPrice;
                }
            },

            {
                title: '收货人', align: "center", unresize: true, templet: function (d) {
                    return d.purchaseOrder.consignee;
                }
            },
            {
                title: '收货仓库', align: "center", unresize: true, templet: function (d) {
                    return d.depot.name;
                }
            },
            {
                title: '订单状态', align: "center", unresize: true, templet: function (d) {
                    if (d.purchaseOrder.status == 0) {
                        return "驳回";
                    } else if (d.purchaseOrder.status == 1) {
                        return "交易成功";
                    } else if (d.purchaseOrder.status == 2) {
                        return "待审核";
                    } else if (d.purchaseOrder.status == 3) {
                        return "申请退货";
                    } else if (d.purchaseOrder.status == 4) {
                        return "退货成功";
                    }
                }
            },
            {title: '操作', minWidth:150,width:150, templet:'#purchaseBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click", function () {
        table.reload("purchaseTable", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                "purchaseCode": $("#purchaseCode").val(),
                "purchaseWareCode": $("#purchaseWareCode").val(),
                "purchaseOrder.status": $("#rStates").val()
            }
        })
    });

    //添加采购订单
    function addUser() {
        //window.location.href = "user/add";
        var index = layui.layer.open({
            title: "添加用户",
            type: 2,
            area: ['390px', '320px'],
            //area: ['700px', '750px'],
            content: path + "requisition/requisitionTable/add",//发送请求
            end: function () {
                window.location.href = path + 'requisition/requisitionTable';
            }
        })
        /*layui.layer.full(index);
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
        var checkStatus = table.checkStatus('purchaseTable'),
            data = checkStatus.data,
            newsId = [];
        if (data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].purchaseOrder.id);
            }
            layer.confirm('确定删除选中的用户？', {icon: 3, title: '提示信息'}, function (index) {
                $.ajax({
                    type: "post",
                    url: path + "requisition/requisitionTable/" + newsId,
                    async: false,
                    data: {
                        "_method": "DELETE"
                    },
                    traditional: true,//用传统的方式来序列化数据，那么就设置为 true	加上这个属性数组才能被识别,否则后台接受不到
                    dataType: "json",
                    success: function (data) {
                        if ("0" == data.code) {
                            layer.msg("删除用户失败", {icon: 2});
                        } else if ("1" == data.code) {
                            layer.msg("删除成功", {icon: 1});
                            window.location.href = path + "requisition/requisitionTable";
                        } else {
                            layer.msg("未知错误，请联系管理员", {icon: 2});
                        }
                    },
                    /*error : function(XMLHttpRequest, textStatus, errorThrown) {
                        alert(XMLHttpRequest.status);
                        alert(XMLHttpRequest.readyState);
                        alert(textStatus);
                    }*/
                });
            })
        } else {
            layer.msg("请选择需要删除的用户");
        }
    });

    //列表操作
    table.on('tool(purchaseList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;
        if (layEvent === 'edit') { //编辑
            var index = layui.layer.open({
                title: "添加用户",
                type: 2,
                /*area: ['390px', '320px'],*/
                area: ['390px', '400px'],
                content: path + "requisition/requisitionTable/" + data.purchaseOrder.id,//发送请求
                end: function () {
                    window.location.href = path + 'requisition/requisitionTable';
                }
            })
        } else if (layEvent === 'del') { //删除
            layer.confirm('确定删除此用户？', {icon: 3, title: '提示信息'}, function (index) {
                tableIns.reload();
                layer.close(index);
                $.ajax({
                    type: 'post',
                    url: path + 'requisition/requisitionTable/' + data.purchaseOrder.id,//请求登录验证接口
                    dataType: 'json',
                    data: {
                        _method: 'delete'
                    },
                    error: function () {
                        layer.msg("删除成功", {icon: 2});
                        setTimeout(function () {
                            window.location.href = path + "requisition/requisitionTable";
                        }, 1200);
                    },
                    success: function (data) {
                        if ("1" == data.code) {
                            layer.msg(data.msg, {icon: 1});
                            setTimeout(function () {
                                window.location.href = path + "requisition/requisitionTable";
                            }, 1200);
                        } else {
                            layer.msg(data.msg, {icon: 2});
                            setTimeout(function () {
                                window.location.href = path + "requisition/requisitionTable";
                            }, 1200);
                        }
                    }
                });
                return false;
            });
        }
    });

});
