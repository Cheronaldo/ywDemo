package com.cherry.dto;

/**
 * 现场设备注册信息DTO
 * Created by Administrator on 2017/11/10.
 */
public class SiteDeviceInfoDTO {

    /**  设备SN码 */
    private String snCode;
    /** 设备部署地址  */
    private String deviceAddress;
    /**  设备经度 */
    private String deviceLongitude;
    /**  设备纬度 */
    private String deviceLatitude;
    /**  现场名称 */
    private String siteName;
    /** 现场类型  */
    private String siteType;
    /** 现场图标  */
    private String siteIcon;

    /**  协议版本号 */
    private String protocolVersion;

    public SiteDeviceInfoDTO(){}

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public String getDeviceLongitude() {
        return deviceLongitude;
    }

    public void setDeviceLongitude(String deviceLongitude) {
        this.deviceLongitude = deviceLongitude;
    }

    public String getDeviceLatitude() {
        return deviceLatitude;
    }

    public void setDeviceLatitude(String deviceLatitude) {
        this.deviceLatitude = deviceLatitude;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getSiteIcon() {
        return siteIcon;
    }

    public void setSiteIcon(String siteIcon) {
        this.siteIcon = siteIcon;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    @Override
    public String toString() {
        return "SiteDeviceInfoDTO{" +
                "snCode='" + snCode + '\'' +
                ", deviceAddress='" + deviceAddress + '\'' +
                ", deviceLongitude='" + deviceLongitude + '\'' +
                ", deviceLatitude='" + deviceLatitude + '\'' +
                ", siteName='" + siteName + '\'' +
                ", siteType='" + siteType + '\'' +
                ", siteIcon='" + siteIcon + '\'' +
                ", protocolVersion='" + protocolVersion + '\'' +
                '}';
    }
}
