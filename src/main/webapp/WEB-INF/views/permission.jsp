<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %><!-- 引入form标签的标签库 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
    <link href="../../scripts/layui/css/layui.css" rel="stylesheet" />
    <script src="../../scripts/jquery-1.10.2.min.js"></script>
    <script src="../../scripts/layui/layui.js"></script>
</head>
<script type="text/javascript">

$(document).ready(function(){
	
})

	function selectAll(aa){
		//全选  
	    $(aa).parent().parent().find("td").eq(1).find(":checkbox").prop("checked", true);
	}
	
	function unSelect(bb){
		//全不选
		$(bb).parent().parent().find("td").eq(1).find(":checkbox").prop("checked", false);
	}
	function reverse(cc){
		//反选 
		$(cc).parent().parent().find("td").eq(1).find(":checkbox").each(function(i,o){
			   $(o).prop("checked",!$(o).prop("checked"));
		  });
	}
	
	//提交
	function getValue(roleId){
	  	var str = roleId+"#";
	  	var btns = $("#demo").find("tr");   // 获得所有行
		for(var i = 2;i<btns.length;i++) //遍历所得的行，然后每行应选择器选中修改按钮，
		{
		  	obj = $(btns[i]).find("td").eq(1).find(":checkbox");
			    for(k in obj){
			        if(obj[k].checked)
			        str += obj[k].value+",";
			    }
			    
		}
	  	alert(str);
	  	
	  	$.ajax({
			    type: "POST",
	            url:"<%=request.getContextPath()%>/permission/updateRole_PermissionTable",
	            data : {"str":str},
	            error: function(request) {
		
	            },
	            success: function() {
		
	            }
	          });
	  	
	  	
	  	
	}
