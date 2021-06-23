package com.ccg.note.service;

import cn.hutool.core.util.StrUtil;
import com.ccg.note.dao.NoteDao;
import com.ccg.note.po.Note;
import com.ccg.note.util.Page;
import com.ccg.note.vo.NoteVo;
import com.ccg.note.vo.ResultInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteService {

    private NoteDao noteDao = new NoteDao();

    /**
     * 添加或修改云记 业务层
     * @param typeId 类型 id
     * @param title 标题
     * @param content 内容
     * @return 结果
     */
    public ResultInfo<Note> addOrUpdate(String typeId, String title, String content, String noteId) {
        ResultInfo<Note> resultInfo = new ResultInfo<>();

        if (StrUtil.isBlank(typeId)) {
            resultInfo.setCode(0);
            resultInfo.setMsg("请选择云记类型。");
            return resultInfo;
        }
        if (StrUtil.isBlank(title)) {
            resultInfo.setCode(0);
            resultInfo.setMsg("云记标题不能为空。");
            return resultInfo;
        }
        if (StrUtil.isBlank(content)) {
            resultInfo.setCode(0);
            resultInfo.setMsg("云记内容不能为空。");
            return resultInfo;
        }
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setTypeId(Integer.parseInt(typeId));
        // resultInfo.setResult(note);
        // 判断云记 id 是否为空
        if (!StrUtil.isBlank(noteId)) {
            note.setNoteId(Integer.parseInt(noteId));
        }

        int row = noteDao.addOrUpdate(note);

        if (row > 0) {
            resultInfo.setCode(1);
        } else {
            resultInfo.setCode(0);
            resultInfo.setMsg("添加云记失败。");
            resultInfo.setResult(note);
        }
        return resultInfo;
    }

    public Page<Note> findNoteListByPage(String pageNumStr, String pageSizeStr, Integer userId,
                                         String title, String date, String typeId) {

        Integer pageNum = 1;  // 默认当前页为第 1 页
        Integer pageSize = 5;  // 默认每页显示 5 条

        // 校验参数
        if (!StrUtil.isBlank(pageNumStr)) {
            pageNum = Integer.parseInt(pageNumStr);
        }
        if (!StrUtil.isBlank(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        // 当前用户云记总数
        long count = noteDao.findNoteCount(userId, title, date, typeId);

        if (count < 1) {  // 没有数据
            return null;
        }

        // 创建 page 对象
        Page<Note> page = new Page<>(pageNum, pageSize, count);
        // 得到数据库中分页查询的开始下标
        Integer index = (pageNum - 1) * pageSize;

        // 获取页面列表
        List<Note> noteList = noteDao.findNoteListByPage(userId, index, pageSize, title, date, typeId);

        page.setDataList(noteList);

        return page;
    }

    /**
     * 通过日期分组查询云记数量
     * @param userId 用户 id
     * @return 分组列表
     */
    public List<NoteVo> findNoteCountByDate(Integer userId) {
        return noteDao.findNoteCountByDate(userId);
    }

    /**
     * 通过类型分组查询云记数量
     * @param userId 用户 id
     * @return 分组列表
     */
    public List<NoteVo> findNoteCountByType(Integer userId) {
        return noteDao.findNoteCountByType(userId);
    }

    /**
     * 查询云记详情
     * @param noteId 云记id
     * @return 云记对象
     */
    public Note findNoteById(String noteId) {
        if (StrUtil.isBlank(noteId)) {
            return null;
        }
        return noteDao.findNoteById(noteId);
    }

    /**
     * 删除云记
     * @param noteId 云记 id
     * @return 结果码 1 成功 0 失败
     */
    public Integer deleteNote(String noteId) {
        if (StrUtil.isBlank(noteId)) {
            return 0;
        }
        int row = noteDao.deleteNote(noteId);
        if (row > 0) {
            return 1;
        }
        return 0;
    }

    /**
     * 通过月份查询云记数量
     * @param userId 用户 id
     * @return resultInfo 对象
     */
    public ResultInfo<Map<String, Object>> queryNoteCountByMonth(Integer userId) {
        ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();

        List<NoteVo> noteVos = noteDao.findNoteCountByDate(userId);

        if (noteVos != null && noteVos.size() > 0) {
            // 得到月份
            List<String> monthList = new ArrayList<>();
            // 得到云记数量
            List<Integer> noteCountList = new ArrayList<>();

            for (NoteVo noteVo : noteVos) {
                monthList.add(noteVo.getGroupName());
                noteCountList.add((int) noteVo.getNoteCount());
            }

            // 准备 Map 对象，封装月份与对应的云记数量
            Map<String, Object> map = new HashMap<>();
            map.put("monthArray", monthList);
            map.put("dataArray", noteCountList);

            resultInfo.setCode(1);
            resultInfo.setResult(map);
        }

       return resultInfo;
    }
}
