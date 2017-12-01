package com.cherry.repository;

import com.cherry.dataobject.VisibleStrategy;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户可见策略表DAO层
 * Created by Administrator on 2017/11/29.
 */
public interface VisibleStrategyRepository extends JpaRepository<VisibleStrategy,String>{


    /**
     * 通过用户名 SN 版本号 获取 当前可视策略记录
     * @param userName
     * @param snCode
     * @param protocolVersion
     * @param isUsed
     * @return
     */
    VisibleStrategy findByUserNameAndSnCodeAndProtocolVersionAndIsUsed(String userName, String snCode, String protocolVersion, Integer isUsed);

    /**
     * 通过用户名 SN 版本号 可视掩码获取 可视策略记录
     * @param userName
     * @param snCode
     * @param protocolVersion
     * @param visibleMask
     * @return
     */
    VisibleStrategy findByUserNameAndSnCodeAndProtocolVersionAndVisibleMask(String userName, String snCode, String protocolVersion, String visibleMask);

}
