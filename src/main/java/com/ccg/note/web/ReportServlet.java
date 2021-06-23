package com.ccg.note.web;

import com.ccg.note.po.User;
import com.ccg.note.service.NoteService;
import com.ccg.note.util.JsonUtil;
import com.ccg.note.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {

    private final NoteService noteService = new NoteService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("menu_page", "report");
        String actionName = req.getParameter("actionName");
        if ("info".equals(actionName)) {
            // 进入报表页面
            reportInfo(req, resp);
        } else if ("month".equals(actionName)) {
            queryNoteCountByMonth(req, resp);
        }
    }

    /**
     * 按照月份查询云记
     * @param req 请求
     * @param resp 响应
     */
    private void queryNoteCountByMonth(HttpServletRequest req, HttpServletResponse resp) {

        User user = (User) req.getSession().getAttribute("user");
        ResultInfo<Map<String, Object>> resultInfo = noteService.queryNoteCountByMonth(user.getUserId());
        JsonUtil.toJson(resp, resultInfo);
    }

    /**
     * 进入地图报表页面
     * @param req 请求
     * @param resp 响应
     */
    private void reportInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置动态包含页面
        req.setAttribute("changePage", "WEB-INF/pages/dataMap.jsp");
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
