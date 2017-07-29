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
    private Integer localadm;

    /**
     * 企业类型
     */
    private Integer enttype;

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

    public void setLocaladm(Integer localadm) {
        this.localadm = localadm;
    }

    public Integer getEnttype() {
        return enttype;
    }

    public void setEnttype(Integer enttype) {
        this.enttype = enttype;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (!StringUtil.isEmpty(entname)) {
            builder.append("entname=" + entname + "&");
        }
        if (localadm != null) {
            builder.append("localadm=" + localadm + "&");
        }
        if (enttype != null) {
            builder.append("enttype=" + enttype + "&");
        }
        builder.append("page=" + (page + 1) + "&rows=" + rows);
        return builder.toString();
    }
}
