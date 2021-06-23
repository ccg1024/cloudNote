# cloudNote  
B 站项目——云日记，使用 tomcat + jsp + servlet + mysql 完成

<img src=".\img\1.png" style="zoom:50%;" />

<img src=".\img\2.png" style="zoom:50%;" />

## 数据库

**用户表**

| 字段名称 | 字段类型 | 字段描述   |
| :------- | :------- | :--------- |
| userId   | int      | 主键，自增 |
| uname    | varchar  | 用户名称   |
| upwd     | varchar  | 用户密码   |
| nick     | varchar  | 用户昵称   |
| head     | varchar  | 用户头像   |
| mood     | varchar  | 用户心情   |

**类型表**

| 字段名称   | 字段类型 | 字段描述   |
| ---------- | -------- | ---------- |
| typeId     | int      | 主键，自增 |
| typeName   | varchar  | 类型名称   |
| **userId** | int      | 用户ID     |

**云集表**

| 字段名称   | 字段类型 | 字段描述   |
| ---------- | -------- | ---------- |
| noteId     | int      | 主键，自增 |
| title      | varchar  | 标题       |
| content    | text     | 内容       |
| **typeId** | int      | 类型ID     |
| pubTime    | datetime | 发布时间   |
| lon        | float    | 经度       |
| lat        | float    | 维度       |

