package com.cherry.repository;

import com.cherry.dataobject.AlarmMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 报警信息代码表DAO层
 * Created by Administrator on 2017/12/07.
 */
public interface AlarmMessageRepository extends JpaRepository<AlarmMessage,Integer>{

    /**
     * 通过报警码获取报警详情
     * @param alarmCode
     * @return
     */
    AlarmMessage findByAlarmCode(Integer alarmCode);
}
