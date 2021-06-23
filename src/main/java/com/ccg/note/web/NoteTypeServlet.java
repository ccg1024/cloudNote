package com.ccg.note.web;


import com.ccg.note.po.NoteType;
import com.ccg.note.po.User;
import com.ccg.note.service.NoteTypeService;
import com.ccg.note.util.JsonUtil;
import com.ccg.note.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/type")
public class NoteTypeServlet extends HttpServlet {

    private final NoteTypeService noteTypeService = new NoteTypeService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("menu_page", "type");
        String actionName = req.getParameter("actionName");

        if ("list".equals(actionName)) {
            // 查询类型列表
            typeList(req, resp);
        } else if ("delete".equals(actionName)) {
            // 删除类型
            deleteType(req, resp);
        } else if ("addOrUpdate".equals(actionName)) {
            // 添加或修改类型
            addOrUpdate(req, resp);
        }
    }

    /**
     * 添加或修改类型
     * @param req 请求
     * @param resp 响应
     */
    private void addOrUpdate(HttpServletRequest req, HttpServletResponse resp) {
        String typeName = req.getParameter("typeName");
        String typeId = req.getParameter("typeId");

        User user = (User) req.getSession().getAttribute("user");
        ResultInfo<Integer> resultInfo = noteTypeService.addOrUpdate(typeName, user.getUserId(), typeId);
        JsonUtil.toJson(resp, resultInfo);
    }

    /**
     * 删除云记类型
     * @param req 请求
     * @param resp 响应
     */
    private void deleteType(HttpServletRequest req, HttpServletResponse resp) {
        // 获取参数
        String  typeId = req.getParameter("typeId");
        ResultInfo<NoteType> resultInfo = noteTypeService.deleteType(typeId);
        // 使用流
        JsonUtil.toJson(resp, resultInfo);
    }

    /**
     * 查询日记类型列表
     * @param req 请求
     * @param resp 响应
     */
    private void typeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        List<NoteType> typeList = noteTypeService.findTypeList(user.getUserId());
        req.setAttribute("typeList", typeList);
        req.setAttribute("changePage", "WEB-INF/pages/dataType.jsp");
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
