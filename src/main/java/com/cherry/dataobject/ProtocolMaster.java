package com.cherry.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;

/** 设备协议主表
 * Created by Administrator on 2017/11/29.
 */
@Entity
public class ProtocolMaster {

    /**  协议版本号 */
    @Id
    private String protocolVersion;
    /**  协议对应的经销商公司 */
    private String agencyCompany;
    /**  备用字段1 */
    private String spareField1;
    /**  备用字段2 */
    private String spareField2;

    public ProtocolMaster(){}

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getAgencyCompany() {
        return agencyCompany;
    }

    public void setAgencyCompany(String agencyCompany) {
        this.agencyCompany = agencyCompany;
    }

    public String getSpareField1() {
        return spareField1;
    }

    public void setSpareField1(String spareField1) {
        this.spareField1 = spareField1;
    }

    public String getSpareField2() {
        return spareField2;
    }

    public void setSpareField2(String spareField2) {
        this.spareField2 = spareField2;
    }

    @Override
    public String toString() {
        return "ProtocolMaster{" +
                "protocolVersion='" + protocolVersion + '\'' +
                ", agencyCompany='" + agencyCompany + '\'' +
                ", spareField1='" + spareField1 + '\'' +
                ", spareField2='" + spareField2 + '\'' +
                '}';
    }
}
