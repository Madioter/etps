package com.madiot.enterprise.model.collect;

import com.madiot.enterprise.common.util.StringUtil;

/**
 * Created by DELL on 2016/6/26.
 */
public class CollectRequest {

    /**
     * 企业名
     */
    private String entname;

    /**
     * 第几页
     */
    private int page;

    /**
     * 每页50条
     */
    private int rows = 100;

    public String getEntname() {
        return entname;
    }

    public void setEntname(String entname) {
        this.entname = entname;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public String toString() {
        if (!StringUtil.isEmpty(entname)) {
            return "entname=&page=" + (page + 1) + "&rows=" + rows;
        } else {
            return "page=" + (page + 1) + "&rows=" + rows;
        }
    }
}
