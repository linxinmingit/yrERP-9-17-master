<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>无jQuery入口的异步刷新</title>
<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="<%=request.getContextPath() %>/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/public.css" media="all" />
<style type="text/css">
gtnk, body {
	margin: 0;
	padding: 0;
	border: 0;
	background-color: #CAFFFF;
}

#loginPanel {background-color #eee;
	
}
</style>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-2.1.0.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/layui/layui.js"></script>
<script type="text/javascript">

window.onload=function () {
	$.ajax({
 		type: 'post',
 		url: '<%=request.getContextPath() %>/menu/getPic',
 		dataType : 'json',
 		success: function(data){
 			//满足取模为0时，使用的变量
 			var strTr = "";
 			//这是最后一行的字符串，一定要定义一个新的变量，不能用strTr这个变量
 			var strTr1 = "";
 			//把arr数组中的字符串元素相加，把存在这个变量
 			var strTable = "";
 			var arr = new Array();//定义一个数组，用来存放strTr的字符串
 			var tt = 0;//遍历data（后台返回的List集合）时用到全局变量，目的是可以用来去人迭代器迭代到哪个元素了
 			var j = 0;//数组下标使用的变量
 			for ( var i in data) {
 				tt++;
 				strTr+="<td onclick='openTable(this)'><i class='layui-icon'>"+data[i].unicode+"</i><input type='hidden' value='"+data[i].id+"'>"+data[i].name+"</td>";
 				if(tt%7==0){//一行7个列
				 arr[j++]="<tr>"+strTr+"</tr>";
				 strTr="";
				}else{ 
					if((data.length - tt) <= (data.length)%7){//最后一行
						strTr1+="<td onclick='openTable(this)'><i class='layui-icon'>"+data[i].unicode+"</i><input type='hidden' value='"+data[i].id+"'>"+data[i].name+"</td>";
 						if((data.length - tt) == 0){
 							arr[j++]="<tr>"+strTr1+"</tr>";
 						}
					}
				}
			}
 			//把arr数组中的元素相加放进strTable保存
 			for (var k = 0; k < arr.length; k++) {
 				strTable+=arr[k];
			}
 			$("#lo").append(strTable); /* 下拉框用 append,其他的用html覆盖 */
 		}
	});
};

function openTable(wqw){
	 //alert($(wqw).find("input").val());
	 alert($(wqw).find("input").val());
}  

</script>
</head>
<body>
	<table border="1" id="lo"></table>
</body>
</html>