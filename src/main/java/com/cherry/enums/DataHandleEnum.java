package com.cherry.enums;

/**
 * 数据处理枚举
 * Created by Administrator on 2017/12/03.
 */
public enum DataHandleEnum {

    GET_DATA_SUCCESS(1,"数据查询成功！"),
    GET_DATA_FAIL(2,"未查询到相关数据！"),
    QUERY_CRITERIA_ERROR(3,"查询条件异常"),
    DATA_EXPORT_FAIL(4,"数据导出失败！"),
    DATA_EXPORT_SUCCESS(5,"数据导出成功！")
    ;


    private Integer code;
    private String message;

    DataHandleEnum(Integer code, String message){
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
