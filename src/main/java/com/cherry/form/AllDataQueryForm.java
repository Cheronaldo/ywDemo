package com.cherry.form;

import java.util.Date;

/**
 * 所有项历史数据请求 表单验证
 * Created by Administrator on 2017/12/01.
 */
public class AllDataQueryForm {

    /**  设备SN码 */
    private String snCode;
    /**  协议版本号 */
    private String protocolVersion;
    /**  起始时间 */
    // 页面传下来的是字符串时间——————————————————
    private String oldDate;
    /**  终止时间 */
    private String newDate;

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
        return "AllDataQueryForm{" +
                "snCode='" + snCode + '\'' +
                ", protocolVersion='" + protocolVersion + '\'' +
                ", oldDate='" + oldDate + '\'' +
                ", newDate='" + newDate + '\'' +
                '}';
    }
}
