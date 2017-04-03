package com.madiot.enterprise.model.collect;

/**
 * Created by DELL on 2016/6/26.
 */
public class EnterpriseCollect {

    /**
     * 序号
     */
    private int index;

    /**
     * 经营范围：
     */
    private String busscope;

    /**
     * 地址：
     */
    private String dom;

    /**
     * 企业/个体名称：
     */
    private String entname;

    /**
     * 主体类型：
     */
    private String enttype_name;

    /**
     * 法人
     */
    private String lerep;

    /**
     * 邮政编码：
     */
    private String postalcode;

    /**
     * 所属辖区：
     */
    private String localadm_name;

    /**
     * 电话：
     */
    private String tel;

    /**
     * 经营年限起
     */
    private String opfrom;

    /**
     * 行业代码：
     */
    private String industryco_name;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getBusscope() {
        return busscope;
    }

    public void setBusscope(String busscope) {
        this.busscope = busscope;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public String getEntname() {
        return entname;
    }

    public void setEntname(String entname) {
        this.entname = entname;
    }

    public String getEnttype_name() {
        return enttype_name;
    }

    public void setEnttype_name(String enttype_name) {
        this.enttype_name = enttype_name;
    }

    public String getLerep() {
        return lerep;
    }

    public void setLerep(String lerep) {
        this.lerep = lerep;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getLocaladm_name() {
        return localadm_name;
    }

    public void setLocaladm_name(String localadm_name) {
        this.localadm_name = localadm_name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getOpfrom() {
        return opfrom;
    }

    public void setOpfrom(String opfrom) {
        this.opfrom = opfrom;
    }

    public String getIndustryco_name() {
        return industryco_name;
    }

    public void setIndustryco_name(String industryco_name) {
        this.industryco_name = industryco_name;
    }
}
