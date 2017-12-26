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
    /**  数据偏移 */
    private Integer offsetNumber;
    /**  实际值 */
    private String actualValue;
    /**  报警下阈值 */
    private String downThreshold;
    /**  报警上阈值 */
    private String upThreshold;
    /**  报警码 */
    private Integer alarmCode;
    /**  报警时间 */
    private Date alarmTime;
    /**  报警处理状态 */
    private Integer handleStatus;
    /**  报警处理结果 */
    private String handleResult;
    /**  报警处理时间 */
    private String handleTime;
    /**  报警记录是否查看 1 表示查看 0 表示未查看 */
    private Integer isChecked;

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

    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
    }

    public String getDownThreshold() {
        return downThreshold;
    }

    public void setDownThreshold(String downThreshold) {
        this.downThreshold = downThreshold;
    }

    public String getUpThreshold() {
        return upThreshold;
    }

    public void setUpThreshold(String upThreshold) {
        this.upThreshold = upThreshold;
    }

    public Integer getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(Integer alarmCode) {
        this.alarmCode = alarmCode;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public Integer getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public String toString() {
        return "AlarmRecord{" +
                "id='" + id + '\'' +
                ", snCode='" + snCode + '\'' +
                ", protocolVersion='" + protocolVersion + '\'' +
                ", offsetNumber=" + offsetNumber +
                ", actualValue='" + actualValue + '\'' +
                ", downThreshold='" + downThreshold + '\'' +
                ", upThreshold='" + upThreshold + '\'' +
                ", alarmCode=" + alarmCode +
                ", alarmTime=" + alarmTime +
                ", handleStatus=" + handleStatus +
                ", handleResult='" + handleResult + '\'' +
                ", handleTime='" + handleTime + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
