package com.ccg.note.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 将对象转换成 JSON 格式的字符串，响应给 ajax 的回调函数
 */
public class JsonUtil {

    public static void toJson(HttpServletResponse response, Object result) {

        try {
            // 使用流
            // 设置响应类型及编码格式 json
            response.setContentType("application/json;charset=UTF-8");
            // 得到字符串输出流
            PrintWriter out = response.getWriter();
            // 通过 fastjson 方法，将 ResultInfo 对象转化成 JSON 格式的字符串
            String json = JSON.toJSONString(result);
            // 通过输出流 输出
            out.write(json);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
