package com.cherry.repository;

import com.cherry.dataobject.DeviceVerify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * 设备操作校验表DAO层
 * Created by Administrator on 2017/11/10.
 */
public interface DeviceVerifyRepository extends JpaRepository<DeviceVerify,String>{

    /**
     * 通过SN码查询设备最新一条校验码记录
     * @param snCode
     * @return
     */
    @Transactional
    @Query(value = "select * from device_verify where device_verify.sn_code = ?1 order by device_verify.id desc limit 1",nativeQuery = true)
    DeviceVerify findLatestOneBySnCode(String snCode);

    // TODO 不需写原生sql(已完成)
    /**
     * 通过SN码查询设备最新一条校验码记录(不需写原生sql)
     * @param snCode
     * @return
     */
    DeviceVerify findFirstBySnCodeOrderByIdDesc(String snCode);
}
