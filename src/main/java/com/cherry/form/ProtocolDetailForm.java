package com.cherry.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 协议祥表记录 参数表单验证
 * Created by Administrator on 2017/11/15.
 */
public class ProtocolDetailForm {

    @NotEmpty(message = "ID必传")
    private String id;
    /**  设备SN码 */
    /** 这些参数暂时不删，以后可能会用到 */
//    @NotEmpty(message = "SN码必传")
//    private String snCode;
//    /**  协议版本号 */
//    @NotEmpty(message = "协议版本必传")
//    private String protocolVersion;
//    /**  数据编号 */
//    private Integer offsetNumber;
//    /**  数据名称 */
//    private String dataName;
    /**  数据是否实时显示 */
    private Integer isVisible;
    /**  是否进行报警监控 */
    private Integer isAlarmed;

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
//
//    public String getProtocolVersion() {
//        return protocolVersion;
//    }
//
//    public void setProtocolVersion(String protocolVersion) {
//        this.protocolVersion = protocolVersion;
//    }
//
//    public Integer getOffsetNumber() {
//        return offsetNumber;
//    }
//
//    public void setOffsetNumber(Integer offsetNumber) {
//        this.offsetNumber = offsetNumber;
//    }
//
//    public String getDataName() {
//        return dataName;
//    }
//
//    public void setDataName(String dataName) {
//        this.dataName = dataName;
//    }

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
}
