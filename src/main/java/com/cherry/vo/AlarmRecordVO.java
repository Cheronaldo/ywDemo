package com.cherry.vo;

import java.util.Date;

/**
 * 设备报警记录 视图对象
 * Created by Administrator on 2017/12/07.
 */
public class AlarmRecordVO {

    private String id;
    /**  报警时间 */
    private String alarmTime;
    /**  协议版本号 */
    //private String protocolVersion;
    /**  报警数据名称 */
    private String dataName;
    /**  报警详情 */
    private String alarmInfo;
    /**  处理状态 */
    private String handleStatus;

    public AlarmRecordVO(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getAlarmInfo() {
        return alarmInfo;
    }

    public void setAlarmInfo(String alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    @Override
    public String toString() {
        return "AlarmRecordVO{" +
                "id='" + id + '\'' +
                ", alarmTime='" + alarmTime + '\'' +
                ", dataName='" + dataName + '\'' +
                ", alarmInfo='" + alarmInfo + '\'' +
                ", handleStatus='" + handleStatus + '\'' +
                '}';
    }
}
