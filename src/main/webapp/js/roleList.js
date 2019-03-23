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
        elem: '#roleList',
        url :path+ 'u_role/roleTable/list',
        request: {//request下面是请求后台的参数的别名,response是响应的别名
            pageName: 'currentPage' //页码的参数名称，默认：page
            ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
        where:{//需要传入的值
            "role.name": $("#rName").val()  //搜索的关键字
        },
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,25,50,100],
        limit : 10,
        id : "roleListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            /*		对应实体类的属性			表头x*/
            {type:'numbers',title:'编号',width:50},
            {field: 'code', title: '角色编号', align:"center",unresize: true},
            {field: 'name', title: '角色名', align:"center", unresize: true},
            {title: '操作', minWidth:220,width:220, templet:'#roleListBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        table.reload("roleListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where:{//需要传入的值
                "role.name": $("#rName").val()  //搜索的关键字
            }
        })
    });

    //添加用户
    function addUser(){
        //window.location.href = "user/add";


        var index = layui.layer.open({
            title : "添加权限",
            type : 2,
            area : ['390px' , '340px'],
            content : path+"u_role/roleTable/add",//发送请求
            end: function(){
                window.location.href=path+'u_role/roleTable';
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

        var checkStatus = table.checkStatus('roleListTable'),
            data = checkStatus.data,
            newsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].id);
            }
            layer.confirm('确定删除选中的用户？', {icon: 3, title: '提示信息'}, function (index) {
                $.ajax({//删除用户
                    type : "post",
                    url : path+"u_role/roleTable/"+newsId,
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
                            window.location.href = path+"u_role/roleTable";
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

    function rolePerm(data) {
        $.get(path+'u_role/roleTable/getPermission', {}, function(str){//获取后台角色列表所有角色,后台返回的json字符串str
            $.get(path+'u_role/roleTable/getPermissionById', {"uid":data.id}, function(uRoleStr){//根据用户id查询用户所拥有的角色,后台返回的json字符串uRoleStr
                var roleStrTd ="";//弹出层td
                var roleStrTr = "";	//弹出层tr
                var checkedStr = "";//控制复选框默认值的标识
                var temp = 0;
                var strTr1 = "";
                /*for (var i = 0; i < str.length; i++){
                    temp++;
                    for (var j = 0; j < uRoleStr.length; j++){
                        if(str[i].id == uRoleStr[j].id){
                            checkedStr = "checked='checked'";//因使用了弹出层样式1，拼接类的html标签都必须这样来给标签赋属性，并且靠多个变量/标识来控制需求
                        }
                    }
                    roleStrTd += "<td style='width: 120px;'><input type='checkbox'"+checkedStr+"value='"+str[i].id+"'/>"+str[i].name+"</td>";
                    //初始化checkedStr，目的是为了下一层的循环接着使用时不会出错
                    checkedStr = "";
                    if(temp%3 == 0){//取模控制弹出层表格的列数，这里是一行三个列
                        roleStrTr += "<tr>"+roleStrTd+"</tr>";
                    }
                }*/
                for (var i = 0; i < str.length; i++){
                    temp++;
                    for (var j = 0; j < uRoleStr.length; j++){
                        if(str[i].id == uRoleStr[j].id){
                            checkedStr = "checked='checked'";//因使用了弹出层样式1，拼接类的html标签都必须这样来给标签赋属性，并且靠多个变量/标识来控制需求
                        }
                    }
                    roleStrTd += "<td style='width: 120px;'><input type='checkbox'"+checkedStr+"value='"+str[i].id+"'/>"+str[i].name+"</td>";
                    //初始化checkedStr，目的是为了下一层的循环接着使用时不会出错
                    if(temp%5 == 0){//取模控制弹出层表格的列数，这里是一行三个列
                        roleStrTr += "<tr>"+roleStrTd+"</tr>";
                        //因为执行到这里，已经把roleStrTd的内容追加给了roleStrTr,必须初始化roleStrTd，目的是为了实现弹出层表格列数的控制
                        roleStrTd = "";
                    }else{
                        if((str.length - temp) <= (str.length)%5){//最后一行
                            strTr1+="<td style='width: 120px;'><input type='checkbox'"+checkedStr+"value='"+str[i].id+"'/>"+str[i].name+"</td>";
                            if((str.length - temp) == 0){
                                roleStrTr += "<tr>"+strTr1+"</tr>";
                            }
                        }
                    }
                    checkedStr = "";
                }
                //角色的弹出层
                layer.open({
                    id: 'LAY_updateRole', //设定一个id，防止重复弹出
                    type: 1,
                    title:'角色设置',
                    btnAlign: 'c',
                    area: ['800px','250px'],//宽、高
                    content: "<b>角色名称:</b><br><table align='center'>"+roleStrTr+"</table>",
                    btn: ['确认', '重置']
                    ,yes: function(index, layero){//layero就是弹出层的html对象
                        //按钮【按钮一】的回调
                        var id = data.id/*+"#"*/;
                        var roleIds = [];
                        var obj = $(layero).find("table").find("tr").find(":checkbox");
                        for(var k in obj){
                            if(obj[k].checked){
                                //userRoleStr += obj[k].id/*+","*/;
                                roleIds.push(obj[k].value);
                            }
                        }
                        $.ajax({
                            type: 'get',
                            url: path+'u_role/roleTable/setPermissions',//后台往user_role表中插入数据
                            dataType : 'json',
                            data: {
                                "id":id,
                                "roleIds":roleIds
                            },//userRoleStr是用户拥有的角色的id拼起来的字符串
                            traditional:true,//用传统的方式来序列化数据，那么就设置为 true	加上这个属性数组才能被识别,否则后台接受不到
                            success: function(str){

                                if("0" == str.code){
                                    top.layer.close(index);
                                    tableIns.reload("roleList",{
                                        page: {
                                            curr: 1 //重新从第 1 页开始
                                        }
                                    })
                                    setTimeout(top.layer.msg(str.msg,{icon:1}),1000);
                                }else if("1" == str.code){
                                    top.layer.close(index);
                                    tableIns.reload("roleList",{
                                        page: {
                                            curr: 1 //重新从第 1 页开始
                                        }
                                    })
                                    setTimeout(top.layer.msg(str.msg,{icon:1}),1000);
                                }else{
                                    top.layer.close(index);
                                    tableIns.reload("roleList",{
                                        page: {
                                            curr: 1 //重新从第 1 页开始
                                        }
                                    })
                                    setTimeout(layer.msg("未知错误，请联系管理员",{icon:2}),1000);
                                }
                            }
                        });

                    }
                    ,btn2: function(index, layero){
                        //按钮【按钮二】的回调
                        rolePerm(data);
                    }
                    ,cancel: function(){
                        //右上角关闭回调
                        tableIns.reload("roleList",{//刷新指定id的列表
                            page: {
                                curr: 1 //重新从第 1 页开始
                            }
                        })
                        //return false 开启该代码可禁止点击该按钮关闭
                    }
                });
            });
        });
    }

    //列表操作
    table.on('tool(roleList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
            var index = layui.layer.open({
                title : "添加权限",
                type : 2,
                area : ['390px' , '340px'],
                content : path+"u_role/roleTable/"+data.id,//发送请求
                end: function(){
                    window.location.href=path+'u_role/roleTable';
                }
            })
        }/*else if(layEvent === 'auth'){
            layer.open({
                id: 'LAY_indepAuth', //设定一个id，防止重复弹出
                type: 2,
                title:'角色权限',
                area: ['800px','500px'],//宽、高
                content: path+'u_role/roleTable/'+data.id,//跳到用户独立权限页面
                end : function(index, layero){
                    tableIns.reload("userList",{
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    })
                }
            });
        }*/else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此用户？',{icon:3, title:'提示信息'},function(index){
                tableIns.reload();
                layer.close(index);
                $.ajax({
                    type: 'post',
                    url: path+'u_role/roleTable/'+data.id,//请求登录验证接口
                    dataType : 'json',
                    data: {
                        _method:'delete'
                    },
                    error: function() {
                        layer.msg("操作失败",{icon:2});
                        setTimeout(function(){
                            window.location.href = path+"u_role/roleTable";
                        },1200);
                    },
                    success: function(data){
                        if("1" == data.code){
                            layer.msg(data.msg,{icon:1});
                            setTimeout(function(){
                                window.location.href = path+"u_role/roleTable";
                            },1200);
                        }else{
                            layer.msg(data.msg,{icon:2});
                            setTimeout(function(){
                                window.location.href = path+"u_role/roleTable";
                            },1200);
                        }
                        /*layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();*/
                    }
                });
                return false;
            });
        }else if(layEvent === 'auth'){//角色赋权限
            //Ajax请求，动态控制角色弹出层的回显
            rolePerm(data);
        }
    });

})