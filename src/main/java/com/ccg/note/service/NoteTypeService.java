package com.ccg.note.service;

import cn.hutool.core.util.StrUtil;
import com.ccg.note.dao.NoteTypeDao;
import com.ccg.note.po.NoteType;
import com.ccg.note.vo.ResultInfo;

import java.util.List;

public class NoteTypeService {

    private final NoteTypeDao noteTypeDao = new NoteTypeDao();

    /**
     * 通过用户 ID 查询信息
     * @param userId 用户 ID
     * @return 结果集合
     */
    public List<NoteType> findTypeList(Integer userId) {
        return noteTypeDao.findTypeListByUserId(userId);
    }

    /**
     * 删除类型
     * @param typeId 类型 ID
     * @return 消息结果
     */
    public ResultInfo<NoteType> deleteType(String typeId) {
        ResultInfo<NoteType> resultInfo = new ResultInfo<>();

        if (StrUtil.isBlank(typeId)) {
            resultInfo.setCode(0);
            resultInfo.setMsg("系统异常，请重试。");
            return resultInfo;
        }
        long noteCount = noteTypeDao.findNoteTypeCountByTypeId(typeId);
        if (noteCount > 0) {
            resultInfo.setCode(0);
            resultInfo.setMsg("该类型存在云记，不能删除。");
            return resultInfo;
        }

        int row = noteTypeDao.deleteTypeByTypeId(typeId);

        if (row > 0) {
            resultInfo.setCode(1);
            resultInfo.setMsg("删除成功！");
        } else {
            resultInfo.setCode(0);
            resultInfo.setMsg("删除失败。");
        }

        return resultInfo;
    }

    /**
     * 添加或修改类型
     * @param typeName 类型名称
     * @param userId 用户 id
     * @param typeId 类型 id
     * @return 信息
     */
    public ResultInfo<Integer> addOrUpdate(String typeName, Integer userId, String typeId) {
        ResultInfo<Integer> resultInfo = new ResultInfo<>();
        if (StrUtil.isBlank(typeName)) {
            resultInfo.setCode(0);
            resultInfo.setMsg("类型名称不能为空");
            return resultInfo;
        }

        // 查询当前用户下类型是否唯一
        Integer code = noteTypeDao.checkTypeName(typeId, typeName, userId);
        // 如果不可用 code=0
        if (code == 0) {
            resultInfo.setCode(0);
            resultInfo.setMsg("类型名称已存在，请重新输入！");
            return resultInfo;
        }

        Integer key = null;  // 主键或受影响行数
        // 判断添加与修改
        if (StrUtil.isBlank(typeId)) {
            // 为空，表示添加操作
            key = noteTypeDao.addType(typeName, userId);
        } else {
            // 非空，表示修改操作
            key = noteTypeDao.updateType(typeName, typeId);
        }

        if (key > 0) {
            resultInfo.setCode(1);
            resultInfo.setResult(key);
        } else {
            resultInfo.setCode(0);
            resultInfo.setMsg("更新失败");
        }
        return  resultInfo;
    }
}
