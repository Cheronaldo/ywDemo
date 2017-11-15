package com.cherry.enums;

/**
 * 协议操作枚举
 * Created by Administrator on 2017/11/15.
 */
public enum ProtocolEnum {
    GET_PROTOCOL_FAIL(1,"查询协议失败！"),
    GET_PROTOCOL_SUCCESS(2,"查询协议成功！"),
    PROTOCOL_INFO_ERROR(3,"协议信息错误!"),
    UPDATE_PROTOCOL_FAIL(4,"协议修改失败！"),
    UPDATE_PROTOCOL_SUCCESS(5,"协议修改成功！"),
    GET_PROTOCOL_VERSION_FAIL(6,"获取协议版本号失败！"),
    GET_PROTOCOL_VERSION_SUCCESS(7,"获取协议版本号成功！"),
    ;


    private Integer code;

    private String message;

    ProtocolEnum(Integer code, String message){
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
