package com.ccg.note;

import com.ccg.note.dao.UserDao;
import com.ccg.note.po.User;
import org.junit.Test;

public class TestUser {

    @Test
    public void testQueryUserByName(){
        UserDao userDao = new UserDao();
        User user = userDao.queryUserByName("admin");
        System.out.println(user.getUpwd());
    }
}
