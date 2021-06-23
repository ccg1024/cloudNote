<%--
  Created by IntelliJ IDEA.
  User: CCG
  Date: 2021/6/7
  Time: 9:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<div class="ml-3 border rounded content-box" style="background-color: #fff"> <!-- 可重复模块 -->
    <div class="m-4 border-bottom pb-2">
        <img src="./statics/img/icons/square.svg" alt="" >
        <p class="d-inline"><strong>查看日记</strong></p>
    </div>
    <div class="m-4">
        <h2 class="text-center">日记</h2>
        <p class="text-muted text-center">
            <small class="text-muted">发布时间：2021-06-07</small>
            <small class="text-muted">云记类别：日记</small>
        </p>
        <div> <!-- 记录内容 -->
            <p>内容</p>
        </div>
        <div> <!-- 按钮 -->
            <button class="btn btn-primary" type="button">修改</button>
            <button class="btn btn-danger float-right" type="button">删除</button>
        </div>
    </div>
</div>