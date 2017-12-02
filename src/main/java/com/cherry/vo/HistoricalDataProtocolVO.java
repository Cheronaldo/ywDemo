package com.cherry.vo;

/**
 * 历史数据协议详情 视图对象
 * Created by Administrator on 2017/12/02.
 */
public class HistoricalDataProtocolVO {


    /**  数据编号 */
    private Integer offsetNumber;
    /**  数据名称 */
    private String dataName;

    public HistoricalDataProtocolVO(){}

    public Integer getOffsetNumber() {
        return offsetNumber;
    }

    public void setOffsetNumber(Integer offsetNumber) {
        this.offsetNumber = offsetNumber;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    @Override
    public String toString() {
        return "HistoricalDataProtocolVO{" +
                "offsetNumber=" + offsetNumber +
                ", dataName='" + dataName + '\'' +
                '}';
    }
}
