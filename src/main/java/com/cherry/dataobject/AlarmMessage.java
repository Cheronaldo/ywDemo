package com.cherry.dataobject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 报警信息代码表
 * Created by Administrator on 2017/12/06.
 */
@Entity
public class AlarmMessage {

    @Id
    @GeneratedValue
    private Integer id;
    /** 报警码 */
    private Integer alarmCode;
    /** 报警码具体内容 */
    private String alarmInfo;

    public AlarmMessage(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(Integer alarmCode) {
        this.alarmCode = alarmCode;
    }

    public String getAlarmInfo() {
        return alarmInfo;
    }

    public void setAlarmInfo(String alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    @Override
    public String toString() {
        return "AlarmMessage{" +
                "id=" + id +
                ", alarmCode=" + alarmCode +
                ", alarmInfo='" + alarmInfo + '\'' +
                '}';
    }
}
