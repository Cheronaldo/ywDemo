package com.cherry.vo;

/**
 * 用户名下所有设备报警记录 视图对象
 * Created by Administrator on 2017/12/26.
 */
public class AlarmRecordAllDeviceVO {

    private String id;
    /**  设备SN码 */
    private String snCode;
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
    /**  报警记录是否查看  1表示查看 0表示未查看 */
    private Integer isChecked;

    public AlarmRecordAllDeviceVO() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
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

    public Integer getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public String toString() {
        return "AlarmRecordAllDeviceVO{" +
                "id='" + id + '\'' +
                ", snCode='" + snCode + '\'' +
                ", alarmTime='" + alarmTime + '\'' +
                ", handleTime='" + handleTime + '\'' +
                ", dataName='" + dataName + '\'' +
                ", actualValue='" + actualValue + '\'' +
                ", alarmInfo='" + alarmInfo + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
