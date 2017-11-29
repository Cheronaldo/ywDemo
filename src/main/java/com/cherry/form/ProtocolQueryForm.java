package com.cherry.form;

/**协议祥表查询表单验证
 * Created by Administrator on 2017/11/23.
 */
public class ProtocolQueryForm {

    /**  设备SN码 */
    private String snCode;
    /** 是否启用协议适配 1 表示启用  */
    private Integer isAdapt;
    /**  协议版本号 */
    private String protocolVersion;
    /**  协议具体内容 */
    private String protocolContent;


    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public Integer getIsAdapt() {
        return isAdapt;
    }

    public void setIsAdapt(Integer isAdapt) {
        this.isAdapt = isAdapt;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getProtocolContent() {
        return protocolContent;
    }

    public void setProtocolContent(String protocolContent) {
        this.protocolContent = protocolContent;
    }

    @Override
    public String toString() {
        return "ProtocolQueryForm{" +
                "snCode='" + snCode + '\'' +
                ", isAdapt=" + isAdapt +
                ", protocolVersion='" + protocolVersion + '\'' +
                ", protocolContent='" + protocolContent + '\'' +
                '}';
    }
}
