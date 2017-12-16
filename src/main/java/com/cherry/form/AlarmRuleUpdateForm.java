package com.cherry.form;

/**
 * 阈值修改 表单验证
 * Created by Administrator on 2017/12/16.
 */
public class AlarmRuleUpdateForm {

    private String id;
    /**  报警下阈值 */
    private String downThreshold;
    /**  报警上阈值 */
    private String upThreshold;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "AlarmRuleUpdateForm{" +
                "id='" + id + '\'' +
                ", downThreshold='" + downThreshold + '\'' +
                ", upThreshold='" + upThreshold + '\'' +
                '}';
    }
}
