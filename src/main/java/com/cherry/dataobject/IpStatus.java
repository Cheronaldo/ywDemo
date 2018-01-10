package com.cherry.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * IP状态表
 * Created by Administrator on 2018/01/09.
 */
@Entity
public class IpStatus {

    @Id
    private String id;
    /**  用户名 */
    private String userName;
    /**  用户登录IP地址 */
    private String userIp;
    /**  用户登录session id */
    private String sessionId;
    /**  用户登录时间 */
    private Date loginTime;
    /**  用户IP启用标志 */
    private Integer isUsed;

    public IpStatus(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    @Override
    public String toString() {
        return "IpStatus{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", userIp='" + userIp + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", loginTime=" + loginTime +
                ", isUsed=" + isUsed +
                '}';
    }
}
