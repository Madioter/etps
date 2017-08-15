package com.madiot.enterprise.model;

import java.sql.Blob;
import java.util.Date;

/**
 * Created by DELL on 2016/7/5.
 */
public class Attachment {

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 创建时间
     */
    private Date addTime;

    /**
     * 文件内容
     */
    private Object fileContent;

    /**
     * 异常信息
     */
    private String error;

    /**
     * 附件状态
     */
    private int state = AttachmentState.START_STATE;

    /**
     * 参数
     */
    private String param;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Object getFileContent() {
        return fileContent;
    }

    public void setFileContent(Object fileContent) {
        this.fileContent = fileContent;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
