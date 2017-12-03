package com.cherry.repository;

import com.cherry.dataobject.HistoricalData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 设备历史数据表DAO层
 * Created by Administrator on 2017/11/29.
 */
public interface HistoricalDataRepository extends JpaRepository<HistoricalData,String>{

    /**
     * 通过SN 协议版本 查询一段时间内的数据记录
     * 分页
     * 所有项历史数据
     * @param snCode
     * @param protocolVersion
     * @param oldDate
     * @param newDate
     * @param pageable
     * @return
     */
    @Transactional
    @Query(value = "SELECT * FROM historical_data WHERE historical_data.sn_code = ?1 AND historical_data.protocol_version = ?2 AND historical_data.data_time BETWEEN ?3 AND ?4 ORDER BY ?#{#pageable}",
            nativeQuery = true)
    Page<HistoricalData> findBySnCodeAndProtocolVersionAndDataTime(String snCode, String protocolVersion, Date oldDate, Date newDate, Pageable pageable);

    /**
     *
     * 通过SN 协议版本 查询一段时间内的数据记录
     * 分页
     * 所有项历史数据
     * 以数据逆序输出（显示最新数据）
     * @param snCode
     * @param protocolVersion
     * @param oldDate
     * @param newDate
     * @param pageable
     * @return
     */
    Page<HistoricalData> findBySnCodeAndProtocolVersionAndDataTimeBetweenOrderByIdDesc(String snCode, String protocolVersion, Date oldDate, Date newDate, Pageable pageable);

    /**
     * 通过SN 协议版本 查询一段时间内的数据记录
     * 单项历史数据
     * @param snCode
     * @param protocolVersion
     * @param oldDate
     * @param newDate
     * @return
     */
    List<HistoricalData> findBySnCodeAndProtocolVersionAndDataTimeBetween(String snCode, String protocolVersion, Date oldDate, Date newDate);

}
