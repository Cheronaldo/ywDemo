package com.cherry.converter;

import com.cherry.dataobject.DeviceInfo;
import com.cherry.dto.SiteDeviceListDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 设备基本信息与现场设备信息列表DTO 转换器
 * Created by Administrator on 2017/11/11.
 */
public class DeviceInfo2SiteDeviceListDTOConverter {

    public static SiteDeviceListDTO convert(DeviceInfo deviceInfo){

        SiteDeviceListDTO siteDeviceListDTO = new SiteDeviceListDTO();
        BeanUtils.copyProperties(deviceInfo, siteDeviceListDTO);
        return siteDeviceListDTO;

    }

    public static List<SiteDeviceListDTO> convert(List<DeviceInfo> deviceInfoList){
        return deviceInfoList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

}
