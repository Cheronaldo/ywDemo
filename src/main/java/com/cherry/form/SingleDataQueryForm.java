package com.cherry.form;

import java.util.Date;

/**
 * 单项历史数据请求 表单验证
 * Created by Administrator on 2017/12/01.
 */
public class SingleDataQueryForm {

    /**  设备SN码 */
    private String snCode;
    /**  协议版本号 */
    private String protocolVersion;
    /**  数据编号 */
    private String offsetNumber;
    /**  起始时间 */
    private String oldDate;
    /**  终止时间 */
    private String newDate;

    public SingleDataQueryForm(){}

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

    public String getOffsetNumber() {
        return offsetNumber;
    }

    public void setOffsetNumber(String offsetNumber) {
        this.offsetNumber = offsetNumber;
    }

    public String getOldDate() {
        return oldDate;
    }

    public void setOldDate(String oldDate) {
        this.oldDate = oldDate;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }

    @Override
    public String toString() {
        return "SingleDataQueryForm{" +
                "snCode='" + snCode + '\'' +
                ", protocolVersion='" + protocolVersion + '\'' +
                ", offsetNumber='" + offsetNumber + '\'' +
                ", oldDate='" + oldDate + '\'' +
                ", newDate='" + newDate + '\'' +
                '}';
    }
}
