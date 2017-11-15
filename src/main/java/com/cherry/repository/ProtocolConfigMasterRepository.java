package com.cherry.repository;

import com.cherry.dataobject.ProtocolConfigMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 设备协议配置主表DAO层
 * Created by Administrator on 2017/11/15.
 */
public interface ProtocolConfigMasterRepository extends JpaRepository<ProtocolConfigMaster,String>{

    /**
     * 通过SN码查询目前启用的协议版本号
     * @param snCode
     * @param isUsed
     * @return
     */
    ProtocolConfigMaster findBySnCodeAndIsUsed(String snCode, Integer isUsed);


    /**
     * 通过SN码和协议版本号查询 协议版本记录
     * @param snCode
     * @param protocolVersion
     * @return
     */
    ProtocolConfigMaster findBySnCodeAndProtocolVersion(String snCode, String protocolVersion);
}
