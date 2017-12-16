package com.cherry.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 报警规则表
 * Created by Administrator on 2017/12/16.
 */
@Entity
public class AlarmRule {

    @Id
    private String id;
    /**  设备SN码 */
    private String snCode;
    /**  协议版本号 */
    private String protocolVersion;
    /**  数据偏移 */
    private Integer offsetNumber;
    /**  报警下阈值 */
    private String downThreshold;
    /**  报警上阈值 */
    private String upThreshold;
    /**  报警码 */
    private Integer alarmCode;

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

    @Override
    public String toString() {
        return "AlarmRule{" +
                "id='" + id + '\'' +
                ", snCode='" + snCode + '\'' +
                ", protocolVersion='" + protocolVersion + '\'' +
                ", offsetNumber=" + offsetNumber +
                ", downThreshold='" + downThreshold + '\'' +
                ", upThreshold='" + upThreshold + '\'' +
                ", alarmCode=" + alarmCode +
                '}';
    }
}
