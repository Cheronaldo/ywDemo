package com.cherry.converter;

import com.cherry.dataobject.HistoricalData;
import com.cherry.dto.HistoryDataDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * HistoricalData -> HistoryDataDTO 转换器
 * Created by Administrator on 2018/01/24.
 */
public class HistoricalData2HistoryDataDTOConverter {

    public static HistoryDataDTO convert(HistoricalData historicalData){

        HistoryDataDTO dataDTO = new HistoryDataDTO();
        BeanUtils.copyProperties(historicalData, dataDTO);
        return dataDTO;

    }

    public static List<HistoryDataDTO> convert(List<HistoricalData> historicalDataList){

        return historicalDataList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());

    }

}
