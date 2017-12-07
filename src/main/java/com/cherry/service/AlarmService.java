package com.cherry.service;

import com.cherry.form.AlarmQueryForm;
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

}
