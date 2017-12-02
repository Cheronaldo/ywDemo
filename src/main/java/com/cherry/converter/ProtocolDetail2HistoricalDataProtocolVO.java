package com.cherry.converter;

import com.cherry.dataobject.ProtocolDetail;
import com.cherry.vo.HistoricalDataProtocolVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  ProtocolDetail --> HistoricalDataProtocolVO 转换器
 * Created by Administrator on 2017/12/02.
 */
public class ProtocolDetail2HistoricalDataProtocolVO {

    public static HistoricalDataProtocolVO convert(ProtocolDetail detail){

        HistoricalDataProtocolVO protocolVO = new HistoricalDataProtocolVO();
        BeanUtils.copyProperties(detail, protocolVO);
        return protocolVO;

    }

    public static List<HistoricalDataProtocolVO> convert(List<ProtocolDetail> detailList){

        return detailList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

}
