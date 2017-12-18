package com.cherry.converter;

import com.cherry.dataobject.ProtocolDetail;
import com.cherry.vo.AlarmThresholdVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**ProtocolDetail -> AlarmThresholdVO 转换器
 * Created by Administrator on 2017/12/18.
 */
public class ProtocolDetail2AlarmThresholdVOConverter {

    public static AlarmThresholdVO covert(ProtocolDetail detail){

        AlarmThresholdVO thresholdVO = new AlarmThresholdVO();
        BeanUtils.copyProperties(detail, thresholdVO);
        return thresholdVO;

    }

    public static List<AlarmThresholdVO> convert(List<ProtocolDetail> detailList){

       return detailList.stream().map(e -> covert(e)).collect(Collectors.toList());

    }


}
