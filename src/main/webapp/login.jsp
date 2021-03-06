<%--
  Created by IntelliJ IDEA.
  User: CCG
  Date: 2021/6/5
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<jsp:useBean id="resultInfo" scope="request" class="com.ccg.note.vo.ResultInfo"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>云R记</title>

    <link rel="stylesheet" href="statics/css/bootstrap.min.css"
          crossorigin="anonymous">
    <link rel="stylesheet" href="statics/css/login.css">
</head>
<body>


<div class="container-left w-50">
    <div class="bg-model"></div>
    <div class="container">
        <div class="row">
            <div class="col">
                <h1 class="text-center left-head text-white">开启移动办公新时代</h1>
                <p class="text-center text-white">多端同步，随时随地轻松办公</p>
            </div>
        </div>
        <div class="row" id="carouse-box">
            <div id="carouselExampleIndicators" class="carousel slide shadow-box rounded-m-lg" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                </ol>
                <div class="carousel-inner rounded-m-lg">
                    <div class="carousel-item active">
                        <img src="statics/img/login03-.jpg" class="d-block w-100 carousel-img" alt="...">
                        <div class="carousel-caption d-none d-md-block">
                            <h5>多端同步</h5>
                            <p>随时随地轻松办公。</p>
                        </div>
                    </div>
                    <div class="carousel-item">
                        <img src="statics/img/login02-.jpg" class="d-block w-100 carousel-img" alt="...">
                        <div class="carousel-caption d-none d-md-block">
                            <h5>多端同步</h5>
                            <p>随时随地轻松办公。</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="container-right w-50">
    <div class="container">
        <div class="row mb-5">
            <div class="col text-right pt-3 pb-3 pr-5 mr-3">
                <a href="#"><img src="statics/img/icons/book.svg" width="46" height="46" alt=""></a>
                <p class="text-muted">云日记</p>
            </div>
        </div>
        <div class="row padding-x-5">
            <div class="col">
                <h3 class="text-center"><strong>登   录</strong></h3>
                <form method="post" action="${pageContext.request.contextPath}/user" id="loginForm" class="mt-5 padding-x-5">
                    <!-- actionName 表示用户行为，通过该参数可在 UserServlet 中判断用户操作功能 -->
                    <input type="hidden" name="actionName" value="login">
                    <label for="userInput">用 户 名</label>
                    <div class="input-group mb-3 input-group-lg">
                        <div class="input-group-prepend ">
                            <div class="input-group-text input-radius-left">
                                <img src="statics/img/icons/person-fill.svg" alt="">
                            </div>
                        </div>
                        <input type="text" class="form-control input-radius-right" id="userInput" name="userInput"
                               placeholder="用户名" value="${resultInfo.result.uname}">
                    </div>
                    <label for="passwordInput">密 码</label>
                    <div class="input-group mb-3 input-group-lg">
                        <div class="input-group-prepend">
                            <div class="input-group-text input-radius-left">
                                <img src="statics/img/icons/lock-fill.svg" alt="">
                            </div>
                        </div>
                        <input type="password" class="form-control input-radius-right" id="passwordInput" name="passwordInput"
                               placeholder="密码" aria-describedby="msgHelper" value="${resultInfo.result.upwd}">
                    </div>
                    <small id="msgHelper" class="form-text text-danger text-center">${resultInfo.msg}</small>
                    <div class="form-group form-check border-bottom">
                        <input type="checkbox" class="form-check-input" id="rememberI" name="rememberI" value="1">
                        <label class="form-check-label mb-2" for="rememberI">记住我</label>
                    </div>
                    <p class="text-center mt-1"><a href="#" class="text-decoration-none">忘记密码？</a></p>
                    <button type="button" class="btn btn-dark btn-block btn-lg mb-3 rounded-pill" onclick="checkLogin()">登陆</button>
                    <button type="button" class="btn btn-primary btn-block btn-lg rounded-pill" onclick="clearInput()">取消</button>
                </form>
                <p class="text-center mt-4">新用户——<a href="#" class="text-decoration-none">注册</a> </p>
            </div>
        </div>
    </div>

    <div class="align-items-center justify-content-center display-flex">
        <div class="line-border"></div>
        <span class="mx-3">copyright © 2021 <a href="#" class="text-decoration-none text-dark">ccg</a></span>
        <div class="line-border"></div>
    </div>
</div>

<script src="statics/js/jquery-3.4.1.min.js" crossorigin="anonymous"></script>
<script src="statics/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="statics/js/login.js"></script>
<script src="statics/js/util.js" type="text/javascript"></script>
</body>
</html>
