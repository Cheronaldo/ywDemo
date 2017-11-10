package com.cherry.service;

import com.cherry.dataobject.DeviceInfo;

/**
 * 设备service接口层
 * Created by Administrator on 2017/11/10.
 */
public interface DeviceService {

    /**
     * 通过SN码查询一条记录
     * @param snCode
     * @return
     */
    DeviceInfo findOneBySnCode(String snCode);



}
