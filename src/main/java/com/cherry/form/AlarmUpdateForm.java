package com.cherry.form;

/**
 * 报警记录状态修改表单验证
 * Created by Administrator on 2017/12/07.
 */
public class AlarmUpdateForm {

    private String id;
    /**  报警处理状态 */
    private String handleStatus;
    /**  处理结果 */
    private String handleResult;
    /**  设备SN码 */
    private String snCode;
    /**  协议版本号 */
    private String protocolVersion;
    /**  处理状态 用于判断是否只查询未解除报警  true表示 已勾选*/
    private Boolean AlarmHandled;
    /**  起始时间 */
    private String oldDate;
    /**  终止时间 */
    private String newDate;

    public AlarmUpdateForm(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public Boolean getAlarmHandled() {
        return AlarmHandled;
    }

    public void setAlarmHandled(Boolean alarmHandled) {
        AlarmHandled = alarmHandled;
    }

    public String getOldDate() {
        return oldDate;
    }

    public void setOldDate(String oldDate) {
        this.oldDate = oldDate;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }

    @Override
    public String toString() {
        return "AlarmUpdateForm{" +
                "id='" + id + '\'' +
                ", handleStatus='" + handleStatus + '\'' +
                ", handleResult='" + handleResult + '\'' +
                ", snCode='" + snCode + '\'' +
                ", protocolVersion='" + protocolVersion + '\'' +
                ", AlarmHandled=" + AlarmHandled +
                ", oldDate='" + oldDate + '\'' +
                ", newDate='" + newDate + '\'' +
                '}';
    }
}
