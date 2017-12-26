package com.cherry.vo;

/**
 * 实时数据显示协议 视图对象
 * Created by Administrator on 2017/11/24.
 */
public class RealTimeProtocolVO {

    /**  数据编号 */
    private Integer offsetNumber;
    /**  数据名称 */
    private String dataName;
    /**  数据单位 */
    private String dataUnit;

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

    public String getDataUnit() {
        return dataUnit;
    }

    public void setDataUnit(String dataUnit) {
        this.dataUnit = dataUnit;
    }

    @Override
    public String toString() {
        return "RealTimeProtocolVO{" +
                "offsetNumber=" + offsetNumber +
                ", dataName='" + dataName + '\'' +
                ", dataUnit='" + dataUnit + '\'' +
                '}';
    }
}
