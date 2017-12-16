package com.cherry.service;

import com.cherry.form.AlarmQueryForm;
import com.cherry.form.AlarmRuleAddForm;
import com.cherry.form.AlarmUpdateForm;
import org.springframework.data.domain.Pageable;


import java.util.Map;

/**
 * 设备报警service层
 * Created by Administrator on 2017/12/07.
 */
public interface AlarmService {

    /**
     * 查询设备一定时间的报警信息列表
     * 分页
     * @param form
     * @param pageable
     * @return
     */
    Map<String, Object> getRecordList(AlarmQueryForm form, Pageable pageable);

    /**
     * 修改设备的一条报警记录处理状态
     * 分页
     * @param form
     * @param pageable
     * @return
     */
    Map<String, Object> updateRecord(AlarmUpdateForm form, Pageable pageable);

    /**
     * 通过 SN 版本号 获取设备阈值列表
     * 分页
     * 按偏移排序
     * @param snCode
     * @param protocolVersion
     * @param pageable
     * @return
     */
    Map<String, Object> getThresholdList(String snCode, String protocolVersion, Pageable pageable);

    /**
     * 通过ID 获取单个子项 最大的上 下阈值
     * 协议详情表
     * @param id
     * @return
     */
    Map<String, Object> getThresholdSingle(String id);

    /**
     * 通过ID 更新一条报警规则
     * @param form
     * @return
     */
    Integer updateThreshold(AlarmUpdateForm form);

    /**
     * 通过协议版本号 获取数据名称及对应阈值 最大 最小 限 列表
     * 协议详情表
     * @param protocolVersion
     * @return
     */
    Map<String, Object> getThresholdList(String protocolVersion);

    /**
     * 添加一条报警规则
     * @param form
     * @return
     */
    Integer addThreshold(AlarmRuleAddForm form);

}
