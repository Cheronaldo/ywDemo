package com.cherry.exception;

import com.cherry.enums.ProtocolEnum;

/**
 * 设备协议异常类
 * Created by Administrator on 2017/11/15.
 */
public class ProtocolException extends RuntimeException{

    private Integer code;

    public ProtocolException(ProtocolEnum protocolEnum){
        super(protocolEnum.getMessage());
        this.code = protocolEnum.getCode();
    }

    public ProtocolException(Integer code, String message){
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
