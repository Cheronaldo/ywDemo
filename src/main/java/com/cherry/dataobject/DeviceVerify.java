package com.cherry.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 设备操作校验表
 * Created by Administrator on 2017/11/10.
 */
@Entity
@Data
@DynamicUpdate
public class DeviceVerify {

    @Id
    private String id;
    /**  设备SN码 */
    private String snCode;
    /**  设备校验码 */
    private String checkCode;
    /**  校验码生成时间 */
    private Date generateTime;

    public DeviceVerify(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public Date getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(Date generateTime) {
        this.generateTime = generateTime;
    }
}
