package com.ccg.note.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 数据库工具类
 */

public class DBUtil {

    // 获取配置文件
    private  static final Properties properties = new Properties();
    static {
        InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            // 通过 load 加载输入流中的对象
            properties.load(in);
            // 获取驱动
            Class.forName(properties.getProperty("jdbcName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     * @return 数据库连接
     */

    public static Connection getConnection(){
        Connection connection = null;
        try {
            // 获取数据库相关信息
            String dbUrl = properties.getProperty("dbUrl");
            String user = properties.getProperty("user");
            String pswd = properties.getProperty("pswd");

            connection = DriverManager.getConnection(dbUrl, user, pswd);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭资源
     * @param resultSet 结果集对象
     * @param preparedStatement 预编译对象
     * @param connection 数据库连接
     */
    public static  void close(ResultSet resultSet,
                              PreparedStatement preparedStatement,
                              Connection connection) {
        // 当资源不为空时关闭
        try {
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            if (connection != null)
                connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
