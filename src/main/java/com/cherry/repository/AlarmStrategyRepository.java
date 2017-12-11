package com.cherry.repository;

import com.cherry.dataobject.AlarmStrategy;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户报警策略表DAO层
 * Created by Administrator on 2017/11/29.
 */
public interface AlarmStrategyRepository extends JpaRepository<AlarmStrategy,String>{

    /**
     * 通过用户名 SN 版本号 获取 报警策略记录
     * @param userName
     * @param snCode
     * @param protocolVersion
     * @param isUsed
     * @return
     */
    AlarmStrategy findByUserNameAndSnCodeAndProtocolVersionAndIsUsed(String userName, String snCode, String protocolVersion, Integer isUsed);

    /**
     * 通过用户名 SN 版本号 可视掩码获取 可视策略记录
     * @param userName
     * @param snCode
     * @param protocolVersion
     * @param alarmMask
     * @return
     */
    AlarmStrategy findByUserNameAndSnCodeAndProtocolVersionAndAlarmMask(String userName, String snCode, String protocolVersion, String alarmMask);

    /**
     * 通过用户名 SN 获取用户对应设备当前启用的策略
     * @param userName
     * @param snCode
     * @param isUsed
     * @return
     */
    AlarmStrategy findByUserNameAndSnCodeAndIsUsed(String userName, String snCode, Integer isUsed);

}
