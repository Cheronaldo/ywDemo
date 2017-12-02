package com.cherry.service;

import com.cherry.dataobject.HistoricalData;
import com.cherry.form.AllDataQueryForm;
import com.cherry.form.SingleDataQueryForm;
import com.cherry.vo.HistoricalDataVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 设备数据Service层
 * Created by Administrator on 2017/12/01.
 */
public interface DataService {

    /**
     * 查询设备所有项数据在指定时间内的历史数据记录 列表
     * 分页
     * @param form
     * @param pageable
     * @return
     */
    Map<String, Object> listGetAll(AllDataQueryForm form, Pageable pageable);

    /**
     * 查询设备单项数据在指定时间内的历史数据记录 列表
     * @param form
     * @return
     */
    List<HistoricalDataVO> listGetOne(SingleDataQueryForm form);

}
