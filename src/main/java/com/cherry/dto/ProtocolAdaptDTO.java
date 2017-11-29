package com.cherry.dto;

/**协议适配DTO
 * Created by Administrator on 2017/11/23.
 */
public class ProtocolAdaptDTO {


    /**  设备SN码 */
    private String snCode;
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
        return "ProtocolAdaptDTO{" +
                "snCode='" + snCode + '\'' +
                ", protocolVersion='" + protocolVersion + '\'' +
                ", protocolContent='" + protocolContent + '\'' +
                '}';
    }
}
