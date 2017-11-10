package com.cherry.converter;

import com.cherry.dataobject.DeviceInfo;
import com.cherry.dto.SiteDeviceInfoDTO;
import org.springframework.beans.BeanUtils;

/**
 * 设备基本信息与现场基本信息DTO 转换器
 * Created by Administrator on 2017/11/10.
 */
public class DeviceInfo2SiteDeviceInfoDTOConverter {

    public static SiteDeviceInfoDTO convert(DeviceInfo deviceInfo){

        SiteDeviceInfoDTO siteDeviceInfoDTO = new SiteDeviceInfoDTO();
        BeanUtils.copyProperties(deviceInfo, siteDeviceInfoDTO);
        return siteDeviceInfoDTO;

    }
}
