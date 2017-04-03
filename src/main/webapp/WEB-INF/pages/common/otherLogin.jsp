<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
    <title></title>
    <script src="<%=path%>/resources/js/easyui/jquery.min.js"></script>
</head>
<body>
<div>
    用户名：
    <input type="text" name="username" value="" id="username"/><br/>
    密码：
    <input type="password" name="password" value="" id="password"/><br/>
    <img src="<%=path%>/getImage"/><br/>
    验证码：
    <input type="text" name="checkImg" value="" id="checkImg"/><br/>
</div>
<input type="button" value="监管平台登录" id="otherLogin"/>
</body>
<script type="text/javascript">
    $(function () {
        $("#otherLogin").click(function () {
            var code = $("#username").val();
            var password = $("#password").val();
            var checkCode = $("#checkImg").val();
            $.post("<%=path%>/otherDoLogin", {
                code: code,
                password: password,
                checkCode: checkCode
            }, function (data) {
                alert(data);
            });
        });
    })
</script>
</html>
