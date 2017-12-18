package com.cherry.repository;

import com.cherry.dataobject.AlarmRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * 设备报警记录DAO层
 * Created by Administrator on 2017/12/06.
 */
public interface AlarmRecordRepository extends JpaRepository<AlarmRecord,String>{

    /**
     * 通过SN码 获取一定时间内的报警记录 列表
     * 分页
     * @param snCode
     * @param oldDate
     * @param newDate
     * @param pageable
     * @return
     */
    Page<AlarmRecord> findBySnCodeAndProtocolVersionAndHandleResultStartingWithAndAlarmTimeBetweenOrderByIdDesc(String snCode, String protocolVersion, String handleResult, Date oldDate, Date newDate, Pageable pageable);


}
