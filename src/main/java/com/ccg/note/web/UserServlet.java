package com.ccg.note.web;

import com.ccg.note.po.User;
import com.ccg.note.service.UserService;
import com.ccg.note.vo.ResultInfo;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

@WebServlet("/user")
@MultipartConfig
public class UserServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置首页导航高亮
        req.setAttribute("menu_page", "user");
        // 接受用户行为
        String actionName = req.getParameter("actionName");

        if ("login".equals(actionName)) {
            // 用户登录
            userLogin(req, resp);
        } else if ("logout".equals(actionName)) {
            // 用户退出
            userLogout(req, resp);
        } else if ("userCenter".equals(actionName)) {
            // 用户中心
            userCenter(req, resp);
        } else if ("userHead".equals(actionName)) {
            // 加载头像
            userHead(req, resp);
        } else if ("checkNick".equals(actionName)) {
            checkNick(req, resp);
        } else if ("updateUser".equals(actionName)) {
            // 修改用户信息
            updateUser(req, resp);
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo<User> resultInfo = userService.updateUser(request);
        request.setAttribute("resultInfo", resultInfo);
        request.getRequestDispatcher("user?actionName=userCenter").forward(request, response);
    }

    /**
     * 检查昵称唯一性
     * @param req 请求
     * @param resp 响应
     */
    private void checkNick(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nick = req.getParameter("nick");
        User user = (User) req.getSession().getAttribute("user");
        Integer code = userService.checkNick(nick, user.getUserId());
        // 通过字符串输出流将结果响应给前台的 ajax 的回调函数
        resp.getWriter().write(code+"");
        resp.getWriter().close();
    }

    /**
     * 获取用户头像
     * @param req 请求
     * @param resp 响应
     */

    private void userHead(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1. 得到参数
        String imgHead = req.getParameter("imageName");
        // 2. 得到图片存放的路径
        String rootPath = req.getServletContext().getRealPath("/WEB-INF/upload/");
        // 3. 生成完整路径
        File file = new File(rootPath + imgHead);
        // 4. 得到图片类型
        String pic = imgHead.substring(imgHead.lastIndexOf(".") + 1);
        // 5. 设置不同的响应
        if ("PNG".equalsIgnoreCase(pic)) {
            resp.setContentType("image/png");
        } else if ("JPG".equalsIgnoreCase(pic) || "JPEG".equalsIgnoreCase(pic)) {
            resp.setContentType("image/jpg");
        } else if ("GIF".equalsIgnoreCase(pic)) {
            resp.setContentType("image/gif");
        }
        FileUtils.copyFile(file, resp.getOutputStream());
    }

    /**
     * 个人中心
     * @param request 请求
     * @param response 响应
     */
    private void userCenter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 1. 设置动态首页包含的内容
        request.setAttribute("changePage", "WEB-INF/pages/userCenter.jsp");

        // 2. 转发到首页
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    /**
     * 用户退出
     * @param request 请求
     * @param response 回应
     */
    private void userLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. 销毁 Session
        request.getSession().invalidate();
        // 2. 删除 Cookie
        Cookie cookie = new Cookie("user", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        // 3. 跳转
        response.sendRedirect("login.jsp");
    }

    /**
     * 用户登录
     * @param request request
     * @param response response
     */
    private void userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取参数 姓名，密码
        String userName = request.getParameter("userInput");
        String userPswd = request.getParameter("passwordInput");

        // 2. 调用 Service 层的方法
        ResultInfo<User> resultInfo = userService.userLogin(userName, userPswd);

        // 判断登录成功与失败
        if (resultInfo.getCode() == 1) {  // 成功
            // 将用户信息设置到 session 中
            request.getSession().setAttribute("user", resultInfo.getResult());
            // 判断用户是否勾选记住密码(rememberI=1)
            String rem = request.getParameter("rememberI");
            // 勾选记住密码，则将用户名与密码设置到 cookie 中，设置失效时间，并响应给客户端
            if ("1".equals(rem)) {
                // 得到 cookie 对象
                Cookie cookie = new Cookie("user", userName + "-" + userPswd);
                // 设置失效时间 3 天，3*24小时*60分*60秒
                cookie.setMaxAge(3*24*60*60);

                // 响应
                response.addCookie(cookie);
            } else {
                // 否则，清空原有的 cookie 对象
                Cookie cookie = new Cookie("user", null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
            // 重定向到 index 页面
            response.sendRedirect("index");
        } else {  // 登录失败
            // 将 resultInfo 对象设置到 request 中
            request.setAttribute("resultInfo", resultInfo);

            // 请求转发跳转到登录页面
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
