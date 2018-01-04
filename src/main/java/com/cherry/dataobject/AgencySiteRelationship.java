package com.cherry.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 经销商与现场 从属关系表
 * Created by Administrator on 2018/01/04.
 */
@Entity
public class AgencySiteRelationship {


    @Id
    private String id;

    /**  经销商用户名 */
    private String agencyUserName;
    /**  现场用户名 */
    private String siteUserName;
    /**  经销商与现场从属关系启用标志 */
    private Integer isUsed;
    /**  现场用户注册时间 */
    private Date registerTime;

    public AgencySiteRelationship(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgencyUserName() {
        return agencyUserName;
    }

    public void setAgencyUserName(String agencyUserName) {
        this.agencyUserName = agencyUserName;
    }

    public String getSiteUserName() {
        return siteUserName;
    }

    public void setSiteUserName(String siteUserName) {
        this.siteUserName = siteUserName;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    @Override
    public String toString() {
        return "AgencySiteRelationship{" +
                "id='" + id + '\'' +
                ", agencyUserName='" + agencyUserName + '\'' +
                ", siteUserName='" + siteUserName + '\'' +
                ", isUsed=" + isUsed +
                ", registerTime=" + registerTime +
                '}';
    }
}
