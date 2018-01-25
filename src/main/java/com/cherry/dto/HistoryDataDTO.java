package com.cherry.dto;

import java.util.Date;

/**
 * 历史数据 DTO 对象
 * Created by Administrator on 2018/01/24.
 */
public class HistoryDataDTO {

    /**  协议版本号 */
    private String protocolVersion;
    /**  设备数据 */
    private String deviceData;
    /**  数据储存时间 */
    private Date dataTime;

    public HistoryDataDTO(){}

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getDeviceData() {
        return deviceData;
    }

    public void setDeviceData(String deviceData) {
        this.deviceData = deviceData;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    @Override
    public String toString() {
        return "HistoryDataDTO{" +
                "protocolVersion='" + protocolVersion + '\'' +
                ", deviceData='" + deviceData + '\'' +
                ", dataTime=" + dataTime +
                '}';
    }
}
