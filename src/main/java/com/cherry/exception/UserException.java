package com.cherry.exception;

import com.cherry.enums.UserEnum;

/**
 * 用户异常类
 * Created by Administrator on 2017/11/07.
 */
public class UserException extends RuntimeException{

    private Integer code;

    public UserException(UserEnum userEnum){
        super(userEnum.getMessage());
        this.code = userEnum.getCode();
    }

    public UserException(Integer code, String message){
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
