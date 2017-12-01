package com.cherry.vo;

/**
 * 协议配置显示协议 试图对象
 * Created by Administrator on 2017/12/01.
 */
public class ProtocolDetailVO {

    private String id;
    /**  数据编号 */
    private Integer offsetNumber;
    /**  数据名称 */
    private String dataName;
    /**  数据是否实时显示 */
    private Integer isVisible;
    /**  是否进行报警监控 */
    private Integer isAlarmed;

    public ProtocolDetailVO(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Integer getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Integer isVisible) {
        this.isVisible = isVisible;
    }

    public Integer getIsAlarmed() {
        return isAlarmed;
    }

    public void setIsAlarmed(Integer isAlarmed) {
        this.isAlarmed = isAlarmed;
    }

    @Override
    public String toString() {
        return "ProtocolDetailVO{" +
                "id='" + id + '\'' +
                ", offsetNumber=" + offsetNumber +
                ", dataName='" + dataName + '\'' +
                ", isVisible=" + isVisible +
                ", isAlarmed=" + isAlarmed +
                '}';
    }
}
