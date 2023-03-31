package com.lq.accident.model.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Data
public class MyPage<T> extends Page<T> {
    private List<Long> pageNums;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private long nextPage;
    private long prePage;
    public MyPage(){}

    public MyPage(long current, long size) {
        super(current, size);
    }

    public void setSelf(){
        this.hasNextPage = hasNext();
        if (hasNextPage) nextPage = current+1;
        hasPreviousPage = hasPrevious()&&getPages()>=current;
        if (hasPreviousPage) prePage = current-1;

        isFirstPage = current == 1;
        isLastPage = current == getPages();
        // 左右2侧页码个数
        long navigatePage = 2;
        if (current<1||getPages()<current) return;
        TreeSet<Long> pageNums = new TreeSet<>();
        pageNums.add(current);
        long page = current;
        // 计算左减个数
        long subNum = navigatePage;
        if (getPages()-current<navigatePage)
            subNum = subNum+navigatePage-(getPages()-current);
        for (int i = 0; i < subNum; i++) {
            page--;
            if (page>0)pageNums.add(page);
            else break;
        }

        page = current;
        // 计算右加个数
        long addNum = navigatePage;
        if (current -1 <navigatePage)
            addNum = addNum +navigatePage - (current-1);
        for (int i = 0; i < addNum; i++) {
            page++;
            if (page<=getPages())pageNums.add(page);
            else break;
        }
        this.pageNums = new ArrayList<>(pageNums);

    }

}
