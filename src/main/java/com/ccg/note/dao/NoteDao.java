package com.ccg.note.dao;

import cn.hutool.core.util.StrUtil;
import com.ccg.note.po.Note;
import com.ccg.note.vo.NoteVo;

import java.util.ArrayList;
import java.util.List;

public class NoteDao {

    /**
     * 添加或修改云记
     * @param note 云记实体类
     * @return 受影响行数
     */
    public int addOrUpdate(Note note) {
        String sql = "";
        List<Object> params = new ArrayList<>();
        params.add(note.getTypeId());
        params.add(note.getTitle());
        params.add(note.getContent());

        // 判断noteId是否为空
        if (note.getNoteId() == null) {
            sql = "insert into tb_note (typeId, title, content, pubTime) values (?,?,?,now())";
        } else {  // 修改操作
            sql = "update tb_note set typeId=?,title=?,content=? where noteId=?";
            params.add(note.getNoteId());
        }
        return BaseDao.executeUpdate(sql, params);
    }

    /**
     * 查询用户云记总数
     * @param userId 用户 id
     * @return 云记数量
     */
    public long findNoteCount(Integer userId, String title, String date, String typeId) {
        String sql = "select count(1) from tb_note n INNER JOIN tb_note_type t on n.typeId = t.typeId where userId = ?";
        List<Object> params = new ArrayList<>();
        params.add(userId);

        if (!StrUtil.isBlank(title)) {
            // 需按标题模糊查询
            sql += " and title like concat('%',?,'%') ";
            params.add(title);
        } else if (!StrUtil.isBlank(date)) {
            // 需按日期模糊查询
            sql += " and date_format(pubTime, '%Y年%m月') = ? ";
            params.add(date);
        } else if (!StrUtil.isBlank(typeId)) {
            // 需按类型模糊查询
            sql += " and n.typeId = ? ";
            params.add(typeId);
        }

        return (long) BaseDao.findSingleValue(sql, params);
    }

    /**
     * 分页查询云记列表
     * @param userId 用户 id
     * @param index 开始下标
     * @param pageSize 页面大小
     * @return 结果集
     */
    public List<Note> findNoteListByPage(Integer userId, Integer index, Integer pageSize,
                                         String title, String date, String typeId) {
        String sql = "SELECT noteId,title,pubTime FROM tb_note n INNER JOIN tb_note_type t ON n.typeId = t.typeId WHERE userId = ? ";
        List<Object> params = new ArrayList<>();
        params.add(userId);

        if (!StrUtil.isBlank(title)) {
            // 需按标题模糊查询
            sql += " and title like concat('%',?,'%') ";
            params.add(title);
        } else if (!StrUtil.isBlank(date)) {
            // 需按日期模糊查询
            sql += " and date_format(pubTime, '%Y年%m月') = ? ";
            params.add(date);
        } else if (!StrUtil.isBlank(typeId)) {
            // 需按类型模糊查询
            sql += " and n.typeId = ? ";
            params.add(typeId);
        }

        // 拼接分页 sql 语句
        sql += " order by pubTime desc limit ?,?";
        params.add(index);
        params.add(pageSize);
        List<Object> noteListO =  BaseDao.queryRows(sql, params, Note.class);
        List<Note> noteList = new ArrayList<>();
        for (Object obj : noteListO) {
            noteList.add((Note) obj);
        }

        return noteList;
    }

    /**
     * 通过日期分组返回云记数量
     * @param userId 用户 id
     * @return 列表集合
     */
    public List<NoteVo> findNoteCountByDate(Integer userId) {
        String sql = "SELECT COUNT(1) noteCount,DATE_FORMAT(pubTime,'%Y年%m月') groupName FROM tb_note n " +
                " INNER JOIN tb_note_type t " +
                " ON n.typeId = t.typeId " +
                " WHERE userId = ? " +
                " GROUP BY DATE_FORMAT(pubTime,'%Y年%m月') " +
                " ORDER BY DATE_FORMAT(pubTime,'%Y年%m月') DESC ";
        List<Object> params = new ArrayList<>();
        params.add(userId);

        List<Object> listO = BaseDao.queryRows(sql, params, NoteVo.class);
        List<NoteVo> list = new ArrayList<>();
        for (Object object : listO) {
            list.add((NoteVo) object);
        }
        return list;
    }

    /**
     * 通过类型分组返回云记数量
     * @param userId 用户 id
     * @return 列表集合
     */
    public List<NoteVo> findNoteCountByType(Integer userId) {
        String sql = "SELECT COUNT(noteId) noteCount, t.typeId, typeName groupName FROM tb_note n " +
                " RIGHT JOIN tb_note_type t ON n.typeId = t.typeId WHERE userId = ? " +
                " GROUP BY t.typeId ORDER BY COUNT(noteId) DESC ";
        List<Object> params = new ArrayList<>();
        params.add(userId);

        List<Object> listO = BaseDao.queryRows(sql, params, NoteVo.class);
        List<NoteVo> list = new ArrayList<>();
        for (Object object : listO) {
            list.add((NoteVo) object);
        }
        return list;
    }

    /**
     * 通过 id 查询云记对象
     * @param noteId 云记 id
     * @return 云记对象
     */
    public Note findNoteById(String noteId) {
        String sql = "select noteId, title, content, pubTime, typeName,n.typeId from tb_note n " +
                " inner join tb_note_type t on n.typeId=t.typeId where noteId = ?";
        List<Object> params = new ArrayList<>();
        params.add(noteId);
        return (Note) BaseDao.queryRow(sql, params, Note.class);
    }

    /**
     * 通过 id 删除云记
     * @param noteId 云记 id
     * @return 受影响行数
     */
    public int deleteNote(String noteId) {
        String sql = "delete from tb_note  where noteId = ?";
        List<Object> params = new ArrayList<>();
        params.add(noteId);
        return BaseDao.executeUpdate(sql, params);
    }
}
