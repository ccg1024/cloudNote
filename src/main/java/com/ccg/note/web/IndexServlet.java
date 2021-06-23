package com.ccg.note.web;

import com.ccg.note.po.Note;
import com.ccg.note.po.User;
import com.ccg.note.service.NoteService;
import com.ccg.note.util.Page;
import com.ccg.note.vo.NoteVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置首页导航高亮
        request.setAttribute("menu_page", "index");

        // 得到用户行为 （判断查询条件：标题查询、日期查询、类型查询）
        String actionName = request.getParameter("actionName");

        // 放置到 request 中，这样查询分页结果可以附带当前是什么查询类型
        request.setAttribute("action", actionName);

        if ("searchTitle".equals(actionName)) {  // 标题查询
            String title = request.getParameter("title");
            request.setAttribute("title", title);

            noteList(request, response, title, null, null);
        } else if ("searchDate".equals(actionName)) {  // 日期查询
            String date = request.getParameter("date");
            request.setAttribute("date", date);

            noteList(request, response, null, date, null);
        } else if ("searchType".equals(actionName)) {  // 类型查询
            String typeId = request.getParameter("typeId");
            request.setAttribute("typeId", typeId);

            noteList(request, response, null, null, typeId);
        } else{
            // 分页查询云记列表
            noteList(request, response, null, null, null);
        }

        // 设置首页动态包含页面
        request.setAttribute("changePage", "WEB-INF/pages/dataList.jsp");

        // 跳转
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    /**
     * 分页查询
     * @param request 请求
     * @param response 响应
     * @param title 标题
     */
    private void noteList(HttpServletRequest request, HttpServletResponse response, String title,
                          String date, String typeId) {

        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");

        User user = (User) request.getSession().getAttribute("user");

        // 调用 Service 层方法，返回 Page 对象
        Page<Note> page = new NoteService().findNoteListByPage(pageNum, pageSize, user.getUserId(), title, date, typeId);
        // 将右侧页面列表存入  request
        request.setAttribute("page", page);

        // 通过日期分组 查询云记数量
        List<NoteVo> dateInfo = new NoteService().findNoteCountByDate(user.getUserId());
        // 因为页面不断变动，所以需要放在 session 作用域中
        request.getSession().setAttribute("dateInfo", dateInfo);

        // 通过类型分组 查询云记数量
        List<NoteVo> typeInfo = new NoteService().findNoteCountByType(user.getUserId());
        // 因为页面不断变动，所以需要放在 session 作用域中
        request.getSession().setAttribute("typeInfo", typeInfo);
    }
}
