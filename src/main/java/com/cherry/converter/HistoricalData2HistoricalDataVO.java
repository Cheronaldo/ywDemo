package com.cherry.converter;

import com.cherry.dataobject.HistoricalData;
import com.cherry.util.DateUtil;
import com.cherry.vo.HistoricalDataVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**HistoricalData --> HistoricalDataVO 转换器
 * Created by Administrator on 2017/12/02.
 */
public class HistoricalData2HistoricalDataVO {

    public static HistoricalDataVO convert(HistoricalData historicalData){
        HistoricalDataVO historicalDataVO = new HistoricalDataVO();
        historicalDataVO.setDataTime(DateUtil.convertDate2String(historicalData.getDataTime()));
        historicalDataVO.setDeviceData(historicalData.getDeviceData());
        return historicalDataVO;
    }

    public static List<HistoricalDataVO> convert(List<HistoricalData> historicalDataList){

        return historicalDataList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

}
