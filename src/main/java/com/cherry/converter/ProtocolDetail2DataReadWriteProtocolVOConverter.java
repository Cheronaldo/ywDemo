package com.cherry.converter;

import com.cherry.dataobject.ProtocolDetail;
import com.cherry.vo.DataReadWriteProtocolVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ProtocolDetail -> DataReadWriteProtocolVO 转换器
 * Created by Administrator on 2018/01/16.
 */
public class ProtocolDetail2DataReadWriteProtocolVOConverter {

    public static DataReadWriteProtocolVO convert(ProtocolDetail detail){

        DataReadWriteProtocolVO readWriteProtocolVO = new DataReadWriteProtocolVO();
        BeanUtils.copyProperties(detail, readWriteProtocolVO);
        return readWriteProtocolVO;

    }

    public static List<DataReadWriteProtocolVO> convert(List<ProtocolDetail> detailList){

        return detailList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());

    }

}
