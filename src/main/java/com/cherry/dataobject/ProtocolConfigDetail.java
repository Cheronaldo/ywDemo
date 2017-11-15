package com.cherry.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 设备协议配置祥表
 * Created by Administrator on 2017/11/15.
 */
@Entity
public class ProtocolConfigDetail {

    @Id
    private String id;
    /**  设备SN码 */
    private String snCode;
    /**  协议版本号 */
    private String protocolVersion;
    /**  数据编号 */
    private Integer offsetNumber;
    /**  数据名称 */
    private String dataName;
    /**  数据是否实时显示 */
    private Integer isVisible;
    /**  是否进行报警监控 */
    private Integer isAlarmed;
    /**  备用字段 */
    private String deleted;

    public ProtocolConfigDetail(){}

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

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public Integer getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Integer isVisible) {
        this.isVisible = isVisible;
    }

    public Integer getIsAlarmed() {
        return isAlarmed;
    }

    public void setIsAlarmed(Integer isAlarmed) {
        this.isAlarmed = isAlarmed;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "ProtocolConfigDetail{" +
                "id='" + id + '\'' +
                ", snCode='" + snCode + '\'' +
                ", protocolVersion='" + protocolVersion + '\'' +
                ", offsetNumber=" + offsetNumber +
                ", dataName='" + dataName + '\'' +
                ", isVisible=" + isVisible +
                ", isAlarmed=" + isAlarmed +
                ", deleted='" + deleted + '\'' +
                '}';
    }
}
