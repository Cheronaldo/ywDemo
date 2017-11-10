package com.cherry.service.impl;

import com.cherry.converter.DeviceInfo2SiteDeviceInfoDTOConverter;
import com.cherry.dataobject.DeviceInfo;
import com.cherry.dataobject.DeviceVerify;
import com.cherry.dataobject.UserDeviceRelationship;
import com.cherry.dto.SiteDeviceInfoDTO;
import com.cherry.enums.DeviceHandleEnum;
import com.cherry.form.SiteDeviceForm;
import com.cherry.repository.DeviceInfoRepository;
import com.cherry.repository.DeviceStatusRepository;
import com.cherry.repository.DeviceVerifyRepository;
import com.cherry.repository.UserDeviceRelationshipRepository;
import com.cherry.service.DeviceService;
import com.cherry.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备service实现层
 * Created by Administrator on 2017/11/10.
 */
@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService{

    @Autowired
    private DeviceInfoRepository deviceInfoRepository;

    @Autowired
    private DeviceStatusRepository deviceStatusRepository;

    @Autowired
    private DeviceVerifyRepository deviceVerifyRepository;

    @Autowired
    private UserDeviceRelationshipRepository userDeviceRelationshipRepository;


    @Override
    public Map<String, Object> checkSiteDeviceIsOnHand(String snCode, String checkCode, String userName) {

        Map<String,Object> map = new HashMap<String,Object>();

        //1.SN码是否有对应的校验码
        DeviceVerify deviceVerify = deviceVerifyRepository.findBySnCode(snCode);
        if(deviceVerify == null){
            //SN码或校验码错误
            map.put("code", 3);
            map.put("msg",DeviceHandleEnum.SN_OR_CHECK_CODE_ERROR.getMessage());
            return map;
        }

        //2.校验码是否有效
        boolean isValid = DateUtil.ifDateValid(deviceVerify.getGenerateTime());
        if(!isValid){
            //校验码失效
            map.put("code", 4);
            map.put("msg",DeviceHandleEnum.CHECK_CODE_INVALID.getMessage());
            return map;
        }

        //3.是否为现场首次注册
        DeviceInfo deviceInfo = deviceInfoRepository.findOne(snCode);
        SiteDeviceInfoDTO siteDeviceInfoDTO = DeviceInfo2SiteDeviceInfoDTOConverter.convert(deviceInfo);
        if(siteDeviceInfoDTO.getDeviceAddress() == null){
            //设备部署地址为空 则为首次注册
            map.put("code", 0);
            map.put("msg",DeviceHandleEnum.FIRST_REGISTER.getMessage());
            return map;
        }

        //4.是否为该用户注册
        //5.该用户是否已启用该设备
        UserDeviceRelationship userDeviceRelationship = userDeviceRelationshipRepository.findBySnCodeAndUserName(snCode, userName);
        if((userDeviceRelationship == null) || (userDeviceRelationship.getIsUsed() == 0) ){
            //追加注册 将查询到的注册信息回传 (用户为注册 或 之前注册 目前已注销)
            map.put("code", 1);
            map.put("msg",DeviceHandleEnum.ADD_REGISTER.getMessage());
            map.put("data", siteDeviceInfoDTO);
            return map;
        }

        //该用户已注册 且以启用 此时只能修改 或注销
        map.put("code", 2);
        map.put("msg",DeviceHandleEnum.ALREADY_REGISTERED.getMessage());
        map.put("data", siteDeviceInfoDTO);
        return map;


    }

    @Override
    public Map<String, Object> siteUserSaveDeviceInfo(SiteDeviceForm siteDeviceForm) {
        return null;
    }

    @Override
    public Map<String, Object> userDeviceRelationshipHandle(String snCode, String userName) {
        return null;
    }

    @Override
    public Map<String, Object> userDeviceUnbind(String snCode, String userName) {
        return null;
    }

    @Override
    public DeviceInfo findOneBySnCode(String snCode) {
        return null;
    }

    @Override
    public Map<String, Object> findListByUser(String userName) {
        return null;
    }

    @Override
    public Map<String, Object> findMapByUser(String userName) {
        return null;
    }
}
