<%--
  Created by IntelliJ IDEA.
  User: CCG
  Date: 2021/6/7
  Time: 10:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="ml-3 border rounded content-box" style="background-color: #fff"> <!-- 可重复模块 -->
    <div class="m-4 border-bottom pb-2">
        <img src="./statics/img/icons/square.svg" alt="" >
        <p class="d-inline"><strong>云记列表</strong></p>
    </div>

    <!-- 判断云记列表是否存在 -->
    <c:if test="${empty page}">
        <h2>暂未查询到云记记录</h2>
    </c:if>
    <c:if test="${!empty page}">
        <div class="m-4"> <!-- 列表内容 -->
            <div class="mb-5">
                <ul class="pl-4">
                    <c:forEach items="${page.dataList}" var="item">
                        <li>
                            <!-- 格式化日期 -->
                            「<fmt:formatDate value="${item.pubTime}" pattern="yyyy-MM-dd"/> 」:
                            <a href="note?actionName=detail&noteId=${item.noteId}" class="text-decoration-none ml-3">${item.title}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>

            <!-- 分页导航 -->
            <div class="d-flex justify-content-center">
                <nav aria-label="Page navigation example">
                    <ul class="pagination mb-0">
                        <!-- 上一页 -->
                        <c:if test="${page.pageNum > 1}">
                            <li class="page-item">
                                <a class="page-link" href="index?actionName=${action}&title=${title}&date=${date}&typeId=${typeId}&pageNum=${page.prePage}" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                        </c:if>

                        <c:forEach begin="${page.startNavPage}" end="${page.endNavPage}" var="p">
                            <li class="page-item <c:if test="${page.pageNum == p}">active</c:if> ">
                                <a class="page-link" href="index?actionName=${action}&title=${title}&date=${date}&typeId=${typeId}&pageNum=${p}">${p}</a>
                            </li>
                        </c:forEach>

<%--                        <li class="page-item active"><a class="page-link" href="#">1</a></li>--%>
<%--                        <li class="page-item"><a class="page-link" href="#">2</a></li>--%>
<%--                        <li class="page-item"><a class="page-link" href="#">3</a></li>--%>

                        <!-- 下一页 -->
                        <c:if test="${page.pageNum < page.totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="index?actionName=${action}&title=${title}&date=${date}&typeId=${typeId}&pageNum=${page.nextPage}" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
    </c:if>
</div>