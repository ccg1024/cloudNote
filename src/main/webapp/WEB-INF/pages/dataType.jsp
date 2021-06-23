<%--
  Created by IntelliJ IDEA.
  User: CCG
  Date: 2021/6/7
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="ml-3 border rounded content-box" style="background-color: #fff"> <!-- 可重复模块 -->
    <div class="m-4 border-bottom pb-2">
        <img src="./statics/img/icons/square.svg" alt="" >
        <p class="d-inline"><strong>类型列表</strong></p>
        <a href="#" class="float-right" data-toggle="tooltip" data-placement="top" id="addType"
           title="添加类型" onclick="openAddDialog()">
            <img src="./statics/img/icons/plus-lg.svg" alt="">
        </a>
    </div>
    <div class="m-4" id="myDiv"> <!-- 主体内容 -->
        <jsp:useBean id="typeList" scope="request" type="java.util.List<com.ccg.note.po.NoteType>"/>
        <c:if test="${empty typeList}">
            <h2>暂未查询到数据</h2>
        </c:if>
        <c:if test="${!empty typeList}">
        <table class="table" id="myTable">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">类型</th>
                <th scope="col">操作</th>
            </tr>
            </thead>
            <tbody>
                <c:set var="num" scope="page" value="1"/>
                <c:forEach items="${typeList}" var="item">
                    <tr id="tr_${item.typeId}">
                        <th scope="row">${num}</th>
                        <td>${item.typeName}</td>
                        <td>
                            <a href="#" data-toggle="tooltip" data-placement="top"
                               onclick="openUpdateDialog(${item.typeId})" title="编辑">
                                <img src="./statics/img/icons/pen.svg" alt="编辑">
                            </a>
                            <a href="#" class="ml-3" data-toggle="tooltip" data-placement="top"
                               onclick="openDeleteDialog('${item.typeName}', ${item.typeId})" title="删除">
                                <img src="./statics/img/icons/x-lg.svg" alt="删除">
                            </a>
                        </td>
                    </tr>
                    <input type="hidden" name="typeId" value="${item.typeId}"/>
                    <c:set value="${num + 1}" var="num"/>
                </c:forEach>
            </tbody>
        </table>
        </c:if>
    </div>
</div>
<!-- 模态框 添加/修改类型 -->
<div class="modal fade" id="myModal" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="myModalLabel">新增</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="typeName" class="col-form-label">类型名称</label>
                        <input class="form-control" type="text" id="typeName" name="typeName" placeholder="类型名称">
                        <input type="hidden" id="typeId" name="typeId">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <span class="text-danger" id="msg"></span>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="btnSubmit" onclick="addOrUpdate()">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 删除提示模态框 -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteModalLabel">删除类型</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>确认删除类型:<strong id="deleteType"></strong></p>
                <input type="hidden" id="deleteId" name="deleteId">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary"
                        onclick="deleteType()">确认</button>
            </div>
        </div>
    </div>
</div>

<!-- 删除结果信息模态框 -->
<div class="modal fade" id="deleteInfoModal" tabindex="-1" aria-labelledby="deleteInfoModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteInfoModalLabel">删除结果</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p id="deleteInfo" class="text-center"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>