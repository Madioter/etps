<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户管理</title>
    <link rel="stylesheet" href="<%=path%>/resources/css/web.css"/>
    <link rel="stylesheet" href="<%=path%>/resources/js/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="<%=path%>/resources/js/easyui/themes/icon.css"/>
    <script src="<%=path%>/resources/js/easyui/jquery.min.js"></script>
    <script src="<%=path%>/resources/js/easyui/jquery.easyui.min.js"></script>
    <script src="<%=path%>/resources/js/easyui/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
<div region="center" style="overflow:auto;padding:5px;" border="false">
    <form id="ff" method="post">
        <table cellpadding="5">
            <tr>
                <td>密　　码:</td>
                <td><input class="easyui-textbox" type="password" name="password" id="password"
                           data-options="required:true"/></td>
            </tr>
            <tr>
                <td>确认密码:</td>
                <td><input class="easyui-textbox" type="password" name="repassword"
                           data-options="required:true" validType="same['#password']"/></td>
            </tr>
        </table>
        <input type="hidden" name="id" value="${id}" id="id"/>
    </form>
</div>

<div region="south" style="height:31px;overflow:hidden;" split="false" border="false">
    <div class="datagrid-toolbar" style="float:right;">
        <a id="save" icon="icon-save" href="javascript:void(0);" class="easyui-linkbutton" plain="true">保存</a>
        <a id="cancel" icon="icon-cancel" href="javascript:void(0);" class="easyui-linkbutton" plain="true">取消</a>
    </div>
</div>
</body>
<script type="text/javascript">
    $("#save").click(function () {
        $('#ff').form('submit', {
            url: "<%=path%>/user/changePwd",
            success: function (data) {
                var jsonData = eval("(" + data + ")");
                if (jsonData.success) {
                    window.parent.closeUserWin(jsonData, false);
                } else {
                    $.messager.alert("提示", jsonData.message);
                }
            }
        });
    });

    $("#cancel").click(function () {
        window.parent.closeUserWin();
    });

    // TIP: 配合body解决页面跳动和闪烁问题
    $("body").css({visibility: "visible"});

</script>
</html>