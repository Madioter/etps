package com.madiot.enterprise.model;

import com.madiot.enterprise.common.excel.ExcelModel;
import com.madiot.enterprise.common.util.StringUtil;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
public class EnterpriseVo extends ExcelModel {

    public void setValues(Map<Integer, String> values) {
        this.setName(values.get(0));
        this.setIndustry(values.get(1));
        this.setLegalPerson(values.get(2));
        this.setRegAddress(values.get(3));
        this.setBusinessScope(values.get(4));
        this.setRegAuth(values.get(5));
        this.setRegDate(values.get(6));
        this.setPhone(values.get(7));
    }

    @Override
    public String[] getTitles() {
        return EXCEL_TITLE;
    }

    //Excel表头
    public final static String[] EXCEL_TITLE = new String[]{"企业名称", "行业", "法人代表人", "注册地址", "经营范围", "注册机关", "注册日期", "联系方式"};


    /**
     * 主键
     */
    private Integer id;

    /**
     * 企业名称
     */
    @NotBlank(message = "企业名称不能为空")
    private String name;

    /**
     * 行业
     */
    private String industry = "未设置";

    /**
     * 法人代表
     */
    @NotBlank(message = "法人不能为空")
    private String legalPerson;

    /**
     * 注册地址
     */
    private String regAddress = "未设置";

    /**
     * 经营范围
     */
    private String businessScope = "未设置";

    /**
     * 注册机关
     */
    private String regAuth = "未设置";

    /**
     * 注册日期
     */
    private String regDate;

    /**
     * 联系方式
     */
    @NotBlank(message = "联系方式不能为空")
    private String phone;

    /* 查询和保存辅助属性 */
    private Integer regAuthCode;

    private Integer industryCode;

    private Integer updateUserId;

    private String updateUserName;

    private Date updateTime;

    private Date addTime;

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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        if (!StringUtil.isEmpty(industry)) {
            this.industry = industry;
        }
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        if (!StringUtil.isEmpty(legalPerson)) {
            this.legalPerson = legalPerson;
        }
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        if (!StringUtil.isEmpty(regAddress)) {
            this.regAddress = regAddress;
        }
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getRegAuth() {
        return regAuth;
    }

    public void setRegAuth(String regAuth) {
        if (!StringUtil.isEmpty(regAuth)) {
            this.regAuth = regAuth;
        }
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRegAuthCode() {
        return regAuthCode;
    }

    public void setRegAuthCode(Integer regAuthCode) {
        this.regAuthCode = regAuthCode;
    }

    public Integer getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(Integer industryCode) {
        this.industryCode = industryCode;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean sameWith(EnterpriseVo model) {
        if (this.getBusinessScope().equals(model.getBusinessScope()) &&
                this.getIndustry().equals(model.getIndustry()) &&
                this.getLegalPerson().equals(model.getLegalPerson()) &&
                this.getName().equals(model.getName()) &&
                this.getRegDate().equals(model.getRegDate()) &&
                this.getPhone().equals(model.getPhone()) &&
                this.getRegAddress().equals(model.getRegAddress()) &&
                this.getRegAuth().equals(model.getRegAuth())) {
            return true;
        }
        return false;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
