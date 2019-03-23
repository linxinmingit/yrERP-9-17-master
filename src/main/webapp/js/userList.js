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
        elem: '#userList',
        url :path+ 'u_user/userTable/list',
        request: {//request下面是请求后台的参数的别名,response是响应的别名
            pageName: 'currentPage' //页码的参数名称，默认：page
            ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
        where:{//需要传入的值
            "user.name": $("#userName").val(),  //搜索的关键字
            "user.depaCode": $("#depaCode").val(),  //搜索的关键字
            "minAge": $("#minAge").val(),  //搜索的关键字   这里设置不进去，因为类型不匹配，但是输入数字后便可以进入
            "maxAge": $("#maxAge").val() //搜索的关键字
        },
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,25,50,100],
        limit : 10,
        id : "userListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            /*		对应实体类的属性			表头x*/
            {type:'numbers',title:'编号',width:50},
            {field: 'name', title: '用户名', align:"center",unresize: true},
            /*这里获取的只是头像的路径，但是在前台是需要显示图片的，所以对headUrl进行处理，如果返回的数据需要处理都是用templet:function(d){ return '处理的数据' } */
            {field: 'photo', title: '头像',  align:'center',templet:function(d){
                return '<img style="width: 28px;height: 28px;"  src='+d.photo+'  class="layui-upload-img layui-circle userFaceBtn userAvatar"/>';
            }},
            {field: 'jobNum', title: '工号', align:"center", unresize: true},
            {field: 'depaCode', title: '部门', align:"center", unresize: true},
            {field: 'sex', title: '性别', align:"center", unresize: true,templet:function(d){
                return d.sex == "0" ? "女" :"男";
            }},
            {field: 'age', title: '年龄', align:"center", unresize: true},
            {field: 'birthday', title: '生日', align:"center", unresize: true},
            {field: 'phoneNumber', title: '电话', align:"center", unresize: true},
            {field: 'addr', title: '地址', align:"center", unresize: true},
            //{field: 'status', title: '状态', align:"center", unresize: true},
            {field: 'states', title: '用户状态',  align:'center',templet:function(d){
                return d.states == "0" ? "限制使用" : "正常使用";
            }},
            {title: '操作', minWidth:386, templet:'#userListBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        table.reload("userListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                "user.name": $("#userName").val(),  //搜索的关键字
                "user.depaCode": $("#depaCode").val(),  //搜索的关键字
                "minAge": $("#minAge").val(),  //搜索的关键字
                "maxAge": $("#maxAge").val() //搜索的关键字
            }
        })
    });

    //添加用户
    function addUser(){
        var index = layui.layer.open({
            title : "添加用户",
            type : 2,
            content : path+"u_user/userTable/add",//发送请求
            area: ['500px', '550px'],
            end: function(){
                window.location.href=path+'u_user/userTable';
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
        addUser();
    })

    //批量删除
    $(".delAll_btn").click(function(){

        var checkStatus = table.checkStatus('userListTable'),
            data = checkStatus.data,
            newsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].id);
            }
            layer.confirm('确定删除选中的用户？', {icon: 3, title: '提示信息'}, function (index) {
                $.ajax({//删除用户
                    type : "post",
                    url : path+"u_user/userTable/"+newsId,
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
                            window.location.href = path+"u_user/userTable";
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
    table.on('tool(userList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
            layer.open({
                type: 2,
                title: '修改用户',
                maxmin: true,
                shadeClose: true, //点击遮罩关闭层
                area: ['500px', '550px'],
                content: path+"u_user/userTable/"+data.id,
                end: function(){
                    window.location.href = path+"u_user/userTable";
                }
            });
            
        }else if(layEvent === 'auth'){
        	layer.open({
       		 id: 'LAY_indepAuth', //设定一个id，防止重复弹出
                type: 2,
                title:'添加独立权限',
                area: ['800px','500px'],//宽、高
                content: path+'user/gotoIndePermission/'+data.id,//跳到用户独立权限页面
                end : function(index, layero){ 
               	 tableIns.reload("userList",{
                         page: {
                             curr: 1 //重新从第 1 页开始
                         }
                     })
               	}  
       		});
        }else if(layEvent === 'setRole'){//角色设置
        	//Ajax请求，动态控制角色弹出层的回显
        	$.get(path+'u_user/userTable/getRole', {}, function(str){//获取后台角色列表所有角色,后台返回的json字符串str
            	$.get(path+'u_user/userTable/getRoleById', {"uid":data.id}, function(uRoleStr){//根据用户id查询用户所拥有的角色,后台返回的json字符串uRoleStr
        		var roleStrTd ="";//弹出层td
        		var roleStrTr = "";	//弹出层tr
        		var checkedStr = "";//控制复选框默认值的标识
        		var temp = 0;
        		for (var i = 0; i < str.length; i++){
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
						//因为执行到这里，已经把roleStrTd的内容追加给了roleStrTr,必须初始化roleStrTd，目的是为了实现弹出层表格列数的控制
						roleStrTd = "";
					}
				}
        		//角色的弹出层
	        	layer.open({
	          		 id: 'LAY_updateRole', //设定一个id，防止重复弹出
	                   type: 1,
	                   title:'角色设置',
	                   btnAlign: 'c',
	                   area: ['400px','230px'],//宽、高
	                   content: "<b>角色名称:</b><br><table align='center'>"+roleStrTr+"</table>",
	                   btn: ['确认', '取消']
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
		             			url: path+'u_user/userTable/setRoles',//后台往user_role表中插入数据
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
   					tableIns.reload("roleList",{//刷新指定id的列表
   		                  page: {
   		                      curr: 1 //重新从第 1 页开始
   		                  }
   		              })
       		        	
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
        }else if(layEvent === 'usable'){ //启用禁用
            var _this = $(this),
                usableText = "是否确定禁用此用户？",
                btnText = "已禁用";
            if(_this.text()=="已禁用"){
                usableText = "是否确定启用此用户？",
                    btnText = "已启用";
            }
            layer.confirm(usableText,{
                icon: 3,
                title:'系统提示',
                cancel : function(index){
                    layer.close(index);
                }
            },function(index){//确认进入
                _this.text(btnText);
                layer.close(index);
                var id = data.id;
                var state = data.states;
                $.ajax({//改变状态
                    type : "get",
                    url : path+"/u_user/userTable/updateState",
                    async : false,
                    data : {
                        "id" : id,
                        "state" : state
                    },
                    dataType : "json",
                    success : function(data) {
                        if("0" == data.code){
                            layer.msg("修改失败",{icon:2});
                        }else if("1" == data.code){
                            layer.msg("修改成功",{icon:2});
                            //window.location.href = path+"u_user/userTable";
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
                if(btnText == "已禁用"){//表示已经禁用
                    $(_this).attr("class","layui-btn layui-btn-xs layui-btn-danger");
                    var index = $(_this).parent("div[class='layui-table-cell laytable-cell-1-10']").parent("td").parent("tr").attr("data-index");//找到下标
                    $(_this).parents("div[class='layui-table-fixed layui-table-fixed-r']").siblings("div[class='layui-table-body layui-table-main']").find("tr[data-index="+index+"]").children("td[data-field='status']").children("div").text("限制使用");//修改text
                }else{//表示已经启用
                    $(_this).attr("class", "layui-btn layui-btn-xs");//修改颜色
                    var index = $(_this).parent("div[class='layui-table-cell laytable-cell-1-10']").parent("td").parent("tr").attr("data-index");
                    $(_this).parents("div[class='layui-table-fixed layui-table-fixed-r']").siblings("div[class='layui-table-body layui-table-main']").find("tr[data-index="+index+"]").children("td[data-field='status']").children("div").text("正常使用");//修改text
                }
                tableIns.reload();
            },function(index){
                layer.close(index);
            });
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此用户？',{icon:3, title:'提示信息'},function(index){
            	tableIns.reload();
                layer.close(index);
            	$.ajax({
        			type: 'post',
        			url: path+'u_user/userTable/'+data.id,//请求登录验证接口
        			dataType : 'json',
        			data: {
                        _method:'delete'
                    },
        			success: function(data){
                        if("0" == data.code){
                            layer.msg("删除用户失败",{icon:2});
                        }else if("1" == data.code){
                            layer.msg("删除成功",{icon:2});
                            window.location.href = path+"u_user/userTable";
                        }else{
                            layer.msg("未知错误，请联系管理员",{icon:2});
                        }
        			}
        		});
            	//return false;
            });
        }
    });
})