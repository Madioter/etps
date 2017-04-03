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
     * 所属辖区id
     */
    private int localadm;

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

    public int getLocaladm() {
        return localadm;
    }

    public void setLocaladm(int localadm) {
        this.localadm = localadm;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (!StringUtil.isEmpty(entname)) {
            builder.append("entname=" + entname + "&");
        }
        if (localadm > 0) {
            builder.append("localadm=" + localadm + "&");
        }
        builder.append("page=" + (page + 1) + "&rows=" + rows);
        return builder.toString();
    }
}
