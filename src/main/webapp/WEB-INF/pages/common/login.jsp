<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <title>登陆</title>
    <link rel="stylesheet" href="<%=path%>/resources/css/web.css"/>
    <link rel="stylesheet" href="<%=path%>/resources/js/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="<%=path%>/resources/js/easyui/themes/icon.css"/>
    <script src="<%=path%>/resources/js/easyui/jquery.min.js"></script>
    <script src="<%=path%>/resources/js/easyui/jquery.easyui.min.js"></script>
    <script src="<%=path%>/resources/js/easyui/easyui-lang-zh_CN.js"></script>
</head>
<body>
<div class="easyui-panel" title="登陆" style="width:400px;" top="center" left="center">
    <div style="padding:10px 60px 20px 60px">
        <form id="ff" method="post">
            <table cellpadding="5">
                <tr>
                    <td>用户名:</td>
                    <td><input class="easyui-textbox" type="text" name="username"
                               data-options="required:true"/></td>
                </tr>
                <tr>
                    <td>密　码:</td>
                    <td><input class="easyui-textbox" type="password" name="password"
                               data-options="required:true"/></td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">确定</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">清空</a>
        </div>
    </div>
</div>
<script>
    function submitForm() {
        $('#ff').form('submit', {
            url: "<%=path%>/logon",
            success: function (data) {
                var jsonData = eval("(" + data + ")");
                if (jsonData.success == true) {
                    window.location.href = "<%=path%>/enterprise/enterpriseList";
                } else {
                    $.messager.show({
                        title: "提示",
                        msg: jsonData.message
                    });
                }
            }
        });
    }

    function clearForm() {
        $('#ff').form('clear');
    }

    $(function () {
        if (self.location != top.location) {
            top.location = "<%=path%>/login";
        }
    });
</script>
</body>
</html>