</script>
<body>
    <!-- <div class="layui-btn-group">
        <a class="layui-btn" lay-filter="add">添加一个节点</a>
        <a class="layui-btn" lay-filter="adds">添加多个节点</a>
        <a class="layui-btn" lay-filter="addChild">添加子节点</a>
        <a class="layui-btn" lay-filter="update">修改节点</a>
        <a class="layui-btn" lay-filter="delete">删除节点及其子节点</a>
    </div> -->
    <button class="layui-btn layui-btn-xs" onclick="getValue(${roleId })">提交</button>
    <div id="demo" class="grid">
    	<input type="hidden" id="roleId" value="${roleId }">
    </div>
    <script id="view" type="text/html">
        <table class="layui-table">
            <thead>
                <tr>
                    <th>菜单名称</th>
                    <th>权限节点</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
					
                {{# layui.each(d.rows,function(index,r){ }}
                <tr>
                    <td><i class="layui-icon">{{r.pic}}</i>{{r.name}}<input type="hidden" name="id" value="{{r.id}}"/></td>
                    <td>
					
					{{#  if(r.pid != 0){ }}
						{{# layui.each(d.pStr,function(index,p){ }}
							{{#  if(r.id == p.m_id){ }}
								{{#  if(p.url == r.url){ }}
								<input type="checkbox" value="{{p.id}}"/>列表
								{{#  } }}

								{{#  if(p.url == "add"){ }}
								<input type="checkbox" value="{{p.id}}"/>新增
								{{#  } }}

								{{#  if(p.url == "delete"){ }}
								<input type="checkbox" value="{{p.id}}"/>编辑
								{{#  } }}

								{{#  if(p.url == "update"){ }}
								<input type="checkbox" value="{{p.id}}"/>删除
								{{#  } }}
							{{#  } }} 
						{{# }); }}
					 {{#  } }} 
					{{#  if(r.pid == 0){ }}
						<p>{{r.url}}</p> 
					{{#  } }} 
					</td>
					{{#  if(r.pid != 0){ }}
						
						<td>
							{{# layui.each(d.pStr,function(index,p){ }}
								{{#  if(r.id == p.m_id){ }}
									<input type="button" value="全选" onclick="selectAll(this)">
									<input type="button" value="全不选" onclick="unSelect(this)">
									<input type="button" value="反选" onclick="reverse(this)">
								{{# return true; } }}
							{{# }); }}
						</td>
                	{{#  } }}
				</tr>
                {{# }); }}
            </tbody>
        </table>
    </script>
    <script>
    layui.config({
        base: '../../scripts/extend/'
    }).use(['laytpl', 'treegrid'], function () {
        var laytpl = layui.laytpl,
            treegrid = layui.treegrid;
        treegrid.config.render = function (viewid, data) {
            var view = document.getElementById(viewid).innerHTML;
            return laytpl(view).render(data) || '';
        };
        
        //var rows = [{"subMenuList":[],"createEmp":"111","createTime":{"date":21,"hours":21,"seconds":59,"month":5,"nanos":0,"timezoneOffset":-480,"year":118,"minutes":30,"time":1529587859000,"day":4},"name":"权限管理","pid":0,"updateTime":{"date":18,"hours":21,"seconds":50,"month":6,"nanos":0,"timezoneOffset":-480,"year":118,"minutes":58,"time":1531922330000,"day":3},"id":1,"pic":"&#xe672;","updateEmp":"刘柏江","url":"11"},{"subMenuList":[],"createEmp":"11","createTime":{"date":21,"hours":21,"seconds":25,"month":5,"nanos":0,"timezoneOffset":-480,"year":118,"minutes":31,"time":1529587885000,"day":4},"name":"菜单管理","pid":1,"updateTime":{"date":21,"hours":21,"seconds":28,"month":5,"nanos":0,"timezoneOffset":-480,"year":118,"minutes":31,"time":1529587888000,"day":4},"id":2,"pic":"&#xe60f;","updateEmp":"11","url":"menu"},{"subMenuList":[],"createEmp":"11","createTime":{"date":21,"hours":21,"seconds":51,"month":5,"nanos":0,"timezoneOffset":-480,"year":118,"minutes":31,"time":1529587911000,"day":4},"name":"账户管理","pid":1,"updateTime":{"date":21,"hours":21,"seconds":55,"month":5,"nanos":0,"timezoneOffset":-480,"year":118,"minutes":31,"time":1529587915000,"day":4},"id":3,"pic":"&#xe613;","updateEmp":"11","url":"userList"},{"subMenuList":[],"createEmp":"11","createTime":{"date":21,"hours":21,"seconds":23,"month":5,"nanos":0,"timezoneOffset":-480,"year":118,"minutes":32,"time":1529587943000,"day":4},"name":"角色管理","pid":1,"updateTime":{"date":17,"hours":19,"seconds":21,"month":6,"nanos":0,"timezoneOffset":-480,"year":118,"minutes":59,"time":1531828761000,"day":2},"id":4,"pic":"","updateEmp":"刘柏江","url":"roleList"}];
        $.ajax({
			type: 'get',
			url: '<%=request.getContextPath() %>/menu/menuArray',//请求后台返回菜单的树形json
			dataType : 'json',
			success: function(data){
				//再次请求后台，获取权限字符串
				$.ajax({
					type: "POST",
					//根据角色id查询角色拥有的权限，
		            url:"<%=request.getContextPath() %>/permission/query/",
					dataType : 'json',
		            error: function() {
			
		            },
		            success: function(permissionStr) {
						//获取角色的id
						var roleId = $("#roleId").val();
			        	//第三次请求后台，把用户id（roleId）传到后台，根据用户id（roleId）获取该用户的权限对象集合
			        	$.ajax({
			        		type: "POST",
			        		//根据角色id查询角色拥有的权限，
			                    url:"<%=request.getContextPath() %>/permission/query/"+roleId,
			        			dataType : 'json',
			                    error: function() {
			
			                    },
			                    success: function(rolePermissionStr) {
			        	 var btns = $("#demo").find("tr");   // 获得所有行
				    		for(var i = 2;i<btns.length;i++) //遍历所得的行，然后每行应选择器选中修改按钮，
				    		{
				    			//获取当前行的复选框，是一个数组
				    		  	obj = $(btns[i]).find("td").eq(1).find(":checkbox");
				    			    for(k in obj){
				    			    	//去掉未定义或者空的值
			    			            if(obj[k].value != undefined && obj[k].value != ""){
			    			            	for ( var j in rolePermissionStr) {
			    			            		//判断当前行的复选框的值是否跟用角色id查询该角色所用有的权限的id是否相等
												if(obj[k].value == rolePermissionStr[j].id){
													//给复选框赋默认值
													$(obj[k]).attr("checked",'checked'); 
												}
											}
				    			         }
				    			           
				    			    }
				    		}
					        			
					       }
			        	})
			
						//这个属性的控件必须放在这里，我也不知道为什么
						var tree1=treegrid.createNew({
			                elem: 'demo',
			                view: 'view',
			                data: { rows: data,"pStr" : permissionStr},
			                parentid: 'pid',
			                singleSelect: true,
			                
			            });
			            tree1.build();
					}
					
				})
				
				 
			}
		});
    });
    </script>
</body>
</html>