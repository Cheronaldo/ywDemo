package com.cherry.repository;

import com.cherry.dataobject.DeviceVerify;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 设备操作校验表DAO层
 * Created by Administrator on 2017/11/10.
 */
public interface DeviceVerifyRepository extends JpaRepository<DeviceVerify,String>{

    /**
     * 通过SN码查询校验码记录
     * @param snCode
     * @return
     */
    DeviceVerify findBySnCode(String snCode);
}
