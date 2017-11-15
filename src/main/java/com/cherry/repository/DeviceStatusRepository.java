package com.cherry.repository;

import com.cherry.dataobject.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 设备状态表DAO层
 * Created by Administrator on 2017/11/10.
 */
public interface DeviceStatusRepository extends JpaRepository<DeviceStatus,String>{

}
