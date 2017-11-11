package com.cherry.dto;

/**
 * 现场用户查询设备列表（主页面）DTO
 * Created by Administrator on 2017/11/10.
 */
public class SiteDeviceListDTO {


    /**  设备SN码 */
    private String snCode;
    /** 设备部署地址  */
    private String deviceAddress;
    /**  设备状态码 0 表示离线 1 表示在线 */
    private Integer isOnline;
    /**  现场名称 */
    private String siteName;
    /**  设备类型(注意将类型码转换为 字符串传给前端)  另外这个字段是否需要待定*/
    private String deviceType;
    /**  设备型号 */
    private String deviceModel;
    /** 现场图标  */
    private String siteIcon;


    public SiteDeviceListDTO(){}

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

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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

    public String getSiteIcon() {
        return siteIcon;
    }

    public void setSiteIcon(String siteIcon) {
        this.siteIcon = siteIcon;
    }
}
