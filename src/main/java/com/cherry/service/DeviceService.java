package com.cherry.service;

import com.cherry.dataobject.DeviceInfo;
import com.cherry.dto.SiteDeviceInfoDTO;
import com.cherry.form.SiteDeviceForm;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
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
     * 1.协议适配
     * 2.现场用户保存设备信息
     * 用于注册 修改
     * @param siteDeviceForm
     * @return
     */
    Integer saveSiteUserDeviceInfo(SiteDeviceForm siteDeviceForm);

    /**
     * 现场用户设备注册
     * 协议适配
     * 设备信息储存
     * 用户设备关系操作
     * @param siteDeviceForm
     * @return
     */
    Integer siteUserDeviceRegister(SiteDeviceForm siteDeviceForm);

    /**
     * 用户与设备解绑
     * 用于经销商 现场 用户 与已注册设备的解绑
     * @param snCode
     * @param userName
     * @return
     */
    Map<String,Object> userDeviceUnbind(String snCode, String userName);

    /**
     * 通过用户名查询 该用户启用是所有设备
     * 用于主页面列表及地图查询列表显示
     * @param userName
     * @return
     */
    List<DeviceInfo> listFindByUser(String userName);

    /**
     * 通过SN码 获取设备状态
     * 用于主页面列表及地图查询列表 获取设备状态
     * @param snCode
     * @return
     */
    Integer getStatusBySnCode(String snCode);

    /**
     * 获取现场用户 名下的设备列表
     * @param userName
     * @return
     */
    Map<String, Object> pageSiteGet(String userName, Pageable pageable);

    /**
     * 获取经销商名下 且现场未绑定的 设备列表
     * @param agencyName
     * @param siteName
     * @param pageable
     * @return
     */
    Map<String, Object> pageAgencyGet(String agencyName, String siteName, Pageable pageable);

    /**
     * 现场用户 与设备 绑定
     * @param userName
     * @param snCode
     * @return
     */
    Integer siteDeviceBind(String userName, String snCode);

    /**
     * 现场用户 与设备 解绑
     * @param userName
     * @param snCode
     * @return
     */
    Integer siteDeviceUnbind(String userName, String snCode);

}
