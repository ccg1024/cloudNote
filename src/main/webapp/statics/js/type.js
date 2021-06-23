// ==========================删除类型==========================
function openDeleteDialog(typeName, typeId) {
    $('#deleteModal').modal("show");
    $('#deleteType').html(typeName);
    $('#deleteId').val(typeId);
}
/**
 * 删除选中的云记类型
 * @param typeId
 */
function deleteType() {
    $('#deleteModal').modal("hide");
    let typeId = $('#deleteId').val();
    $.ajax({
        type: 'post',
        url: 'type',
        data: {
            actionName: 'delete',
            typeId: typeId
        },
        success: function (result) {
            // 判断是否成功
            if (result.code == 1) {
                // alert("删除成功");
                $('#deleteInfoModal').modal("show");
                $('#deleteInfo').html(result.msg);
                deleteDom(typeId);
            } else {
                $('#deleteInfoModal').modal("show");
                $('#deleteInfo').html(result.msg);
            }
        }
    });
}

function deleteDom(typeId) {
    let myTable = $('#myTable');
    // 得到 table 元素的子元素 tr 的数量
    let trLength = $('#myTable tr').length;
    if (trLength == 2) {
        // 只有一条记录
        $('#myTable').remove();
        // 设置提示信息
        $('#myDiv').html("<h2>未查询到类型记录！</h2>")
    } else {
        // 有多条记录
        $('#tr_' + typeId).remove();
    }
    $('#li_'+typeId).remove();
}

/**
 * 打开添加模态框
 */
function openAddDialog() {
    $('#myModalLabel').html("新增类型");
    $('#typeName').val("");
    $('#typeId').val("");
    $('#msg').html("");
    $('#myModal').modal("show");
}
/**
 * 打开修改模态框
 * @param typeId
 */
function openUpdateDialog(typeId) {
    $('#myModalLabel').html("修改类型");
    let tr = $('#tr_'+typeId);
    let typeName = tr.children().eq(1).text();
    $('#typeName').val(typeName);
    $('#typeId').val(typeId);
    $('#msg').html("");
    $('#myModal').modal("show");
}

/**
 * 添加类型、修改类型 确认按钮
 */
function addOrUpdate() {
    // 获取文本框
    let typeName = $('#typeName').val();
    let typeId = $('#typeId').val();

    // 判断参数合法性
    if (isEmpty(typeName)) {
        $('#msg').html("类型名称不能为空");
        return;
    }

    $.ajax({
        type: "post",
        url: "type",
        data: {
            actionName: "addOrUpdate",
            typeName: typeName,
            typeId: typeId
        },
        success: function (result) {
            if (result.code == 1) {  // 成功
                // 关闭模态框
                $('#myModal').modal('hide');
                if (isEmpty(typeId)) {  // 为空，表示执行添加操作
                    addDome(typeName, result.result);
                } else {  // 表示执行修改操作
                    updateDome(typeName, typeId);
                }
            } else {
                $('#msg').html(result.msg);
            }
        }
    });
}
// $("#btnSubmit").click(function () {  // 链接标签无法用 click 函数
//     $('#myModal').modal('hide');
//     // 获取文本框
//     let typeName = $('#typeName').val();
//     let typeId = $('#typeId').val();
//
//     // 判断参数合法性
//     if (isEmpty(typeName)) {
//         $('#msg').html("类型名称不能为空");
//         return;
//     }
//
//     $.ajax({
//        type: "post",
//        url: "type",
//        data: {
//            actionName: "addOrUpdate",
//            typeName: typeName,
//            typeId: typeId
//        },
//         success: function (result) {
//             if (result.code == 1) {  // 成功
//                 // 关闭模态框
//                 $('#myModal').modal('hide');
//                 if (isEmpty(typeId)) {  // 为空，表示执行添加操作
//
//                 } else {  // 表示执行修改操作
//
//                 }
//             } else {
//                 $('#msg').html(result.msg);
//             }
//         }
//     });
// });
/**
 * 修改的 dom
 * @param typeName
 * @param typeId
 */
function updateDome(typeName, typeId) {
    let tr = $('#tr_' + typeId);
    tr.children().eq(1).text(typeName);
    let link = $('#link_' + typeId);
    link.html(typeName);
}

/**
 * 添加类型 dom
 * @param typeName
 * @param typeId
 */
function addDome(typeName, typeId) {
    // 获取当前有多少个表格行，由于类型行占一行，所以 trLength 就是当前的行数
    let trLength = $('#myTable tr').length;
    if (trLength == 0) {
        trLength = 1;
    }
    let tr = '<tr id="tr_' + typeId + '"><th scope="row">'+trLength+'</th><td>'+typeName+'</td><td><a href="#" data-toggle="tooltip" data-placement="top" ' +
        'onclick="openUpdateDialog('+typeId+')" title="编辑" data-original-title="编辑"><img src="./statics/img/icons/pen.svg" alt="编辑"></a>' +
        '<a href="#" class="ml-3" data-toggle="tooltip" data-placement="top" onclick="openDeleteDialog(\''+typeName+'\', '+typeId+')" title="删除" data-original-title="删除">' +
        '<img src="./statics/img/icons/x-lg.svg" alt="删除"></a>'+'</td></tr><input type="hidden" name="typeId" value="'+typeId+'"/>'
    let myTable = $('#myTable');
    // 需判断表格对象是否存在
    if (myTable.length > 0) {
        myTable.append(tr);
    } else {
        // 需要拼接一个表格
        myTable = '<table class="table" id="myTable"><thead><tr><th scope="col">#</th>';
        myTable += '<th scope="col">类型</th><th scope="col">操作</th></tr></thead><tbody>' + tr;
        myTable += '</tbody> </table>';
        $('#myDiv').html(myTable);
    }
    let typeUl = $('#typeUl');
    let li = '<li class="list-group-item" id="li_' + typeId + '"><a href="#" class="text-decoration-none"' +
        ' id="link_' + typeId + '">' + typeName + '</a><span class="badge badge-pill badge-info float-right">' +
        '0</span></li>';
    typeUl.append(li);
}