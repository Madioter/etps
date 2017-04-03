package com.madiot.enterprise.common.http;

import org.springframework.stereotype.Repository;

/**
 * Created by DELL on 2016/8/21.
 */
@Repository
public class ConnectInfo {

    private String collectUrl;

    private String loginUrl;

    private String imageUrl;

    private String defaultUserName;

    private String defaultPassword;

    private String admtreeUrl;

    private int topadmId;

    public String getCollectUrl() {
        return collectUrl;
    }

    public void setCollectUrl(String collectUrl) {
        this.collectUrl = collectUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDefaultUserName() {
        return defaultUserName;
    }

    public void setDefaultUserName(String defaultUserName) {
        this.defaultUserName = defaultUserName;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public String getAdmtreeUrl() {
        return admtreeUrl;
    }

    public void setAdmtreeUrl(String admtreeUrl) {
        this.admtreeUrl = admtreeUrl;
    }

    public int getTopadmId() {
        return topadmId;
    }

    public void setTopadmId(int topadmId) {
        this.topadmId = topadmId;
    }
}
