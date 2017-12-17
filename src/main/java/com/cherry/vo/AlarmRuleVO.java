package com.cherry.vo;

/**
 * 阈值规则 视图对象
 * Created by Administrator on 2017/12/17.
 */
public class AlarmRuleVO {

    private String id;
    /**  数据偏移 */
    private Integer offsetNumber;
    /**  数据名称 */
    private String dataName;
    /**  报警下阈值 */
    private String downThreshold;
    /**  报警上阈值 */
    private String upThreshold;

    public AlarmRuleVO(){}

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

    public String getDownThreshold() {
        return downThreshold;
    }

    public void setDownThreshold(String downThreshold) {
        this.downThreshold = downThreshold;
    }

    public String getUpThreshold() {
        return upThreshold;
    }

    public void setUpThreshold(String upThreshold) {
        this.upThreshold = upThreshold;
    }

    @Override
    public String toString() {
        return "AlarmRuleVO{" +
                "id='" + id + '\'' +
                ", offsetNumber=" + offsetNumber +
                ", dataName='" + dataName + '\'' +
                ", downThreshold='" + downThreshold + '\'' +
                ", upThreshold='" + upThreshold + '\'' +
                '}';
    }
}
