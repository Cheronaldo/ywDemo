package com.cherry.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 用户与设备关系表
 * Created by Administrator on 2017/11/10.
 */
@Entity
@Data
@DynamicUpdate
public class UserDeviceRelationship {

    @Id
    private String id;
    /**  设备SN码 */
    private String snCode;
    /**  用户名 */
    private String userName;
    /**  用户注册该设备的时间 */
    private Date registerTime;
    /**  用户是否启用该设备 0表示未启用 */
    private Integer isUsed;

    public UserDeviceRelationship(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    @Override
    public String toString() {
        return "UserDeviceRelationship{" +
                "id='" + id + '\'' +
                ", snCode='" + snCode + '\'' +
                ", userName='" + userName + '\'' +
                ", registerTime=" + registerTime +
                ", isUsed=" + isUsed +
                '}';
    }
}
