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
    public MyPage(){}

    public MyPage(long current, long size) {
        super(current, size);
    }

    public void setSelf(){
        this.hasNextPage = hasNext();
        hasPreviousPage = hasPrevious();
        if (current == 1) isFirstPage = true;
        else isFirstPage = false;
        if (current == getPages()) isLastPage = true;
        else isLastPage = false;
        TreeSet<Long> pageNums = new TreeSet<>();
        pageNums.add(current);
        long page = current;
        for (int i = 0; i < 2; i++) {
            page--;
            if (page>0)pageNums.add(page);
        }
        page = current;
        for (int i = 0; i < 2; i++) {
            page++;
            if (page<=getPages())pageNums.add(page);
        }
        this.pageNums = new ArrayList<>(pageNums);

    }

}
