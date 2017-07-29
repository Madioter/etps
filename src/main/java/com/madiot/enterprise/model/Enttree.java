package com.madiot.enterprise.model;

/**
 * Created by julian on 2017/4/3.
 */
public class Enttree {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 状态： 1：have child， 0：no child
     */
    private Integer state;

    /**
     * 是否初始化：1：have init， 0： no init
     */
    private Integer isInit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsInit() {
        return isInit;
    }

    public void setIsInit(Integer isInit) {
        this.isInit = isInit;
    }
}
