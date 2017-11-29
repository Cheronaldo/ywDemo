package com.cherry.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 用户报警策略表
 * Created by Administrator on 2017/11/29.
 */
@Entity
public class AlarmStrategy {

    @Id
    private String id;
    /**  用户名 */
    private String userName;
    /**  设备SN码 */
    private String snCode;
    /**  协议版本号 */
    private String protocolVersion;
    /**  数据报警掩码 */
    private String alarmMask;
    /**  是否启用该策略 */
    private Integer isUsed;
    /**  启用时间 */
    private Date usedTime;
    /**  备用字段 */
    private String spareField;

    public AlarmStrategy(){}

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

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getAlarmMask() {
        return alarmMask;
    }

    public void setAlarmMask(String alarmMask) {
        this.alarmMask = alarmMask;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public Date getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Date usedTime) {
        this.usedTime = usedTime;
    }

    public String getSpareField() {
        return spareField;
    }

    public void setSpareField(String spareField) {
        this.spareField = spareField;
    }

    @Override
    public String toString() {
        return "AlarmStrategy{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", snCode='" + snCode + '\'' +
                ", protocolVersion='" + protocolVersion + '\'' +
                ", alarmMask='" + alarmMask + '\'' +
                ", isUsed=" + isUsed +
                ", usedTime=" + usedTime +
                ", spareField='" + spareField + '\'' +
                '}';
    }
}
