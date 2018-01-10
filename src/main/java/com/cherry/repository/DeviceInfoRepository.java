package com.cherry.repository;

import com.cherry.dataobject.DeviceInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 设备基本信息表DAO层
 * Created by Administrator on 2017/11/10.
 */
public interface DeviceInfoRepository extends JpaRepository<DeviceInfo,String>{

    /**
     * 通过SN码列表 查询获取设备基本信息列表
     * @param snCodeList
     * @return
     */
    List<DeviceInfo> findBySnCodeIn(List<String> snCodeList);

    /**
     * 由SN码列表获取设备列表
     * @param snCodeList
     * @param pageable
     * @return
     */
    Page<DeviceInfo> findBySnCodeIn(List<String> snCodeList, Pageable pageable);

}
