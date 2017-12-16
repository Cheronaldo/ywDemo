package com.cherry.repository;

import com.cherry.dataobject.AlarmRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 报警规则DAO层
 * Created by Administrator on 2017/12/16.
 */
public interface AlarmRuleRepository extends JpaRepository<AlarmRule, String>{

    /**
     * 通过 SN 版本号 查询设备报警规则
     * 按照偏移值排序
     * 分页
     * @param snCode
     * @param protocolVersion
     * @param pageable
     * @return
     */
    Page<AlarmRule> findBySnCodeAndProtocolVersionOrderByOffsetNumberAsc(String snCode, String protocolVersion, Pageable pageable);


}
