<jsp:useBean id="user" scope="session" class="com.ccg.note.po.User"/>
<%--
  Created by IntelliJ IDEA.
  User: CCG
  Date: 2021/6/7
  Time: 9:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"  isELIgnored="false" %>
<div class="ml-3 border rounded content-box" style="background-color: #fff"> <!-- 可重复模块 -->
    <div class="m-4 border-bottom pb-2">
        <img src="./statics/img/icons/square.svg" alt="" >
        <p class="d-inline"><strong>个人中心</strong></p>
    </div>
    <div class="m-4 border rounded">
        <form class="form-inline p-4 d-flex flex-wrap align-items-center" method="post" action="user" enctype="multipart/form-data">
            <input type="hidden" name="actionName" value="updateUser">
            <div class="form-group mr-3 ">
                <label for="inputNick" class="pr-2">昵称</label>
                <input type="text" class="form-control" id="inputNick" name="inputNick" value="${user.nick}">
            </div>

            <!-- 选择文件按钮 -->
            <div class="form-group">
                <label for="chooseFile" class="sr-only"></label>
                <input type="file" class="form-control-file" id="chooseFile" name="chooseFile">
            </div>

            <div class="form-group">
                <img src="user?actionName=userHead&imageName=${user.head}" alt="" style="width: 30px; height: 30px">
            </div>

            <div class="form-group w-100 mt-3">
                <label for="moodText"></label>
                <textarea class="form-control w-100"  id="moodText" name="moodText" placeholder="心情">${user.mood}</textarea>
            </div>

            <button type="submit" class="btn btn-success mt-3" id="btn-c" onclick="return updateUser()">修改</button>
            <small id="msg" class="text-danger ml-3"></small>
        </form>
    </div>
</div>

<script type="text/javascript">
    $("#inputNick").blur(function () {
        // 1. 获取值
        let nickName = $("#inputNick").val();
        // 2. 判断值是否为空
        if (isEmpty(nickName)) {
            $("#msg").html("用户昵称不能为空")
            $("#btn-c").prop("disabled", true)
            return;
        }
        let nick = '${user.nick}';
        if (nickName == nick) {
            return;
        }
        $.ajax({
            type: "get",
            url: "user",
            data: {
                actionName: "checkNick",
                nick: nickName,
            },
            success: function (code) {
                if (code == 1) {
                    $("#msg").html("");
                    $("#btn-c").prop("disabled", false);
                } else {
                    $("#msg").html("该昵称已存在，请重新输入");
                    $("#btn-c").prop("disabled", true);
                }
            }
        })
    }).focus(function () {
        $("#msg").html("")
        $("#btn-c").prop("disabled", false)
    })

    function updateUser() {
        let nickName = $("#inputNick").val();
        // 2. 判断值是否为空
        if (isEmpty(nickName)) {
            $("#msg").html("用户昵称不能为空")
            $("#btn-c").prop("disabled", true)
            return false;
        }



        return true
    }

</script>