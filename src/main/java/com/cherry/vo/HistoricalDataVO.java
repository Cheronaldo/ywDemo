package com.cherry.vo;

import java.util.Date;

/**
 * 单项数据历史记录 视图对象
 * Created by Administrator on 2017/12/01.
 */
public class HistoricalDataVO {

    /** 数据值 */
    private String deviceData;
    // 页面能否解析Date类型数据？ 建议在页面转换为 String ，因为页面必定会循环，后台不循环处理以节约时间
    /** 数据储存时间 */
    private String dataTime;

    public HistoricalDataVO(){}

    public String getDeviceData() {
        return deviceData;
    }

    public void setDeviceData(String deviceData) {
        this.deviceData = deviceData;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    @Override
    public String toString() {
        return "HistoricalDataVO{" +
                "deviceData='" + deviceData + '\'' +
                ", dataTime='" + dataTime + '\'' +
                '}';
    }
}
