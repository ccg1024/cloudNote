package com.ccg.note.dao;

import com.ccg.note.po.User;
import com.ccg.note.util.DBUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装数据库操作中的重复行为
 * 1. 获取数据库连接
 * 2. 定义 sql 语句
 * 3. 预编译
 * 4. 设置参数 注：下标从 1 开始
 * 5. 执行
 * 6. 关闭资源
 *
 * 基础的 JDBC 操作
 *      更新操作 (添加、修改、删除)
 *      查询操作
 *          1. 查询一个字段 （只会返回一条记录且只有一个字段，一般用于查总数）
 *          2. 查询集合
 *          3. 查询某个对象
 */
public class BaseDao {

    /**
     * 更新操作：添加、修改、删除
     * @param sql 执行 sql 语句
     * @param params 所需参数集合
     * @return 受影响行数
     */
    public static int executeUpdate(String sql, List<Object> params) {
        int row = 0; // 受影响行数
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);  // 预编译
            if (params != null && params.size() > 0) {  // 有参数则设置
                // 循环设置参数，类型为 Object ，下标从 1 开始
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i+1, params.get(i));
                }
            }
            // 执行更新
            row = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, preparedStatement, connection);
        }
        return row;
    }

    /**
     * 查询一个字段
     * @param sql 查询语句
     * @param params 参数
     * @return 查询结果
     */
    public static Object findSingleValue(String sql, List<Object> params) {
        Object obj = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (params != null && params.size() > 0) {  // 有参数则设置
                // 循环设置参数，类型为 Object ，下标从 1 开始
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i+1, params.get(i));
                }
            }
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                obj = resultSet.getObject(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(resultSet, preparedStatement, connection);
        }

        return obj;
    }

    /**
     * 查询集合
     * 前面步骤相同，执行查询后
     * 得到结果集的元数据对象
     * 判断分析结果集
     *      实例化对象
     *      遍历查询的字段数量，得到每一个列名
     *      通过反射，使用列名得到对应的 field 对象 （实体类中对应的字段）
     *      拼接 set 方法，得到字符串
     *      通过反射，将 set 方法字符串反射称类中对应的 set 方法
     *      通过 invoke 调用 set 方法 （代理）
     *      将对应的 JavaBean 设置到集合中
     * @param sql sql 语句
     * @param params 参数
     * @param cls 类型
     * @return 结果集
     */
    public static List<Object> queryRows(String sql, List<Object> params, Class<?> cls) {
        List<Object> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (params != null && params.size() > 0) {  // 有参数则设置
                // 循环设置参数，类型为 Object ，下标从 1 开始
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i+1, params.get(i));
                }
            }
            resultSet = preparedStatement.executeQuery();
            // 得到结果集的元数据对象
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            // 得到查询的字段数量
            int fieldNum = resultSetMetaData.getColumnCount();
            // 判断并分析结果集
            while (resultSet.next()) {
                // 实例化对象 以 User 类为例
                Object object = cls.newInstance(); // User.class.newInstance()
                // 遍历查询字段数量，得到数据库中查询的每个列名
                for (int i = 1; i <= fieldNum; i++) {
                    // 得到查询的每一个列名
                    // getColumnLabel(): 获取列名或别名
                    // getColumnName(): 获取列名
                    String columnName = resultSetMetaData.getColumnLabel(i);  // userId
                    // System.out.println("columnName: " + columnName );
                    // 通过反射，使用列名得到对应的 field 对象
                    Field field = cls.getDeclaredField(columnName);  // Integer userId 中的 userId
                    // System.out.println("field: " + field);
                    // 拼接 set 方法的名字，字符串
                    String setMethod = "set" + columnName.substring(0,1).toUpperCase() + columnName.substring(1);
                    // 通过反射，将 set 方法字符串转化成对应的 set 方法
                    Method method = cls.getDeclaredMethod(setMethod, field.getType());  // setUserId(Integer userId)
                    // 得到查询的每一个字段对应的值
                    Object value = resultSet.getObject(columnName);
                    // 通过 invoke 方法调用 set 方法
                    method.invoke(object, value);  // user.setUserId(1)
                }
                list.add(object);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(resultSet, preparedStatement, connection);
        }

        return list;
    }

    public static Object queryRow(String sql, List<Object> params, Class<?> cls) {
        List<Object> list = queryRows(sql, params, cls);
        Object object = null;
        // 如果集合不为空，返回第一条数据
        if (list.size() > 0) {
            object = list.get(0);
        }
        return object;
    }
}
