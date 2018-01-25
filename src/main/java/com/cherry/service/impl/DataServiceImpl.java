package com.cherry.service.impl;

import com.cherry.converter.HistoricalData2HistoricalDataVO;
import com.cherry.converter.HistoricalData2HistoryDataDTOConverter;
import com.cherry.dataobject.HistoricalData;
import com.cherry.dto.HistoryDataDTO;
import com.cherry.enums.DataHandleEnum;
import com.cherry.form.AllDataQueryForm;
import com.cherry.form.SingleDataQueryForm;
import com.cherry.repository.HistoricalDataRepository;
import com.cherry.service.DataService;
import com.cherry.util.DateUtil;
import com.cherry.util.ExportExcel;
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
        // 1.判断页面时间参数是否为 null
        Date oldDate = DateUtil.oldDateHandle(form.getOldDate());
        Date newDate = DateUtil.newDateHandle(form.getNewDate());

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
        List<HistoricalDataVO> historicalDataVOList = HistoricalData2HistoricalDataVO.convertAll(dataPage.getContent());


        map.put("code", 0);
        map.put("msg", DataHandleEnum.GET_DATA_SUCCESS.getMessage());
        map.put("total",dataPage.getTotalPages());
        map.put("records",dataPage.getTotalElements());
        map.put("data",historicalDataVOList);


        return map;
    }

    @Override
    public List<HistoricalDataVO> listGetOne(SingleDataQueryForm form) {

        // 1.判断页面时间参数是否为 null
        Date oldDate = DateUtil.oldDateHandle(form.getOldDate());
        Date newDate = DateUtil.newDateHandle(form.getNewDate());

        // 2.获取历史数据记录列表
        List<HistoricalData> historicalDataList = dataRepository.findBySnCodeAndProtocolVersionAndDataTimeBetween(form.getSnCode(),
                form.getProtocolVersion(),
                oldDate,
                newDate);

        if (historicalDataList.size() == 0){
            return null;
        }
        // 3. 获取单项历史数据对象列表  不封装为lambda表达式 因为还有参数 offsetNumber ,使用lambda表达式更麻烦
        List<HistoricalDataVO> historicalDataVOList = new ArrayList<>();
        for (HistoricalData historicalData : historicalDataList){

            HistoricalDataVO historicalDataVO = new HistoricalDataVO();
            String[] dataArray = historicalData.getDeviceData().split("_");
            historicalDataVO.setDataTime(DateUtil.convertDate2String(historicalData.getDataTime()));
            historicalDataVO.setDeviceData(dataArray[Integer.parseInt(form.getOffsetNumber()) - 1]);

            historicalDataVOList.add(historicalDataVO);
        }

        return historicalDataVOList;
    }

    @Override
    public String exportExcel(AllDataQueryForm form, String headers, String userName) {

        // 1.查询数据列表
        // 1.判断页面时间参数是否为 null
        Date oldDate = DateUtil.oldDateHandle(form.getOldDate());
        Date newDate = DateUtil.newDateHandle(form.getNewDate());

        // 2.获取历史数据记录列表
        List<HistoricalData> historicalDataList = dataRepository.findBySnCodeAndProtocolVersionAndDataTimeBetweenOrderByIdDesc(form.getSnCode(),
                form.getProtocolVersion(),
                oldDate,
                newDate);

        if (historicalDataList.size() == 0){
            return null;
        }

        // 2.封装至 HistoryDataDTO 对象
        List<HistoryDataDTO> dataDTOList = HistoricalData2HistoryDataDTOConverter.convert(historicalDataList);

        // 3.数据导出
        return ExportExcel.exportExcel(dataDTOList, headers, userName, form.getSnCode());

    }
}
