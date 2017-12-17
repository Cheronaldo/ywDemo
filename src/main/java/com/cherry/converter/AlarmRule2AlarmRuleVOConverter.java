package com.cherry.converter;

import com.cherry.dataobject.AlarmRule;
import com.cherry.vo.AlarmRuleVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AlarmRule --> AlarmRuleVO 转换器
 * Created by Administrator on 2017/12/17.
 */
public class AlarmRule2AlarmRuleVOConverter {

    public static AlarmRuleVO convert(AlarmRule alarmRule){

        AlarmRuleVO alarmRuleVO = new AlarmRuleVO();
        BeanUtils.copyProperties(alarmRule, alarmRuleVO);
        return alarmRuleVO;

    }

    public static List<AlarmRuleVO> convert(List<AlarmRule> alarmRuleList){

        return alarmRuleList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());

    }

}
