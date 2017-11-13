package com.cherry.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 设备状态表
 * Created by Administrator on 2017/11/10.
 */
@Entity
@Data
@DynamicUpdate
public class DeviceStatus {

    /**  设备SN码 */
    @Id
    private String snCode;
    /**  设备状态码 0 表示离线 1 表示在线 */
    private Integer isOnline;
    /**  设备最近一次心跳时间 */
    private Date heartTime;

    public DeviceStatus(){}

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public Date getHeartTime() {
        return heartTime;
    }

    public void setHeartTime(Date heartTime) {
        this.heartTime = heartTime;
    }

    @Override
    public String toString() {
        return "DeviceStatus{" +
                "snCode='" + snCode + '\'' +
                ", isOnline=" + isOnline +
                ", heartTime=" + heartTime +
                '}';
    }
}
