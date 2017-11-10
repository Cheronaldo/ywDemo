package com.cherry.enums;

/**
 * Created by Administrator on 2017/11/10.
 */
public enum DeviceTypeEnum {
    HMI_DEVICE(1,"HMI"),
    ;

    private Integer code;

    private String message;

    DeviceTypeEnum(Integer code, String message){
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
