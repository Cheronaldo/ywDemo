package com.cherry.enums;

/**
 * 返回结果类型枚举
 * Created by Administrator on 2017/11/07.
 */
public enum ResultEnum {
    SUCCESS(0,"成功"),
    USER_ALREADY_EXIST(1,"用户已存在"),
    USER_INFORMATION_ERROR(2,"用户信息错误"),
    ;


    private Integer code;

    private String message;

    ResultEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
