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
import com.cherry.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        // 1.SN码是否有对应的校验码 查询最新一条校验记录
        DeviceVerify deviceVerify = deviceVerifyRepository.findLatestOneBySnCode(snCode);
        boolean isChecked = (deviceVerify == null) || (!checkCode.equals(deviceVerify.getCheckCode()));
        if (isChecked){
            // SN码或校验码错误
            map.put("code", 3);
            map.put("msg",DeviceHandleEnum.SN_OR_CHECK_CODE_ERROR.getMessage());
            return map;
        }

        // 2.校验码是否有效
        boolean isValid = DateUtil.ifDateValid(deviceVerify.getGenerateTime());
        if (!isValid){
            // 校验码失效
            map.put("code", 4);
            map.put("msg",DeviceHandleEnum.CHECK_CODE_INVALID.getMessage());
            return map;
        }

        // 3.经销商是否已注册该设备
        DeviceInfo deviceInfo = deviceInfoRepository.findOne(snCode);
        if (deviceInfo == null){
            // 经销商未注册
            map.put("code",5);
            map.put("msg",DeviceHandleEnum.REGISTER_ENABLE.getMessage());
            return map;
        }
        // 4.是否为现场首次注册
        SiteDeviceInfoDTO siteDeviceInfoDTO = DeviceInfo2SiteDeviceInfoDTOConverter.convert(deviceInfo);
        // 从校验表中获取相应版本号
        siteDeviceInfoDTO.setProtocolVersion(deviceVerify.getProtocolVersion());
        //if (siteDeviceInfoDTO.getDeviceAddress() == null){
        if (StringUtils.isEmpty(siteDeviceInfoDTO.getDeviceAddress())){
            // 设备部署地址为空 则为首次注册
            map.put("code", 0);
            map.put("msg",DeviceHandleEnum.FIRST_REGISTER.getMessage());
            map.put("data", siteDeviceInfoDTO.getProtocolVersion());
            return map;
        }

        // 5.是否为该用户注册
        // 6.该用户是否已启用该设备
        UserDeviceRelationship userDeviceRelationship = userDeviceRelationshipRepository.findBySnCodeAndUserName(snCode, userName);
        boolean isUsed = (userDeviceRelationship == null) || (userDeviceRelationship.getIsUsed() == 0);
        if (isUsed){
            // 追加注册 将查询到的注册信息回传 (用户为注册 或 之前注册 目前已注销)
            map.put("code", 1);
            map.put("msg",DeviceHandleEnum.ADD_REGISTER.getMessage());
            map.put("data", siteDeviceInfoDTO);
            return map;
        }

        // 该用户已注册 且以启用 此时只能修改 或注销
        map.put("code", 2);
        map.put("msg",DeviceHandleEnum.ALREADY_REGISTERED.getMessage());
        map.put("data", siteDeviceInfoDTO);
        return map;


    }

    @Override
    public Map<String, Object> saveSiteUserDeviceInfo(SiteDeviceForm siteDeviceForm) {

        Map<String,Object> map = new HashMap<String,Object>();

        // 1.查询设备信息记录  修改记录时 一定要先查 再修改 最后储存修改后的结果
        DeviceInfo deviceInfo =deviceInfoRepository.findOne(siteDeviceForm.getSnCode());

        // 2.将form对象属性赋给设备信息对象
        BeanUtils.copyProperties(siteDeviceForm, deviceInfo);
        deviceInfoRepository.save(deviceInfo);
        map.put("code",0);
        map.put("msg",DeviceHandleEnum.SAVE_SUCCESS.getMessage());

        return map;
    }

    @Override
    public Map<String, Object> saveUserDeviceRelationshipHandle(String snCode, String userName) {

        Map<String,Object> map = new HashMap<String,Object>();

        // 1.查询用户是否已注册过该设备
        UserDeviceRelationship userDeviceRelationship = userDeviceRelationshipRepository.findBySnCodeAndUserName(snCode, userName);
        if (userDeviceRelationship == null){
            // 2.没有 则添加用户 设备关系记录
            UserDeviceRelationship relationship = new UserDeviceRelationship();
            relationship.setId(KeyUtil.genUniqueKey());
            relationship.setSnCode(snCode);
            relationship.setUserName(userName);
            relationship.setRegisterTime(DateUtil.getDate());
            relationship.setIsUsed(1);

            userDeviceRelationshipRepository.save(relationship);

        }else {
            // 3.已注册 则修改注册时间 修改启用标志
            userDeviceRelationship.setRegisterTime(DateUtil.getDate());
            userDeviceRelationship.setIsUsed(1);
            userDeviceRelationshipRepository.save(userDeviceRelationship);
        }

        map.put("code", 0);
        map.put("msg", DeviceHandleEnum.USER_DEVICE_RELATIONSHIP_HANDLE_SUCCESS.getMessage());
        return map;
    }

    @Override
    public Map<String, Object> userDeviceUnbind(String snCode, String userName) {
        Map<String,Object> map = new HashMap<String,Object>();
        // 1.查询记录
        UserDeviceRelationship userDeviceRelationship = userDeviceRelationshipRepository.findBySnCodeAndUserName(snCode, userName);
        // 2.修改启用标志 为 未启用
        userDeviceRelationship.setIsUsed(0);

        userDeviceRelationshipRepository.save(userDeviceRelationship);

        map.put("code", 0);
        map.put("msg", DeviceHandleEnum.DEVICE_UNBIND_SUCCESS.getMessage());
        return map;
    }

    @Override
    public List<DeviceInfo> listFindByUser(String userName) {


        // 1.通过 用户名 和已启用 获取用户设备启用关系列表
        List<UserDeviceRelationship> userDeviceRelationshipList = userDeviceRelationshipRepository.findByIsUsedAndUserName(1,userName);

        // 2.获取SN码查询列表
        List<String> snCodeList = userDeviceRelationshipList.stream()
                .map(e -> e.getSnCode())
                .collect(Collectors.toList());

        // 3.通过SN码查询列表获取设备基本信息列表
        return  deviceInfoRepository.findBySnCodeIn(snCodeList);

        //4.有查询结果时 再转化为对应的DTO对象 并获取状态 信息  Controller层
    }

    @Override
    public Integer getStatusBySnCode(String snCode) {

        return deviceStatusRepository.findOne(snCode).getIsOnline();
    }
}
