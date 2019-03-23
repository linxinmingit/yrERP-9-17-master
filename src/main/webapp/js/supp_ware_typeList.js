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
        elem: '#supplierList',
        url: path + 'suppWareType/suppWareTypeTable/list',
        /* parseData: function(res){ //res 即为原始返回的数据
             return {
                 //"code": res.status, //解析接口状态
                 //"msg": res.message, //解析提示文本
                 "count": res.totalRecord, //解析数据长度
                 "data": res.pageDataList //解析数据列表
             };
         },*/
        request: {
            pageName: 'currentPage' //页码的参数名称，默认：page
            , limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
        where: {//需要传入的值
            "name": $("#type").val() //搜索的关键字
        },
        /*response: {
            // statusName: 'status' //规定数据状态的字段名称，默认：code
            //,statusCode: 200 //规定成功的状态码，默认：0
            //,msgName: 'hint' //规定状态信息的字段名称，默认：msg
            countName: 'totalRecord' //规定数据总数的字段名称，默认：count
            ,dataName: 'pageData' //规定数据列表的字段名称，默认：data
        },*/
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 25, 50, 100],
        limit: 10,
        id: "supplierListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            /*		对应实体类的属性			表头x*/
            {field: 'name', edit: 'name', width: '15%', title: '类型'},
            {field: 'code', width: '15%', title: '类型编号'},
            {field: 'supCode', width: '20%', title: '上级商品类别编号'},
            {field: 'createTime', width: '20%', title: '创建时间'},
            {field: 'createEmp', width: '15%', title: '创建人'},
            {title: '操作', minWidth: 150, width: 150, templet: '#supplierListBar', fixed: "right", align: "center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click", function () {
        if ($(".searchVal").val() != '') {
            table.reload("supplierListTable", {
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    "name": $("#type").val(),  //搜索的关键字
                }
            })
        } else {
            window.location.href = path + "ware_type/ware_typeTable";
        }
    });

    //添加用户
    function addDepot() {
        var index = layui.layer.open({
            title: "添加供应商品类型",
            type: 2,
            area: ['800px', '520px'],
            content: path + "suppWareType/suppWareTypeTable/add",//发送请求
            end: function () {
                window.location.href = path + 'suppWareType/suppWareTypeTable';
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
        addDepot();
    })

    //批量删除
    $(".delAll_btn").click(function () {
        var checkStatus = table.checkStatus('supplierListTable'),
            data = checkStatus.data,
            newsId = [];
        if (data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].newsId);
            }
            layer.confirm('确定删除选中的商品类型？', {icon: 3, title: '提示信息'}, function (index) {
                $.ajax({//删除用户
                    type: "post",
                    url: path + "suppWareType/suppWareTypeTable/" + newsId,
                    async: false,
                    data: {
                        "_method": "DELETE"
                    },
                    traditional: true,//用传统的方式来序列化数据，那么就设置为 true	加上这个属性数组才能被识别,否则后台接受不到
                    dataType: "json",
                    success: function (data) {
                        if ("2" == data.code) {
                            layer.msg("删除商品类型失败", {icon: 2});
                        } else if ("1" == data.code) {
                            layer.msg("删除商品类型成功", {icon: 1});
                            window.location.href = path + "suppWareType/suppWareTypeTable";
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
            layer.msg("请选择需要删除的商品类型");
        }
    })

    //列表操作
    table.on('tool(supplierList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;
        if (layEvent === 'edit') { //编辑
            layer.open({
                type: 2,
                title: '添加菜单',
                maxmin: true,
                shadeClose: true, //点击遮罩关闭层
                area: ['800px', '520px'],
                content: path + 'suppWareType/suppWareTypeTable/' + data.id,
                end: function () {
                    window.location.href = path + "suppWareType/suppWareTypeTable";
                }
            });

        } else if (layEvent === 'del') { //删除
            layer.confirm('确定删除此商品类型？', {icon: 3, title: '提示信息'}, function (index) {
                tableIns.reload();
                layer.close(index);
                $.ajax({
                    type: 'post',
                    url: path + 'suppWareType/suppWareTypeTable/' + data.id,//请求登录验证接口
                    dataType: 'json',
                    data: {_method: 'delete'},
                    error: function () {
                        layer.msg("操作失败", {icon: 2});
                        setTimeout(function () {
                            window.location.href = path + "/suppWareType/suppWareTypeTable";
                        }, 1200);
                    },
                    success: function (data) {
                        if ("1" == data.code) {
                            layer.msg(data.msg, {icon: 1});
                            setTimeout(function () {
                                window.location.href = path + "suppWareType/suppWareTypeTable";
                            }, 1200);
                        } else {
                            layer.msg(data.msg, {icon: 2});
                            setTimeout(function () {
                                window.location.href = path + "suppWareType/suppWareTypeTable";
                            }, 1200);
                        }
                    }
                });
                return false;
            });
        }
    });

})