package com.cherry.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 现场设备注册 修改 参数表单验证
 * Created by Administrator on 2017/11/10.
 */
public class SiteDeviceForm {

    /**  用户名 */
    @NotEmpty(message = "用户名必填")
    private String userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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
}
