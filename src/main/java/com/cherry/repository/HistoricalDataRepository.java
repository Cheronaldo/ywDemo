package com.cherry.repository;

import com.cherry.dataobject.HistoricalData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 设备历史数据表DAO层
 * Created by Administrator on 2017/11/29.
 */
public interface HistoricalDataRepository extends JpaRepository<HistoricalData,String>{
}
