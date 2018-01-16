package com.cherry.vo;

/**
 * 数据读写协议 视图对象
 * Created by Administrator on 2018/01/16.
 */
public class DataReadWriteProtocolVO {

    /**  数据编号 */
    private Integer offsetNumber;
    /**  数据名称 */
    private String dataName;
    /**  数据单位 */
    private String dataUnit;
    /**  数据格式 */
    private String dataType;
    /**  数据是否可读 */
    private String isRead;
    /**  数据是否可写 */
    private String isWrite;

    public DataReadWriteProtocolVO(){}

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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getIsWrite() {
        return isWrite;
    }

    public void setIsWrite(String isWrite) {
        this.isWrite = isWrite;
    }

    @Override
    public String toString() {
        return "DataReadWriteProtocolVO{" +
                "offsetNumber=" + offsetNumber +
                ", dataName='" + dataName + '\'' +
                ", dataUnit='" + dataUnit + '\'' +
                ", dataType='" + dataType + '\'' +
                ", isRead='" + isRead + '\'' +
                ", isWrite='" + isWrite + '\'' +
                '}';
    }
}
