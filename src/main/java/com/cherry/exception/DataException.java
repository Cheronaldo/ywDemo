package com.cherry.exception;

import com.cherry.enums.DataHandleEnum;

/**
 * 数据处理异常类
 * Created by Administrator on 2017/12/03.
 */
public class DataException extends RuntimeException{

    private Integer code;

    public DataException(DataHandleEnum dataHandleEnum){
        super(dataHandleEnum.getMessage());
        this.code = dataHandleEnum.getCode();
    }

    public DataException(Integer code, String message){
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
