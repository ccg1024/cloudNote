package com.ccg.note.filter;

import cn.hutool.core.util.StrUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求中中文
 *  乱码原因
 *      服务器默认的解析编码为 ISO-8859-1, 不支持中文
 *  乱码情况
 *      POST 请求
 *          Tomcat7 及以下版本   乱码
 *          Tomcat8 及以上版本   乱码
 *      GET 请求
 *          Tomcat7 及以下版本   乱码
 *          Tomcat8 及以上版本   不乱码
 *  解决方案
 *      POST 请求
 *          通过 request.setCharacterEncoding("UTF-8") 设置编码格式来解决，只针对 POST 有效
 *      GET 请求
 *          Tomcat7 及以下
 *              new String(request.getParameter("xxx").getBytes("ISO-8859-1", "UTF-8"));
 *
 */
@WebFilter("/*")  // 过滤所有资源
public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 基于 Http
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 处理 POST 请求
        request.setCharacterEncoding("UTF-8");

        // 得到请求类型
        String method = request.getMethod();
        // 若为 GET 请求。忽略大小写比较
        if ("GET".equalsIgnoreCase(method)) {
            // 一般格式为 Apache Tomcat /x.x.xx... 斜杠后面跟版本号
            String serverInfo = request.getServletContext().getServerInfo();
            String version = serverInfo.substring(serverInfo.lastIndexOf("/")+1, serverInfo.indexOf("."));
            if (version != null && Integer.parseInt(version) < 8) {
                // 以下为接近重写底层的方法，了解思路。之后可以用其他方法解决编码问题
                MyWapper myWapper = new MyWapper(request);  // 传递 request 对象
                // 通过 myWapper 对象中的 request 使用 getParameter，由于就近原则，会使用改造后的方法。
                filterChain.doFilter(myWapper, response);
                return;
            }
            // 不是 7 及以下版本将使用原来的 request 对象
        }
        // 放行资源
        filterChain.doFilter(request, response);
    }

    /**
     * 构造一个内部类，本质为 request 对象的类
     * 因为要重新解释接受到的中文字符，就需要改变 request 内部的编码过程
     * 就需要对 request 内代码进行修饰，通过继承的方式实现
     * 重写 getParameter() 方法，
     */
    class MyWapper extends HttpServletRequestWrapper {

        // 提升构造器中 request 对象的作用域
        private HttpServletRequest request;

        /**
         * 得到处理的 request 对像
         * @param request 服务器产生的 request 对象
         */
        public MyWapper(HttpServletRequest request) {
            super(request);
            this.request = request; // 自己写的 request 接到了服务器产生 request 对象。
        }

        /**
         * 重写 getParameter
         * @param name 前端参数
         * @return 处理后结果
         */
        @Override
        public String getParameter(String name) {
            // 获取参数
            String value = request.getParameter(name);

            // 判断是否为空
            if (StrUtil.isBlank(value)) {
                return value;
            }

            // 通过 new String() 处理乱码
            try {
                value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return value;
        }
    }

}
