package com.web.mall.pojo;

import java.util.List;

/**
 * @author iven
 * @version 2021.2.3
 * @date 2022/9/1 19:40
 */
public class PageResp<T> {
    private long total;
    private List<T> list;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageResp{" +
                "total=" + total +
                ", list=" + list +
                '}';
    }
}
