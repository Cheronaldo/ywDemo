package com.cherry.service.impl;

import com.cherry.dataobject.HistoricalData;
import com.cherry.form.AllDataQueryForm;
import com.cherry.form.SingleDataQueryForm;
import com.cherry.repository.HistoricalDataRepository;
import com.cherry.service.DataService;
import com.cherry.vo.SingleHistoricalDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备数据service实现层
 * Created by Administrator on 2017/12/01.
 */
@Service
public class DataServiceImpl implements DataService{

    @Autowired
    private HistoricalDataRepository dataRepository;

    @Override
    public Page<HistoricalData> listGetAll(AllDataQueryForm form, Pageable pageable) {
        return dataRepository.findBySnCodeAndProtocolVersionAndDataTime(form.getSnCode(),
                form.getProtocolVersion(),
                form.getOldDate(),
                form.getNewDate(),
                pageable);
    }

    @Override
    public List<SingleHistoricalDataVO> listGetOne(SingleDataQueryForm form) {
        return null;
    }
}
