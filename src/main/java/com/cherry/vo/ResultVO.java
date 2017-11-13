package com.cherry.vo;

/**
 * 返回给前端 VO
 * Created by Administrator on 2017/11/07.
 */
public class ResultVO<T> {

    /** 返回操作码 */
    private Integer code;
    /** 提示信息 */
    private String msg;
    /** 具体数据内容 */
    private T data;

    public ResultVO() {}

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultVO{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
