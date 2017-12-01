package com.cherry.repository;

import com.cherry.dataobject.DeviceProtocolRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 设备协议关系表DAO层
 * Created by Administrator on 2017/11/29.
 */
public interface DeviceProtocolRelationshipRepository extends JpaRepository<DeviceProtocolRelationship, String>{

    /**
     * 通过SN码查询 设备当前启用的协议版本号
     * @param snCode
     * @param isUsed
     * @return
     */
    DeviceProtocolRelationship findBySnCodeAndIsUsed(String snCode,Integer isUsed);


    /**
     * 通过SN码和协议版本号查询 设备协议版本记录
     * @param snCode
     * @param protocolVersion
     * @return
     */
    DeviceProtocolRelationship findBySnCodeAndProtocolVersion(String snCode, String protocolVersion);

}
