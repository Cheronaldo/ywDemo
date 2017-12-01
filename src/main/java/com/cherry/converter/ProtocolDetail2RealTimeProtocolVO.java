package com.cherry.converter;

import com.cherry.dataobject.ProtocolDetail;
import com.cherry.vo.RealTimeProtocolVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ProtocolDetail --> RealTimeProtocolVO 转换器
 * Created by Administrator on 2017/12/01.
 */
public class ProtocolDetail2RealTimeProtocolVO {

    public static RealTimeProtocolVO convert(ProtocolDetail protocolDetail){
        RealTimeProtocolVO realTimeProtocolVO = new RealTimeProtocolVO();
        BeanUtils.copyProperties(protocolDetail, realTimeProtocolVO);
        return realTimeProtocolVO;

    }

    public static List<RealTimeProtocolVO> convert(List<ProtocolDetail> detailList){
        return detailList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());

    }

}
