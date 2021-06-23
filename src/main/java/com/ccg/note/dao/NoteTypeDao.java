package com.ccg.note.dao;

import com.ccg.note.po.NoteType;
import com.ccg.note.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NoteTypeDao {

    public List<NoteType> findTypeListByUserId(Integer userId) {
        String sql = "select typeId, typeName, userId from tb_note_type where userId = ?";
        List<Object> params = new ArrayList<>();
        params.add(userId);
        List<Object> list_b =   BaseDao.queryRows(sql, params, NoteType.class);
        List<NoteType> list = new ArrayList<>();
        for (Object object : list_b) {
            list.add((NoteType) object);
        }

        return list;
    }

    /**
     * 通过类型 ID 去查询——表 tb_note
     * @param typeId 类型 ID
     * @return 结果集
     */
    public long findNoteTypeCountByTypeId(String typeId) {
        String sql = "select count(1) from tb_note where typeId = ?";
        List<Object> params = new ArrayList<>();
        params.add(typeId);
        return (long) BaseDao.findSingleValue(sql, params);
    }

    /**
     * 通过类型 ID 删除——表 tb_note_type
     * @param typeId 类型 ID
     * @return 结果集
     */
    public int deleteTypeByTypeId(String typeId) {
        String sql = "delete from tb_note_type where typeId = ?";
        List<Object> params = new ArrayList<>();
        params.add(typeId);
        return BaseDao.executeUpdate(sql, params);
    }

    /**
     * 验证类型名称可用性返回 0 1
     * @param typeId 类型 id
     * @param typeName 类型名称
     * @param userId 用户名
     * @return 1表示成功
     */
    public Integer checkTypeName(String typeId, String typeName, Integer userId) {
        String sql = "select * from tb_note_type where userId = ? and typeName = ?";
        List<Object> params = new ArrayList<>();
        params.add(userId);
        params.add(typeName);
        NoteType noteType = (NoteType) BaseDao.queryRow(sql, params, NoteType.class);

        if (noteType == null) {
            // 对象为空，表示可用
            return 1;
        } else {
            // 如果是修改操作，则需要判断是否为当前记录本身
            if (typeId.equals(noteType.getTypeId().toString())) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * 添加类型
     * @param typeName 类型名称
     * @param userId 用户 id
     * @return 类型主键
     */
    public Integer addType(String typeName, Integer userId) {
        Integer key = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "insert into tb_note_type (typeName, userId) values (?,?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,typeName);
            preparedStatement.setInt(2,userId);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                //  获取主键
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    key = resultSet.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(resultSet, preparedStatement, connection);
        }

        return key;
    }

    /**
     * 修改类型
     * @param typeName 类型名称
     * @param typeId 类型 id
     * @return 受影响行数
     */
    public Integer updateType(String typeName, String typeId) {
        String sql = "update tb_note_type set typeName = ? where typeId = ?";
        List<Object> params = new ArrayList<>();
        params.add(typeName);
        params.add(typeId);
        return BaseDao.executeUpdate(sql, params);
    }
}
