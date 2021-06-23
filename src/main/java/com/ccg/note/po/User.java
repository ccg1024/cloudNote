package com.ccg.note.po;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

// 用来如下的注解就可以不需要写 Getter 与 Setter 方法
// 来自插件 lombok
@Getter
@Setter
public class User implements Serializable {

    private Integer userId; // 用户ID
    private String uname; // 用户名称
    private String upwd; // 用户密码
    private String nick; // 用户昵称
    private String head; // 用户头像
    private String mood; // 用户签名

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", uname='" + uname + '\'' +
                ", upswd='" + upwd + '\'' +
                ", nick='" + nick + '\'' +
                ", head='" + head + '\'' +
                ", mood='" + mood + '\'' +
                '}';
    }
}
