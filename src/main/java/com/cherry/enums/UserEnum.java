package com.cherry.enums;

/**
 * 用户类 操作枚举
 * Created by Administrator on 2017/11/08.
 */
public enum UserEnum {
    USER_ALREADY_EXIST(1,"用户已存在"),
    USER_VALID(2,"用户名符合要求"),
    USER_INFORMATION_ERROR(3,"用户信息错误"),
    USER_REGISTER_SUCCESS(4,"注册成功"),
    USER_LOGIN_FAIL(5,"登录失败"),
    USER_LOGIN_SUCCESS(6,"登录成功"),
    USER_GET_FAIL(7,"用户信息获取失败"),
    USER_GET_SUCCESS(8,"用户信息获取成功"),
    USER_UPDATE_SUCCESS(9,"修改成功"),
    USER_LOGOUT_SUCCESS(10,"注销成功"),
    SEND_CODE_SUCCESS(11,"验证码发送成功"),
    SEND_CODE_FAIL(12,"验证码发送失败,请重试"),
    REQUEST_TOO_FREQUENT(13,"请求过于频繁，稍后再试"),
    USER_REGISTER_FAIL(13,"注册失败"),
    USER_UPDATE_FAIL(14,"修改失败"),

    ;


    private Integer code;

    private String message;

    UserEnum(Integer code, String message){
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
