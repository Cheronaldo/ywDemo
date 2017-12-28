package com.cherry.util;

import com.cherry.dataobject.VisibleStrategy;

/**
 * 对象属性操作工具类
 * Created by Administrator on 2017/12/28.
 */
public class BeanUtil {

    /**
     * 添加默认策略
     * @param userName
     * @param snCode
     * @param protocolVersion
     * @param mask
     * @return
     */
    public static VisibleStrategy addVisibleStrategy(String userName, String snCode, String protocolVersion, String mask){

        VisibleStrategy strategy = new VisibleStrategy();
        strategy.setId(KeyUtil.genUniqueKey());
        strategy.setUserName(userName);
        strategy.setSnCode(snCode);
        strategy.setProtocolVersion(protocolVersion);
        strategy.setVisibleMask(mask);
        strategy.setAlarmMask(mask);
        strategy.setIsUsed(1);
        strategy.setUsedTime(DateUtil.getDate());

        return strategy;

    }


}
