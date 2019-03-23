<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/9/20
  Time: 8:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script language="javascript" type="text/javascript">
    // 以下方式直接跳转
    //window.location.href='<%=request.getContextPath()%>/index';
    window.location.href='<%=request.getContextPath()%>/login';
    // 以下方式定时跳转
    //setTimeout("javascript:location.href='hello.html'", 5000);
</script>
<body>
</body>
</html>
