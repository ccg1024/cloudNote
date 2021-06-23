package com.ccg.note.util;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分页工具类
 */
@Getter
@Setter
public class Page<T> {

    private Integer pageNum;  // 当前页，默认第一页，从前台传递
    private Integer pageSize;  // 每页显示数量，可前台传递，可后台设定
    private long totalCount;  // 总记录数

    private Integer totalPages;  // 总页数 = 总记录数 / 每页显示数量
    private Integer prePage;  // 上一页
    private Integer nextPage;  // 下一页

    // 默认导航页数显示 10 个，可以自己调整
    private Integer startNavPage;  // 导航开始页（当前页-5，如果当前页-5小于1，则开始页为1
    private Integer endNavPage;  // 导航结束页

    private List<T> dataList;  // 当前页的数据集合

    /**
     * 通过该构造函数得到基本的数据，用来计算其他数据
     * @param pageNum 当前页
     * @param pageSize 每页显示数量
     * @param totalCount 总记录数
     */
    public Page(Integer pageNum, Integer pageSize, long totalCount) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;

        this.totalPages = (int) Math.ceil(totalCount/(pageSize * 1.0));
        this.prePage = Math.max(pageNum - 1, 1);
        this.nextPage = pageNum + 1 > totalPages ? totalPages : pageNum + 1;

        this.startNavPage = pageNum - 5;
        this.endNavPage = pageNum + 4;

        if (this.startNavPage < 1) {
            this.startNavPage = 1;
            this.endNavPage = this.startNavPage + 9 > totalPages ? totalPages: this.startNavPage + 9;
        }

        if (this.endNavPage > totalPages) {
            this.endNavPage = totalPages;
            this.startNavPage = Math.max(this.endNavPage - 9, 1);
        }
    }
}
