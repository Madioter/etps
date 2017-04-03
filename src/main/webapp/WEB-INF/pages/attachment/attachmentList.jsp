<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=8">
    <meta charset="UTF-8">
    <title>附件列表</title>

    <link rel="stylesheet" href="<%=path%>/resources/css/web.css">
    <link rel="stylesheet" href="<%=path%>/resources/js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" href="<%=path%>/resources/js/easyui/themes/icon.css">
    <script src="<%=path%>/resources/js/easyui/jquery.min.js"></script>
    <script src="<%=path%>/resources/js/easyui/jquery.easyui.min.js"></script>
    <script src="<%=path%>/resources/js/easyui/easyui-lang-zh_CN.js"></script>
    <script src="<%=path%>/resources/js/localDefault.js"></script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
<div region="center" border="false">
    <table class="easyui-datagrid" title="附件列表" style="width:800px;height:250px" id="attachmentList"
           data-options="fit:true,
           <c:if test='${login_user.username == "admin"}'>pagination:true,</c:if>
           <c:if test='${login_user.username != "admin"}'>pagination:false,</c:if>
           rownumbers:true,singleSelect:false,fitColumns:true,url:'<%=path%>/data-collect/getAttachmentJson',method:'post',toolbar:'#tb',queryParams: getParams()">
        <thead>
        <tr>
            <th data-options="field:'id',hidden:true">ID</th>
            <th data-options="field:'fileName',width:100,align:'left'">文件名称</th>
            <th data-options="field:'addTime',width:80,align:'left',formatter: formatDatebox">添加时间</th>
            <th data-options="field:'option',width:150,align:'center',formatter:optionRenderer">操作</th>
        </tr>
        </thead>
    </table>
    <div id="tb" style="padding:2px 5px;">
        <c:if test='${login_user.username == "admin"}'>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
               onclick="otherLogin();">监管平台登录</a>
        </c:if>
        <br/>
        企业名称: <input type="text" class="easyui-text" style="width:110px" id="name"/>
        所属辖区: <input class="easyui-combotree" id = "localadm" name="localadm" style="width: 260px;"
                     url="<%=path%>/data-collect/admtree" style="width:260px;" panelAlign="right" panelWidth="260px" panelHeight="300px"/>
        从: <input type="text" class="easyui-text" style="width:110px" id="startRows"/> 条
        至: <input type="text" class="easyui-text" style="width:110px" id="endRows"/> 条（请一次选择不要选择超过500条）
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="collectData();">数据采集</a>
    </div>
</div>

<div id="otherLogin" class="easyui-dialog" closed="true" model="true" closable="true" title="监管平台登录"
     buttons="#dlg-buttons"
     style="width:350px;height:200px;padding:5px;">
    <form id="importForm" method="post" enctype="multipart/form-data" action="<%=path%>/enterprise/excelImport">
        <table cellpadding="5">
            <tr>
                <td>
                    用户名：
                </td>
                <td>
                    <input type="text" name="username" value="" id="username"/>
                </td>
            </tr>
            <tr>
                <td>
                    密码：
                </td>
                <td>
                    <input type="password" name="password" value="" id="password"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <img id="checkImg"/>
                </td>
            </tr>
            <tr>
                <td>
                    验证码：
                </td>
                <td>
                    <input type="text" name="checkCode" value="" id="checkCode"/>
                </td>
            </tr>
            <tr></tr>
            <tr>
                <td></td>
                <td>
                    <input type="button" value="监管平台登录" id="otherDoLogin"/>
                </td>
            </tr>
        </table>
    </form>
</div>

</body>
<script type="text/javascript">
    function reloadList(page) {
        $('#attachmentList').datagrid('options').queryParams = getParams();
        if (page) {
            $('#attachmentList').datagrid('options').pageNumber = page;
        }
        $('#enterpriseList').datagrid("reload");
    }

    function getParams() {
        return {};
    }

    function doSave() {
        $("#importForm").submit();
    }

    function doExport(id) {
        window.open("<%=path%>/data-collect/getFile?id=" + id);
    }

    function collectData() {
        var startRows = $('#startRows').val();
        if (!startRows) {
            startRows = 0;
            $('#startRows').val(0);
        }
        var endRows = $('#endRows').val();
        if (!endRows) {
            endRows = 100;
            $('#endRows').val(100);
        }

        var data = {
            enterpriseName: $("#name").val(),
            localadm: $("#localadm").combotree('getValue'),
            startRows: startRows,
            endRows: endRows
        };

        $.post("<%=path%>/data-collect/collectData", data, function (data) {
            alert(data);
        });
    }

    $(function () {
        <c:if test="${result != null}">
        $("#message").dialog("open");
        </c:if>
        // TIP: 配合body解决页面跳动和闪烁问题
        $("body").css({visibility: "visible"});

    });

    function otherLogin() {
        $("#otherLogin").dialog("open");
        $("#checkImg").attr("src", "<%=path%>/getImage?rom=" + Math.random());
        $.post("<%=path%>/otherLogin", {}, function (data) {
            var jsonData = data;
            if (!jsonData.success) {
                alert("监控平台目前无法登陆，请查看地址是否发生变化");
                $("#otherLogin").dialog("close");
            }
        });
    }

    function deleteAttachment() {
        $.post("<%=path%>/data-collect/deleteAttachment", {id: id}, function (data) {
            var jsonData = data;
            if (jsonData.success) {
                $.messager.alert("消息", "删除成功");
                reloadList();
            } else {
                $.messager.alert("错误", jsonData.message);
            }
        });
    }

    function optionRenderer(val, rec) {
        var button = "<a href='javascript:void(0);' class='easyui-linkbutton' onclick='doExport(" + rec.id + ")'>下载</a>&nbsp;&nbsp;";
        button += "<a href='javascript:void(0);' class='easyui-linkbutton' onclick='deleteAttachment(" + rec.id + ")'>删除</a>";
        return button;
    }

    $(function () {
        $("#otherDoLogin").click(function () {
            var code = $("#username").val();
            var password = $("#password").val();
            var checkCode = $("#checkCode").val();
            $.post("<%=path%>/otherDoLogin", {
                code: code,
                password: password,
                checkCode: checkCode
            }, function (data) {
                var jsonData = data;
                if (jsonData.success) {
                    $("#otherLogin").dialog("close");
                } else {
                    alert(jsonData.message);
                }
            });
        });

        $("#checkImg").click(function () {
            $("#checkImg").attr("src", "<%=path%>/getImage?rom=" + Math.random());
        });
    });

</script>
</html>
