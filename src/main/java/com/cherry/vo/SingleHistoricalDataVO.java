package com.cherry.vo;

import java.util.Date;

/**
 * 单项数据历史记录 视图对象
 * Created by Administrator on 2017/12/01.
 */
public class SingleHistoricalDataVO {

    /** 数据值 */
    private String data_value;
    // 页面能否解析Date类型数据？ 建议在页面转换为 String ，因为页面必定会循环，后台不循环处理以节约时间
    /** 数据储存时间 */
    private Date data_time;

    public SingleHistoricalDataVO(){}

    public String getData_value() {
        return data_value;
    }

    public void setData_value(String data_value) {
        this.data_value = data_value;
    }

    public Date getData_time() {
        return data_time;
    }

    public void setData_time(Date data_time) {
        this.data_time = data_time;
    }

    @Override
    public String toString() {
        return "SingleHistoricalDataVO{" +
                "data_value='" + data_value + '\'' +
                ", data_time=" + data_time +
                '}';
    }
}
