package com.ccg.note.web;

import cn.hutool.core.util.StrUtil;
import com.ccg.note.po.Note;
import com.ccg.note.po.NoteType;
import com.ccg.note.po.User;
import com.ccg.note.service.NoteService;
import com.ccg.note.service.NoteTypeService;
import com.ccg.note.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/note")
public class NoteServlet extends HttpServlet {

    private final NoteService noteService = new NoteService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("menu_page", "note");
        String actionName = request.getParameter("actionName");

        if ("view".equals(actionName)) {
            noteView(request, response);
        } else if ("addOrUpdate".equals(actionName)) {
            addOrUpdate(request, response);
        } else if ("detail".equals(actionName)) {
            noteDetail(request, response);
        } else if ("delete".equals(actionName)) {
            noteDelete(request, response);
        }
    }

    /**
     * 删除云记
     * @param request 请求
     * @param response 响应
     */
    private void noteDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String noteId = request.getParameter("noteId");
        Integer code = noteService.deleteNote(noteId);
        response.getWriter().write(code+"");  // 以字符串形式输出
        response.getWriter().close();
    }

    /**
     * 云记详情
     * @param request 请求
     * @param response 响应
     */
    private void noteDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String noteId = request.getParameter("noteId");
        Note note = noteService.findNoteById(noteId);
        request.setAttribute("note", note);
        request.setAttribute("changePage","WEB-INF/pages/dataDetail.jsp");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    /**
     * 添加或修改操作
     * 接受参数 类型 ID 标题 内容
     * @param request 请求
     * @param response 响应
     */
    private void addOrUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String typeId = request.getParameter("selectType");
        String title = request.getParameter("inputTitle");
        String content = request.getParameter("content");

        // 修改操作需要 noteId
        String noteId = request.getParameter("noteId");

        ResultInfo<Note> resultInfo = noteService.addOrUpdate(typeId, title, content, noteId);

        if (resultInfo.getCode() == 1) {
            response.sendRedirect("index");
        } else {
            request.setAttribute("resultInfo", resultInfo);
            String url = "note?actionName=view";
            if (!StrUtil.isBlank(noteId)) {
                url += "&noteId=" + noteId;
            }
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    /**
     * 进入发布云记的页面
     * @param request 请求
     * @param response 响应
     */
    private void noteView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 修改操作的参数
        String noteId = request.getParameter("noteId");
        Note note = noteService.findNoteById(noteId);
        // 将 note 对象设置到请求域中
        request.setAttribute("noteInfo", note);

        User user = (User) request.getSession().getAttribute("user");
        List<NoteType> typeList = new NoteTypeService().findTypeList(user.getUserId());
        request.setAttribute("typeList", typeList);

        request.setAttribute("changePage", "WEB-INF/pages/dataEditor.jsp");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
