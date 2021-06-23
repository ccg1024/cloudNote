package com.ccg.note;

import com.ccg.note.dao.BaseDao;
import com.ccg.note.dao.UserDao;
import com.ccg.note.po.NoteType;
import com.ccg.note.util.DBUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单元测试
 * 1. 方法返回值，建议使用 void
 * 2. 参数列表，建议使用空参
 */
public class TestDB {

    // 使用日志工厂类，记录日志
    private Logger logger = LoggerFactory.getLogger(TestDB.class);

    @Test
    public void testDB() {
        System.out.println(DBUtil.getConnection());
        logger.info("获取数据库连接" + DBUtil.getConnection());
    }

    @Test
    public void testBaseDao() {
        String sql = "update tb_user set nick='aaa' where userId=1";
        String sql1 = "select * from tb_note_type";
        System.out.println(BaseDao.queryRows(sql1, null, NoteType.class));
    }
}
