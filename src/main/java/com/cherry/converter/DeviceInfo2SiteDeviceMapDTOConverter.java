package com.cherry.converter;

import com.cherry.dataobject.DeviceInfo;
import com.cherry.dto.SiteDeviceMapDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 设备基本信息与现场设备地图信息列表DTO 转换器
 * Created by Administrator on 2017/11/11.
 */
public class DeviceInfo2SiteDeviceMapDTOConverter {

    public static SiteDeviceMapDTO convert(DeviceInfo deviceInfo){

        SiteDeviceMapDTO siteDeviceMapDTO = new SiteDeviceMapDTO();
        BeanUtils.copyProperties(deviceInfo, siteDeviceMapDTO);
        return siteDeviceMapDTO;

    }

    public static List<SiteDeviceMapDTO> convert(List<DeviceInfo> deviceInfoList){
        return deviceInfoList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

}
