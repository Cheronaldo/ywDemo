package com.cherry.dto;

/**
 * 协议策略DTO
 * Created by Administrator on 2017/12/01.
 */
public class ProtocolStrategyDTO {

    /**  数据编号 */
    private Integer offsetNumber;
    /**  数据是否实时显示 */
    private Integer isVisible;
    /**  是否进行报警监控 */
    private Integer isAlarmed;

    public ProtocolStrategyDTO(){}

    public Integer getOffsetNumber() {
        return offsetNumber;
    }

    public void setOffsetNumber(Integer offsetNumber) {
        this.offsetNumber = offsetNumber;
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
        return "ProtocolStrategyDTO{" +
                "offsetNumber=" + offsetNumber +
                ", isVisible=" + isVisible +
                ", isAlarmed=" + isAlarmed +
                '}';
    }
}
