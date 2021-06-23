<%--
  Created by IntelliJ IDEA.
  User: CCG
  Date: 2021/6/22
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<jsp:useBean id="note" scope="request" type="com.ccg.note.po.Note"/>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="ml-3 border rounded content-box" style="background-color: #fff"> <!-- 可重复模块 -->
    <div class="m-4 border-bottom pb-2">
        <img src="./statics/img/icons/square.svg" alt="" >
        <p class="d-inline"><strong>查看云记</strong></p>
    </div>


    <div class="m-4"> <!-- 云记内容 -->
        <div class="row">
            <div class="col">
                <h3 class="text-center">${note.title}</h3>
            </div>
        </div>
        <div class="row">
            <div class="col text-center">
                <small>发布时间: 「<fmt:formatDate value="${note.pubTime}" pattern="yyyy-MM-dd HH:mm"/>」
                    &nbsp;&nbsp; 云记类型: ${note.typeName}</small>
            </div>
        </div>
        <div class="row mt-3 mb-3">
            <div class="col">
                ${note.content}
            </div>
        </div>
        <div class="row">
            <div class="col">
                <button class="btn btn-primary" type="button" onclick="updateNote(${note.noteId})">修改</button>
                <button class="btn btn-danger float-right" type="button" id="deleteNoteBtn">删除</button>
            </div>
        </div>
    </div>

</div>

<!-- 删除云记提醒模态框 -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteModalLabel">删除云记</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>确认删除云记:&nbsp;&nbsp; <strong>${note.title}</strong></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary"
                        onclick="deleteNote(${note.noteId})">确认</button>
            </div>
        </div>
    </div>
</div>

<!-- 删除结果提示模态框 -->
<div class="modal fade" id="deleteInfoModal" tabindex="-1" aria-labelledby="deleteInfoModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteInfoModalLabel">删除结果</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>删除云记:&nbsp;&nbsp; <strong>${note.title}</strong>——<span id="infoAlert"></span></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $('#deleteNoteBtn').click(function () {
        $('#deleteModal').modal('show');
    });

    function deleteNote(noteId) {
        $('#deleteModal').modal('hide');
        $.ajax({
            type: "post",
            url: "note",
            data: {
                actionName: "delete",
                noteId: noteId
            },
            success: function (code) {
                // 判断是否删除成功
                if (code == 1) {
                    // 跳转到首页
                    window.location.href = "index";
                } else {
                    // 提示
                    $('#infoAlert').html("失败").css('color','red');
                    $('#deleteInfoModal').modal('show');
                }
            }
        });
    }

    function updateNote(noteId) {
        window.location.href = "note?actionName=view&noteId=" + noteId;
    }
</script>