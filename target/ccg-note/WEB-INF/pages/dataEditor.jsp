<%--
  Created by IntelliJ IDEA.
  User: CCG
  Date: 2021/6/7
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<jsp:useBean id="noteInfo"  scope="request" class="com.ccg.note.po.Note"/>--%>
<%--<jsp:useBean id="resultInfo" scope="request" class="com.ccg.note.vo.ResultInfo"/>--%>
<%--<jsp:useBean id="resultInfo" scope="request" type="com.ccg.note.vo.ResultInfo<com.ccg.note.po.Note>"/>--%>
<div class="ml-3 border rounded content-box" style="background-color: #fff"> <!-- 可重复模块 -->
    <div class="m-4 border-bottom pb-2">
        <img src="./statics/img/icons/square.svg" alt="" >
        <p class="d-inline">
            <strong>
                <c:if test="${empty noteInfo}">
                    发布云记
                </c:if>
                <c:if test="${!empty noteInfo}">
                    修改云记
                </c:if>
            </strong>
        </p>
    </div>
    <div class="m-4 d-flex flex-column"> <!-- 主体内容 -->
        <jsp:useBean id="typeList" scope="request" type="java.util.List<com.ccg.note.po.NoteType>"/>
        <c:if test="${empty typeList}">
            <h2>暂未查询到云记类型！</h2>
            <h4><a href="type?actionName=list">添加类型</a></h4>
        </c:if>
        <c:if test="${!empty typeList}">
        <form method="post" action="note">
            <!-- 隐藏域 -->
            <input type="hidden" name="actionName" value="addOrUpdate">
            <input type="hidden" name="noteId" value="${noteInfo.noteId}">
            <div class="form-group row">
                <label for="selectType" class="col-sm-2 col-form-label">类型</label>
                <div class="col-sm-10">
                    <select class="custom-select" id="selectType" name="selectType">
                        <option value="">选择类型</option>
                        <!-- resultInfo 对象的优先级要高些 -->
                        <c:forEach var="item" items="${typeList}">
                            <c:choose>
                                <c:when test="${!empty resultInfo}">
                                    <option <c:if test="${resultInfo.result.typeId == item.typeId}">selected</c:if> value="${item.typeId}">${item.typeName}</option>
                                </c:when>
                                <c:otherwise>
                                    <option <c:if test="${noteInfo.typeId == item.typeId}">selected</c:if> value="${item.typeId}">${item.typeName}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group row">
                <label for="inputTitle" class="col-sm-2 col-form-label">标题</label>
                <div class="col-sm-10">
                    <c:choose>
                        <c:when test="${!empty resultInfo}">
                            <input type="text" class="form-control" id="inputTitle" name="inputTitle" value="${resultInfo.result.title}">
                        </c:when>
                        <c:otherwise>
                            <input type="text" class="form-control" id="inputTitle" name="inputTitle" value="${noteInfo.title}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <!-- 富文本编辑器 -->
            <div class="form-group">
                <label for="content" class="sr-only">内容</label>
                <c:choose>
                    <c:when test="${!empty resultInfo}">
                        <textarea id="content" name="content">${resultInfo.result.content}</textarea>
                    </c:when>
                    <c:otherwise>
                        <textarea id="content" name="content">${noteInfo.content}</textarea>
                    </c:otherwise>
                </c:choose>
            </div>
            <p id="error" class="text-danger form-text"></p>
            <p class="text-danger form-text">${resultInfo.msg}</p>
            <button class="btn btn-primary mt-2 btn-block" type="submit" onclick="return checkForm()">提交</button>
        </form>
        </c:if>
    </div>
</div>
<script type="text/javascript">
    let ue;
    $(function () {
        ue = UE.getEditor('content');
    })

    /**
     * 表单校验
     * @returns {boolean}
     */
    function checkForm() {
        let typeId = $('#selectType').val();
        let title = $('#inputTitle').val();
        let content = ue.getContent();

        if (isEmpty(typeId)) {
            $('#error').html("请选择云记类型。");
            return false;
        }

        if (isEmpty(title)) {
            $('#error').html("标题不能为空。");
            return false;
        }

        if (isEmpty(content)) {
            $('#error').html("云记内容不能为空。");
            return false;
        }

        return true;
    }
</script>
