package com.cherry.converter;

import com.cherry.dataobject.ProtocolDetail;
import com.cherry.vo.ProtocolDetailVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ProtocolDetail 转 ProtocolDetailVO 转换器
 * Created by Administrator on 2017/12/01.
 */
public class ProtocolDetail2ProtocolDetailVO {

    public static ProtocolDetailVO convert(ProtocolDetail protocolDetail){
        ProtocolDetailVO protocolDetailVO = new ProtocolDetailVO();
        BeanUtils.copyProperties(protocolDetail, protocolDetailVO);
        return protocolDetailVO;
    }

    public static List<ProtocolDetailVO> convert(List<ProtocolDetail> detailList){

        return detailList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }


}
