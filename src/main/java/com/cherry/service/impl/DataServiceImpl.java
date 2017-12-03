package com.cherry.service.impl;

import com.cherry.converter.HistoricalData2HistoricalDataVO;
import com.cherry.dataobject.HistoricalData;
import com.cherry.form.AllDataQueryForm;
import com.cherry.form.SingleDataQueryForm;
import com.cherry.repository.HistoricalDataRepository;
import com.cherry.service.DataService;
import com.cherry.util.DateUtil;
import com.cherry.vo.HistoricalDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 设备数据service实现层
 * Created by Administrator on 2017/12/01.
 */
@Service
public class DataServiceImpl implements DataService{

    @Autowired
    private HistoricalDataRepository dataRepository;

    @Override
    public Map<String, Object> listGetAll(AllDataQueryForm form, Pageable pageable) {

        Map<String, Object> map = new HashMap<String, Object>();
        Date oldDate = null;
        Date newDate = null;
        // 1.判断页面时间参数是否为 null
        boolean isOldDateNull = StringUtils.isEmpty(form.getOldDate());
        boolean isNewDateNull = StringUtils.isEmpty(form.getNewDate());
        if (isOldDateNull){
            // 为空 则默认为最早记录的时间
            form.setOldDate("2017-01-01 00:00:00");
        }
        oldDate = DateUtil.convertString2Date(form.getOldDate());
        if (isNewDateNull){
            // 为空则默认为当前系统时间
            newDate = new Date();
        }else {
            newDate = DateUtil.convertString2Date(form.getNewDate());
        }

        // 2.获取历史数据记录 分页对象
        Page<HistoricalData> dataPage = dataRepository.findBySnCodeAndProtocolVersionAndDataTimeBetweenOrderByIdDesc(form.getSnCode(),
                                                                        form.getProtocolVersion(),
                                                                        oldDate,
                                                                        newDate,
                                                                        pageable);
        if (dataPage.getTotalPages() == 0){
            return null;
        }

        // 3.封装为 HistoricalDataVO
        List<HistoricalDataVO> historicalDataVOList = HistoricalData2HistoricalDataVO.convert(dataPage.getContent());


        map.put("total",dataPage.getTotalPages());
        map.put("records",dataPage.getTotalElements());
        map.put("rows",historicalDataVOList);


        return map;
    }

    @Override
    public List<HistoricalDataVO> listGetOne(SingleDataQueryForm form) {

        // 1.判断页面时间参数是否为 null
        Date oldDate = null;
        Date newDate = null;
        // 1.判断页面时间参数是否为 null
        boolean isOldDateNull = StringUtils.isEmpty(form.getOldDate());
        boolean isNewDateNull = StringUtils.isEmpty(form.getNewDate());
        if (isOldDateNull){
            // 为空 则默认为最早记录的时间
            form.setOldDate("2017-01-01 00:00:00");
        }
        oldDate = DateUtil.convertString2Date(form.getOldDate());
        if (isNewDateNull){
            // 为空则默认为当前系统时间
            newDate = new Date();
        }else {
            newDate = DateUtil.convertString2Date(form.getNewDate());
        }

        // 2.获取历史数据记录列表
        List<HistoricalData> historicalDataList = dataRepository.findBySnCodeAndProtocolVersionAndDataTimeBetween(form.getSnCode(),
                form.getProtocolVersion(),
                oldDate,
                newDate);

        if (historicalDataList.size() == 0){
            return null;
        }
        // 3. 获取单项历史数据对象列表  封装为lambda表达式
        List<HistoricalDataVO> historicalDataVOList = new ArrayList<>();
        for (HistoricalData historicalData : historicalDataList){

            HistoricalDataVO historicalDataVO = new HistoricalDataVO();
            String[] dataArray = historicalData.getDeviceData().split("_");
            historicalDataVO.setDataTime(DateUtil.convertDate2String(historicalData.getDataTime()));
            historicalDataVO.setDeviceData(dataArray[form.getOffsetNumber() - 1]);

            historicalDataVOList.add(historicalDataVO);
        }

        return historicalDataVOList;
    }
}
