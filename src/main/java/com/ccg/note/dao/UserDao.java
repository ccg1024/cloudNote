package com.ccg.note.dao;

import com.ccg.note.po.User;
import com.ccg.note.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    /**
     * 使用 BaseDao 版的用户名查询
     * @param userName 用户名
     * @return 查询结果
     */
    public User queryUserByName(String userName) {
        User user = null;
        String sql = "select * from tb_user where uname = ?";
        // 设置参数集合
        List<Object> params = new ArrayList<>();
        params.add(userName);
        user = (User) BaseDao.queryRow(sql, params, User.class);
        return user;
    }

    /**
     * 通过用户名查询
     * @param userName 用户名
     * @return 结果
     */
    public User queryUserByname(String userName) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // 1. 获取数据库连接
            connection = DBUtil.getConnection();
            // 2. 定义 sql 语句
            String sql = "select * from tb_user where uname = ?";
            // 3. 预编译
            preparedStatement = connection.prepareStatement(sql);
            // 4. 设置参数
            preparedStatement.setString(1,userName);
            // 5. 执行查询，返回结果集
            resultSet = preparedStatement.executeQuery();
            // 6. 判断并分析结果集
            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUname(userName);
                user.setHead(resultSet.getString("head"));
                user.setNick(resultSet.getString("nick"));
                user.setMood(resultSet.getString("mood"));
                user.setUpwd(resultSet.getString("upwd"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(resultSet,preparedStatement,connection);
        }
        return user;
    }

    /**
     * 通过昵称与用户 id 限制查询对象
     * @param nick 查询昵称
     * @param userId 需排除的用户 id
     * @return 结果
     */
    public User queryUserByNickAndUserId(String nick, Integer userId) {

        String sql = "select * from tb_user where nick = ? and userId != ?";
        List<Object> params = new ArrayList<>();
        params.add(nick);
        params.add(userId);
        return (User) BaseDao.queryRow(sql, params, User.class);
    }

    /**
     * 更新用户信息
     * @param user 更新用户
     * @return 结果
     */
    public int updateUser(User user) {
        String sql = "update tb_user set nick = ?, mood = ?, head = ? where userId = ?";
        List<Object> params = new ArrayList<>();
        params.add(user.getNick());
        params.add(user.getMood());
        params.add(user.getHead());
        params.add(user.getUserId());
        return BaseDao.executeUpdate(sql, params);
    }
}
