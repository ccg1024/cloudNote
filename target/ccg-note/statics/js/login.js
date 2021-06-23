/**
 * 用户登录表单校验
 * 1. 获取用户名与密码
 * 2. 判断用户名与密码是否为空
 *      为空提示
 *      否则提交
 */
function checkLogin() {
    let userName = $("#userInput").val();
    let userPswd = $("#passwordInput").val();

    if (isEmpty(userName)) {
        $("#msgHelper").html("用户名称不能为空！");
        return;
    }
    if (isEmpty(userPswd)) {
        $("#msgHelper").html("用户密码不能为空！");
        return;
    }
    // 提交 利用 jquery 提交表单
    $("#loginForm").submit();
}

function clearInput() {
    $("#userInput").val("");
    $("#passwordInput").val("");

}