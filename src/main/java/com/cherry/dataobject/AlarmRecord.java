package com.cherry.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 设备报警记录表
 * Created by Administrator on 2017/12/06.
 */
@Entity
public class AlarmRecord {

    @Id
    private String id;
    /**  设备SN码 */
    private String snCode;
    /**  协议版本号 */
    private String protocolVersion;
    /**  数据编号 */
    private Integer offsetNumber;
    /**  报警码 */
    private Integer alarmCode;
    /**  报警处理状态 */
    private Integer handleStatus;
    /**  报警时间 */
    private Date alarmTime;

    public AlarmRecord(){}

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

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public Integer getOffsetNumber() {
        return offsetNumber;
    }

    public void setOffsetNumber(Integer offsetNumber) {
        this.offsetNumber = offsetNumber;
    }

    public Integer getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(Integer alarmCode) {
        this.alarmCode = alarmCode;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    @Override
    public String toString() {
        return "AlarmRecord{" +
                "id='" + id + '\'' +
                ", snCode='" + snCode + '\'' +
                ", protocolVersion='" + protocolVersion + '\'' +
                ", offsetNumber=" + offsetNumber +
                ", alarmCode=" + alarmCode +
                ", handleStatus=" + handleStatus +
                ", alarmTime=" + alarmTime +
                '}';
    }
}
