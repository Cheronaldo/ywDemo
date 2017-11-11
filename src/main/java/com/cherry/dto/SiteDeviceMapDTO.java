package com.cherry.dto;

/**
 * 现场用户查询设备列表（地图）DTO
 * Created by Administrator on 2017/11/10.
 */
public class SiteDeviceMapDTO {

    /**  设备SN码 */
    private String snCode;
    /**  设备类型码 */
    private String deviceType;
    /**  设备型号 */
    private String deviceModel;
    /**  设备状态码 0 表示离线 1 表示在线 */
    private Integer isOnline;
    /** 设备部署地址  */
    private String deviceAddress;
    /**  设备经度 */
    private String deviceLongitude;
    /**  设备纬度 */
    private String deviceLatitude;
    /** 现场图标  */
    private String siteIcon;

    public SiteDeviceMapDTO(){}

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
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

    public String getSiteIcon() {
        return siteIcon;
    }

    public void setSiteIcon(String siteIcon) {
        this.siteIcon = siteIcon;
    }
}
