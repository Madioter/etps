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
<div region="center" border="false">
    <table class="easyui-datagrid" title="员工列表" style="width:800px;height:250px" id="userList"
           data-options="fit:true,pagination:true,rownumbers:true,singleSelect:false,url:'<%=path%>/user/userListJson',method:'post',toolbar:'#tb',queryParams: getParams()">
        <thead>
        <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th data-options="field:'id',hidden:true"></th>
            <th data-options="field:'username',width:100,align:'left'">用户名</th>
            <th data-options="field:'name',width:100,align:'left'">姓名</th>
            <th data-options="field:'number',width:80,align:'left'">工号</th>
            <th data-options="field:'option',width:150,align:'center',formatter:optionRenderer">操作</th>
        </tr>
        </thead>
    </table>
    <div id="tb" style="padding:2px 5px;">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="doEdit()">新增</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
           onclick="batchDelete()">删除</a>
        <br/>
        用户名: <input class="easyui-text" style="width:110px" id="username">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="reloadList();">查询</a>
    </div>
</div>

<div id="dd" class="easyui-window" closed="true" style="width:360px;height:140px;padding:5px;"></div>
</body>
<script type="text/javascript">

    function reloadList(page) {
        $('#userList').datagrid('options').queryParams = getParams();
        if (page) {
            $('#userList').datagrid('options').pageNumber = page;
        }
        $('#userList').datagrid("reload");
    }

    function optionRenderer(val, rec) {
        var button = "<a href='javascript:void(0);' class='easyui-linkbutton' onclick='resetPassword(" + rec.id + ")'>重置密码</a>&nbsp;&nbsp;";
        button += "<a href='javascript:void(0);' class='easyui-linkbutton' onclick='deleteUser(" + rec.id + ")'>删除</a>";
        return button;
    }

    function resetPassword(id) {
        var url = '<%=path%>/user/resetPassword?id=' + id;
        $('#dd').window({title: "重置密码"});
        $('#dd').html("<iframe src='" + url + "' scrolling='auto' style='width:100%;height:100%;border:0;'></iframe>");
        $('#dd').window('open');
    }

    function deleteUser(id) {
        $.post("<%=path%>/user/deleteUser", {id: id}, function (data) {
            var jsonData = data;
            if (jsonData.success) {
                $.messager.alert("消息", "删除成功");
                reloadList();
            } else {
                $.messager.alert("错误", jsonData.message);
            }
        });
    }

    function closeUserWin(data, doReloadList) {
        $('#dd').window('close');
        if (data.success) {
            $.messager.alert("消息", data.message);
            if (doReloadList) {
                reloadList();
            }
        }
    }

    //公用的函数放到闭包外面
    function doEdit(id) {
        var url = '<%=path%>/user/editUser';
        $('#dd').window({title: "新增用户", height: 280});
        $('#dd').html("<iframe src='" + url + "' scrolling='auto' style='width:100%;height:100%;border:0;'></iframe>");
        $('#dd').window('open');
    }

    function batchDelete() {
        if (checkSelection() == false) return false;

        var ids = [];
        var items = $('#userList').datagrid('getSelections');
        for (var i = 0; i < items.length; i++) {
            ids.push(items[i].id);
        }
        $.post("<%=path%>/user/deleteUser", {ids: ids.join(",")}, function (data) {
            var jsonData = data;
            if (jsonData.success) {
                $.messager.alert("消息", "删除成功");
                reloadList();
            } else {
                $.messager.alert("提示", jsonData.message);
            }
        });
    }

    function checkSelection() {
        var items = $('#userList').datagrid('getSelections');
        if (items.length == 0) {
            $.messager.alert("提示", '请先选择记录');
            return false;
        }
        return true;
    }

    function getParams() {
        return {username: $("#username").val()};
    }


    $(function () {
        // TIP: 配合body解决页面跳动和闪烁问题
        $("body").css({visibility: "visible"});
    });


</script>
</html>