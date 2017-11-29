package com.cherry.repository;

import com.cherry.dataobject.ProtocolMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 设备协议主表DAO层
 * Created by Administrator on 2017/11/29.
 */
public interface ProtocolMasterRepository extends JpaRepository<ProtocolMaster,String>{
}
