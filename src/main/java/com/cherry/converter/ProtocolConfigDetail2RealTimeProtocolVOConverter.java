package com.cherry.converter;

import com.cherry.dataobject.ProtocolConfigDetail;
import com.cherry.vo.RealTimeProtocolVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ProtocolConfigDetail --> RealTimeProtocolVO 转换器
 * 可删除
 * Created by Administrator on 2017/11/24.
 */
public class ProtocolConfigDetail2RealTimeProtocolVOConverter {

    public static RealTimeProtocolVO convert(ProtocolConfigDetail protocolConfigDetail){
        RealTimeProtocolVO realTimeProtocolVO = new RealTimeProtocolVO();
        BeanUtils.copyProperties(protocolConfigDetail, realTimeProtocolVO);
        return realTimeProtocolVO;
    }

    public static List<RealTimeProtocolVO> convert(List<ProtocolConfigDetail> detailList){
        return detailList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

}
