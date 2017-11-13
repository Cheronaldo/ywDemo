package com.cherry.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 设备基本信息表
 * Created by Administrator on 2017/11/10.
 */
@Entity
public class DeviceInfo {

    /**  设备SN码 */
    @Id
    private String snCode;
    /** 设备Mac地址  */
    private String deviceMac;
    /**  设备类型码 */
    private Integer deviceType;
    /**  设备型号 */
    private String deviceModel;
    /** 设备出货号  */
    private String shipmentNumber;
    /**  设备在研单位 */
    private String researchUnit;
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

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    public String getResearchUnit() {
        return researchUnit;
    }

    public void setResearchUnit(String researchUnit) {
        this.researchUnit = researchUnit;
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


    @Override
    public String toString() {
        return "DeviceInfo{" +
                "snCode='" + snCode + '\'' +
                ", deviceMac='" + deviceMac + '\'' +
                ", deviceType=" + deviceType +
                ", deviceModel='" + deviceModel + '\'' +
                ", shipmentNumber='" + shipmentNumber + '\'' +
                ", researchUnit='" + researchUnit + '\'' +
                ", deviceAddress='" + deviceAddress + '\'' +
                ", deviceLongitude='" + deviceLongitude + '\'' +
                ", deviceLatitude='" + deviceLatitude + '\'' +
                ", siteName='" + siteName + '\'' +
                ", siteType='" + siteType + '\'' +
                ", siteIcon='" + siteIcon + '\'' +
                '}';
    }
}
