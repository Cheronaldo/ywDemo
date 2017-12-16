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
    /**  报警处理时间 */
    private String handleTime;
    /**  报警数据名称 */
    private String dataName;
    /**  实际值 */
    private String actualValue;
    /**  报警详情 */
    private String alarmInfo;
    /**  处理状态 */
    private String handleStatus;
    /**  处理结果 */
    private String handleResult;

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

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
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

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    @Override
    public String toString() {
        return "AlarmRecordVO{" +
                "id='" + id + '\'' +
                ", alarmTime='" + alarmTime + '\'' +
                ", handleTime='" + handleTime + '\'' +
                ", dataName='" + dataName + '\'' +
                ", actualValue='" + actualValue + '\'' +
                ", alarmInfo='" + alarmInfo + '\'' +
                ", handleStatus='" + handleStatus + '\'' +
                ", handleResult='" + handleResult + '\'' +
                '}';
    }
}
