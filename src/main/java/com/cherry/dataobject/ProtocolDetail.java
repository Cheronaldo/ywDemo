package com.cherry.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 设备协议祥表
 * Created by Administrator on 2017/11/29.
 */
@Entity
public class ProtocolDetail {

    @Id
    private String id;
//    /**  设备SN码 */
//    private String snCode;
    /**  协议版本号 */
    private String protocolVersion;
    /**  数据编号 */
    private Integer offsetNumber;
    /**  数据名称 */
    private String dataName;
    /**  数据单位 */
    private String dataUnit;
    /**  数据阈值上限 */
    private String maxThreshold;
    /**  数据阈值下限 */
    private String minThreshold;
    /**  备用字段 */
    private String deleted;

    public ProtocolDetail(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public String getSnCode() {
//        return snCode;
//    }
//
//    public void setSnCode(String snCode) {
//        this.snCode = snCode;
//    }

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

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataUnit() {
        return dataUnit;
    }

    public void setDataUnit(String dataUnit) {
        this.dataUnit = dataUnit;
    }

    public String getMaxThreshold() {
        return maxThreshold;
    }

    public void setMaxThreshold(String maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    public String getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(String minThreshold) {
        this.minThreshold = minThreshold;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "ProtocolDetail{" +
                "id='" + id + '\'' +
                ", protocolVersion='" + protocolVersion + '\'' +
                ", offsetNumber=" + offsetNumber +
                ", dataName='" + dataName + '\'' +
                ", dataUnit='" + dataUnit + '\'' +
                ", maxThreshold='" + maxThreshold + '\'' +
                ", minThreshold='" + minThreshold + '\'' +
                ", deleted='" + deleted + '\'' +
                '}';
    }
}
