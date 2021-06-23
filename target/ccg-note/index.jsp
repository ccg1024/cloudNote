<jsp:useBean id="title" scope="request" class="java.lang.String"/>
<jsp:useBean id="user" scope="session" class="com.ccg.note.po.User"/>
<jsp:useBean id="dateInfo" scope="session" type="java.util.List<com.ccg.note.vo.NoteVo>"/>
<jsp:useBean id="typeInfo" scope="session" type="java.util.List<com.ccg.note.vo.NoteVo>"/>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="menu_page" scope="request" class="java.lang.String"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">

    <title>云R记</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/bootstrap.min.css"
          crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/baseConfig.css">

    <script src="${pageContext.request.contextPath}/statics/js/jquery-3.6.0.js" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/statics/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/statics/js/util.js"></script>
    <script src="./statics/js/type.js" type="text/javascript"></script>
    <!-- 富文本配置文件 -->
    <script type="text/javascript" src="./statics/ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="./statics/ueditor/ueditor.all.js"></script>
</head>
<body style="background-color: #f7f7f7">

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">
        <img src="statics/img/icons/cloud-white.svg" alt="" width="32" height="32">
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li <c:if test="${menu_page=='index'}">class="active nav-item mx-3"</c:if>
                <c:if test="${menu_page!='index'}">class="mx-3 nav-item"</c:if>
                class="nav-item mx-3">
                <a class="nav-link" href="index">
                    <img src="statics/img/icons/bookmark-white.svg" alt="" width="18" height="18">
                    主 页<span class="sr-only">(current)</span>
                </a>
            </li>
            <li <c:if test="${menu_page=='note'}">class="active nav-item mx-3"</c:if>
                <c:if test="${menu_page!='note'}">class="mx-3 nav-item"</c:if>
                class="nav-item mx-3">
                <a class="nav-link" href="note?actionName=view">
                    <img src="statics/img/icons/arrow-up-square.svg" alt="" width="18" height="18">
                    发表云记
                </a>
            </li>
            <li <c:if test="${menu_page=='type'}">class="active nav-item mx-3"</c:if>
                <c:if test="${menu_page!='type'}">class="mx-3 nav-item"</c:if>
                class="nav-item mx-3">
                <a class="nav-link" href="type?actionName=list">
                    <img src="statics/img/icons/file-earmark-white.svg" width="18" height="18" alt="">
                    类别管理
                </a>
            </li>
            <li <c:if test="${menu_page=='user'}">class="active nav-item mx-3"</c:if>
                <c:if test="${menu_page!='user'}">class="mx-3 nav-item"</c:if>
                class="nav-item mx-3">
                <a class="nav-link" href="user?actionName=userCenter">
                    <img src="statics/img/icons/file-person-white.svg" alt="" width="18" height="18">
                    个人中心
                </a>
            </li>
            <li <c:if test="${menu_page=='report'}">class="active nav-item mx-3"</c:if>
                <c:if test="${menu_page!='report'}">class="mx-3 nav-item"</c:if>
                class="nav-item mx-3">
                <a class="nav-link" href="report?actionName=info">
                    <img src="statics/img/icons/table.svg" alt="" width="18" height="18">
                    数据报表
                </a>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0" action="index" role="search">
            <input type="hidden" name="actionName" value="searchTitle">
            <input class="form-control mr-sm-2" name="title" type="search" placeholder="Search"
                   aria-label="Search" value="${title}">
            <button class="btn btn-outline-success my-2 my-sm-0 change-bottom" type="submit">查询</button>
        </form>
    </div>
</nav>

<div class="container mt-3">
    <div class="row ">
        <div class="col-3 d-flex flex-column card-width-change">

            <!-- 用户卡片 -->
            <div class="card mb-3 user-car-model">
                <img src="user?actionName=userHead&imageName=${user.head}" class="card-img-top p-4 border-bottom" alt="...">
                <div class="card-body">
                    <h5 class="card-title">${user.nick}</h5>
                    <p class="card-text">
                        ${user.mood}
                    </p>
                </div>
                <div class="card-body">
                    <a href="user?actionName=logout" class="card-link">退出</a>
                </div>
            </div>

            <!-- 云记日期卡片 -->
            <div class="card mb-3" >
                <div class="card-header">
                    <a href="#"> <img src="statics/img/icons/tag.svg" alt=""> </a>
                    <strong>云记日期</strong>
                </div>
                <ul class="list-group list-group-flush">
                    <c:forEach items="${dateInfo}" var="item">
                        <li class="list-group-item">
                            <a href="index?actionName=searchDate&date=${item.groupName}" class="text-decoration-none">${item.groupName}</a>
                            <span class="badge badge-pill badge-info float-right">${item.noteCount}</span>
                        </li>
                    </c:forEach>
                </ul>
            </div>

            <!-- 云记类别卡片 -->
            <div class="card mb-3">
                <div class="card-header">
                    <a href="#"> <img src="statics/img/icons/tag.svg" alt=""> </a>
                    <strong>云记类别</strong>
                </div>
                <ul class="list-group list-group-flush" id="typeUl">
                    <c:forEach items="${typeInfo}" var="item">
                        <li class="list-group-item" id="li_${item.typeId}">
                            <a href="index?actionName=searchType&typeId=${item.typeId}" class="text-decoration-none" id="link_${item.typeId}">${item.groupName}</a>
                            <span class="badge badge-pill badge-info float-right">${item.noteCount}</span>
                        </li>
                    </c:forEach>
                </ul>
            </div>

        </div> <!-- 左侧结束 -->

        <!-- 可变内容 -->
        <div class="col-9 card-width-change mb-3">
            <!-- 动态包含 -->
<%--            <jsp:include page="pages/dataList.jsp"></jsp:include>--%>
            <jsp:useBean id="changePage" scope="request" type="java.lang.String"/>
            <c:if test="${empty changePage}">
                <jsp:include page="WEB-INF/pages/dataList.jsp"/>
            </c:if>
            <c:if test="${!empty changePage}">
                <jsp:include page="${changePage}"/>
            </c:if>
        </div>

    </div>
</div>
</body>
</html>