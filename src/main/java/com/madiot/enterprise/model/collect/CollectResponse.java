package com.madiot.enterprise.model.collect;

import java.util.List;

/**
 * Created by DELL on 2016/6/26.
 */
public class CollectResponse {

    private int total;

    private List<EnterpriseCollect> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<EnterpriseCollect> getRows() {
        return rows;
    }

    public void setRows(List<EnterpriseCollect> rows) {
        this.rows = rows;
    }
}
