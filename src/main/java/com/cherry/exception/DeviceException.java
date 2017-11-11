package com.cherry.exception;

import com.cherry.enums.DeviceHandleEnum;

/**
 * 设备异常类
 * Created by Administrator on 2017/11/11.
 */
public class DeviceException extends RuntimeException{

    private Integer code;

    public DeviceException(DeviceHandleEnum deviceHandleEnum){
        super(deviceHandleEnum.getMessage());
        this.code = deviceHandleEnum.getCode();
    }

    public DeviceException(Integer code, String message){
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
