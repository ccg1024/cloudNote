/**
 * 判断字符串是否为空
 * 为空，返回 true
 * 否则，返回 false
 * @param str
 * @returns {Boolean}
 */
 function isEmpty(str) {
    return str == null || str.trim() === "";
}

$(function () {
    $('[data-toggle="tooltip"]').tooltip()
})

//
// $(function () {
//     var quill = new Quill('#editor-container', {
//         modules: {
//             toolbar: [
//                 [{ header: [1, 2, false] }],
//                 ['bold', 'italic', 'underline'],
//                 ['image', 'code-block']
//             ]
//         },
//         placeholder: 'Compose an epic...',
//         theme: 'snow' // or 'bubble'
//     });
// })