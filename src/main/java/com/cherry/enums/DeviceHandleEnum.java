package com.cherry.enums;

/**
 * 设备操作结果枚举
 * Created by Administrator on 2017/11/10.
 */
public enum DeviceHandleEnum {
    SN_OR_CHECK_CODE_ERROR(1,"SN码或校验码错误！"),
    CHECK_CODE_INVALID(2,"校验码失效！"),
    FIRST_REGISTER(3,"首次注册"),
    ADD_REGISTER(4,"追加注册"),
    ALREADY_REGISTERED(5,"用户已注册该设备"),
    SAVE_SUCCESS(6,"信息保存成功"),
    REGISTER_ENABLE(7,"经销商未对此设备进行注册"),
    USER_DEVICE_RELATIONSHIP_HANDLE_SUCCESS(8,"用户与设备关系操作成功"),
    DEVICE_UNBIND_SUCCESS(9,"设备注销成功"),
    FIND_DEVICE_LIST_SUCCESS(10,"查询设备列表成功"),
    FIND_DEVICE_LIST_FAIL(11,"未查询到设备信息"),
    ;


    private Integer code;

    private String message;

    DeviceHandleEnum(Integer code, String message){
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
