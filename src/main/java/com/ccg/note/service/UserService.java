package com.ccg.note.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.ccg.note.dao.UserDao;
import com.ccg.note.po.User;
import com.ccg.note.vo.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.sql.Struct;

public class UserService {

    private final UserDao userDao = new UserDao();

    /**
     * 用户登录
     * @param userName 用户名
     * @param userPwd 密码
     * @return 结果信息
     */
    public ResultInfo<User> userLogin(String userName, String userPwd) {
        ResultInfo<User> resultInfo = new ResultInfo<>();

        // 数据回显，当登录失败时，跳转到登录页面后，之前填的信息将保留在页面上。
        User u = new User();
        u.setUname(userName);
        u.setUpwd(userPwd);
        // 设置到 resultInfo 对象中
        resultInfo.setResult(u);
        // 1. 判断参数是否为空，防止绕过前端直接往接口提交信息
        if (StrUtil.isBlank(userName) || StrUtil.isBlank(userPwd)) {
            resultInfo.setCode(0);
            resultInfo.setMsg("用户姓名或密码不能为空！");
            return resultInfo;
        }

        // 2. 查询用户对象
        User user = userDao.queryUserByname(userName);

        // 3. 判断用户是否为空
        if (user == null) {
            resultInfo.setCode(0);
            resultInfo.setMsg("该用户不存在！");
            return resultInfo;
        }

        // 4. 不为空，对比密码
        //  将前台传递到密码按照 MD5 算法的方法加密
        userPwd = DigestUtil.md5Hex(userPwd);
        // 判断加密后的密码是否相同
        if (!userPwd.equals(user.getUpwd())) { // 密码不相同
            resultInfo.setCode(0);
            resultInfo.setMsg("用户密码不正确");
            return resultInfo;
        }
        resultInfo.setCode(1);
        resultInfo.setResult(user);
        return resultInfo;
    }

    /**
     * 验证昵称的唯一性
     * @param nick 昵称
     * @param userId 用户 id
     * @return 结果
     */
    public Integer checkNick(String nick, Integer userId) {
        if (StrUtil.isBlank(nick)) {
            return 0;
        }
         User user = userDao.queryUserByNickAndUserId(nick, userId);

        if (user != null) {
            return 0;
        }
        return 1;
    }

    /**
     * 修改应用信息
     * @param request 请求
     * @return 结果
     */
    public ResultInfo<User> updateUser(HttpServletRequest request) {
        ResultInfo<User> resultInfo = new ResultInfo<>();

        String nick = request.getParameter("inputNick");
        String mood = request.getParameter("moodText");

        if (StrUtil.isBlank(nick)) {
            resultInfo.setCode(0);
            resultInfo.setMsg("用户昵称不能为空");
        }

        User user = (User) request.getSession().getAttribute("user");
        user.setNick(nick);
        user.setMood(mood);
        // 文件上传
        try {
            // 1. 获取 Part 对象
            Part part = request.getPart("chooseFile");
            // 2. 从头部信息中获取上传的文件名
            String header = part.getHeader("Content-Disposition");
            // 获取具体的请求头值
            String str = header.substring(header.lastIndexOf("=") + 2);
            // 获取上传的文件名
            String fileName = str.substring(0, str.length() - 1);
            if (!StrUtil.isBlank(fileName)) {
                // 用户上传了头像，更新对象中的头像
                user.setHead(fileName);
                String filePath = request.getServletContext().getRealPath("/WEB-INF/upload/");
                // 上传文件
                part.write(filePath + "/" + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int row = userDao.updateUser(user);
        if (row > 0) {
            resultInfo.setCode(1);
            // 更新 Session 中的用户对象
            request.getSession().setAttribute("user", user);
        } else {
            resultInfo.setCode(0);
            resultInfo.setMsg("更新失败");
        }

        return resultInfo;
    }
}
