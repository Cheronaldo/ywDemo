package com.cherry.vo;

/**
 * 报警阈值（最大 最小） 视图对象
 * 添加阈值记录时 查询协议祥表 获取最大 最小阈值
 * Created by Administrator on 2017/12/18.
 */
public class AlarmThresholdVO {

    private String id;
    /**  数据偏移 */
    private Integer offsetNumber;
    /**  数据名称 */
    private String dataName;
    /**  报警下阈值 */
    private String minThreshold;
    /**  报警上阈值 */
    private String MaxThreshold;

    public AlarmThresholdVO(){}

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

    public String getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(String minThreshold) {
        this.minThreshold = minThreshold;
    }

    public String getMaxThreshold() {
        return MaxThreshold;
    }

    public void setMaxThreshold(String maxThreshold) {
        MaxThreshold = maxThreshold;
    }

    @Override
    public String toString() {
        return "AlarmThresholdVO{" +
                "id='" + id + '\'' +
                ", offsetNumber=" + offsetNumber +
                ", dataName='" + dataName + '\'' +
                ", minThreshold='" + minThreshold + '\'' +
                ", MaxThreshold='" + MaxThreshold + '\'' +
                '}';
    }
}
