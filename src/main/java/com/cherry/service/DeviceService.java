package com.cherry.service;

import com.cherry.dataobject.DeviceInfo;
import com.cherry.form.SiteDeviceForm;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备service接口层
 * Created by Administrator on 2017/11/10.
 */
public interface DeviceService {

    /**
     * 现场用户操作类型校验
     * 1.校验SN码 校验码是否匹配
     * 2.设备是否为首次注册
     * 3.追加注册时，是否是该用户已注册
     * @param snCode
     * @param checkCode
     * @param userName
     * @return
     */
    Map<String,Object> checkSiteDeviceIsOnHand(String snCode, String checkCode, String userName);

    /**
     * 现场用户保存设备信息
     * 用于注册 修改
     * @param siteDeviceForm
     * @return
     */
    Map<String,Object> siteUserSaveDeviceInfo(SiteDeviceForm siteDeviceForm);

    /**
     * 操作用户与设备关系
     * 用于经销商 现场 用户注册设备
     * @param snCode
     * @param userName
     * @return
     */
    Map<String,Object> userDeviceRelationshipHandle(String snCode, String userName);

    /**
     * 用户与设备解绑
     * 用于经销商 现场 用户 与已注册设备的解绑
     * @param snCode
     * @param userName
     * @return
     */
    Map<String,Object> userDeviceUnbind(String snCode, String userName);

    /**
     * 通过SN码查询一条记录
     * @param snCode
     * @return
     */
    DeviceInfo findOneBySnCode(String snCode);

    /**
     * 通过用户名查询 该用户启用是所有设备
     * 用于主页面列表及地图查询列表显示
     * @param userName
     * @return
     */
    Map<String,Object> findListByUser(String userName);

    /**
     * 通过SN码 获取设备状态
     * 用于主页面列表及地图查询列表 获取设备状态
     * @param snCode
     * @return
     */
    Integer findStatusBySnCode(String snCode);

}
