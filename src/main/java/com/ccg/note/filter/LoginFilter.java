package com.ccg.note.filter;

import com.ccg.note.po.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 非法访问拦截
 * 拦截资源：
 *      所有资源    /*
 *
 * 需要被放行的资源
 *      1. 指定页面，放行 例如：登录页面，注册页面
 *      2. 静态资源，放行
 *      3. 指定行为，放行 无需登录就可执行的操作，例如：登录操作
 *      4. 登录状态，放行 判断 session 作用域中是否存在 user 对象(之前写登录时，存的对象名为 user)，不存在，则拦截跳转到登录页面
 *
 *  免登录
 *      通过 Cookie 与 Session 对象实现
 *      使用情景：
 *          用户处于未登录状态，且请求需要登录才能访问的资源时。
 *      实现：
 *          从 Cookie 对象中获取用户的姓名与密码，自动执行登录操作
 *              1. 获取 Cookie 数组     request.getCookies()
 *              2. 判断 Cookie 数组
 *              3. 遍历 Cookie 数组，获取指定的 Cookie 对象——登录模块中存入的对象名为 user
 *              4. 得到对应的 Cookie 对象的 value （姓名与密码：userName-userPwd）
 *              5. 通过 split() 方法将 value 字符串分割成数组
 *              6. 从数组中分别得到对应的姓名与密码
 *              7. 请求转发到登录操作    user?actionName=login&userName=姓名&userPwd=密码
 *              8. return
 *
 * 以上选项都不满足，则拦截请求，跳转到登录页面。
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 基于 HTTP
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 得到访问的路径。格式：项目路径/资源路径
        String path = request.getRequestURI();

        // 1. 指定页面，放行。例如：登录页面
        if (path.contains("/login.jsp")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. 静态资源，放行
        if (path.contains("/statics")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. 指定行为，放行。例如：登录行为
        if (path.contains("/user")) {
            // 得到用户行为
            String actionName = request.getParameter("actionName");
            if ("login".equals(actionName)) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        // 4. 登录状态，放行。判断 session 中是否存在 user 对象
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("user".equals(cookie.getName())) {
                    String value = cookie.getValue();
                    String[] val = value.split("-");
                    String userName = val[0];
                    String userPwd = val[1];
                    String url = "user?actionName=login&userInput=" + userName + "&passwordInput=" + userPwd;
                    System.out.println("使用 Cookie");
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                }
            }
        }
        // 拦截请求，重定向跳转到登陆页面
        response.sendRedirect("login.jsp");
    }

    @Override
    public void destroy() {

    }
}
