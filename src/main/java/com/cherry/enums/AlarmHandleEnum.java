package com.cherry.enums;

/**
 * 设备报警处理枚举类
 * Created by Administrator on 2017/12/07.
 */
public enum AlarmHandleEnum {

    GET_RECORD_SUCCESS(0,"查询成功！"),
    GET_RECORD_FAIL(1,"未查询到相关记录！"),
    ALARM_UNHANDLED(2,"未处理"),
    ALARM_HANDLED(3,"已处理"),
    QUERY_CRITERIA_ERROR(4,"查询参数错误！"),
    UPDATE_CRITERIA_ERROR(5,"修改测试错误！"),
    UPDATE_SUCCESS(6, "修改成功！")
    ;


    private Integer code;
    private String message;

    AlarmHandleEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
