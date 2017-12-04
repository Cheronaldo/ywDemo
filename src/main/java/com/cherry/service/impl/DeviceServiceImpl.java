package com.cherry.service.impl;

import com.cherry.converter.DeviceInfo2SiteDeviceInfoDTOConverter;
import com.cherry.dataobject.DeviceInfo;
import com.cherry.dataobject.DeviceVerify;
import com.cherry.dataobject.ProtocolConfigMaster;
import com.cherry.dataobject.UserDeviceRelationship;
import com.cherry.dto.ProtocolAdaptDTO;
import com.cherry.dto.SiteDeviceInfoDTO;
import com.cherry.enums.DeviceHandleEnum;
import com.cherry.exception.DeviceException;
import com.cherry.form.SiteDeviceForm;
import com.cherry.repository.*;
import com.cherry.service.DeviceService;
import com.cherry.service.ProtocolService;
import com.cherry.util.DateUtil;
import com.cherry.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ProtocolService protocolService;

    @Override
    public Map<String, Object> checkSiteDeviceIsOnHand(String snCode, String checkCode, String userName) {

        Map<String,Object> map = new HashMap<String,Object>();

        // 1.SN码是否有对应的校验码 查询最新一条校验记录
        DeviceVerify deviceVerify = deviceVerifyRepository.findFirstBySnCodeOrderByIdDesc(snCode);
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
        // 获取SiteDeviceInfoDTO
        SiteDeviceInfoDTO siteDeviceInfoDTO = DeviceInfo2SiteDeviceInfoDTOConverter.convert(deviceInfo);
        // 4.判断是否需要进行协议请求 (由于经销商注册时会添加协议，现场注册时不必考虑协议为null)
        String currentProtocolVersion = protocolService.getProtocolVersionBySnCode(snCode);
        boolean isProtocolEquals = currentProtocolVersion.equals(deviceVerify.getProtocolVersion());
        if (isProtocolEquals){
            // 协议版本适配 不启用协议请求
            siteDeviceInfoDTO.setIsAsk(0);
        } else {
            siteDeviceInfoDTO.setIsAsk(1);
        }

        // 5.是否为现场首次注册
        if (StringUtils.isEmpty(siteDeviceInfoDTO.getDeviceAddress())){
            // 设备部署地址为空 则为首次注册
            map.put("code", 0);
            map.put("msg",DeviceHandleEnum.FIRST_REGISTER.getMessage());
            map.put("data", siteDeviceInfoDTO.getIsAsk());
            return map;
        }

        // 6.是否为该用户注册
        // 7.该用户是否已启用该设备
        UserDeviceRelationship userDeviceRelationship = userDeviceRelationshipRepository.findBySnCodeAndUserName(snCode, userName);
        boolean isUsed = (userDeviceRelationship == null) || (userDeviceRelationship.getIsUsed() == 0);
        if (isUsed){
            // 追加注册 将查询到的注册信息回传 (用户为注册 或 之前注册 目前已注销)
            map.put("code", 1);
            map.put("msg",DeviceHandleEnum.ADD_REGISTER.getMessage());
            map.put("data", siteDeviceInfoDTO);
            return map;
        }

        // 8.该用户已注册 且以启用 此时只能修改 或注销
        map.put("code", 2);
        map.put("msg",DeviceHandleEnum.ALREADY_REGISTERED.getMessage());
        map.put("data", siteDeviceInfoDTO);
        return map;


    }

    @Override
    @Transactional
    public Integer saveSiteUserDeviceInfo(SiteDeviceForm siteDeviceForm) {

        Map<String,Object> map = new HashMap<String,Object>();

        // 1.判断是否需要进行相应适配
        if (siteDeviceForm.getIsAdapt() == 1){
            ProtocolAdaptDTO adaptDTO = new ProtocolAdaptDTO();
            BeanUtils.copyProperties(siteDeviceForm, adaptDTO);
            int adaptResult = protocolService.protocolAdapt(adaptDTO);
            if (adaptResult == 1){
                // adaptResult 为 1时 说明协议适配失败
                return 1;
            }

        }

        // 2.查询设备信息记录  修改记录时 一定要先查 再修改 最后储存修改后的结果
        DeviceInfo deviceInfo =deviceInfoRepository.findOne(siteDeviceForm.getSnCode());

        // 3.将siteDeviceInfoDTO对象属性赋给设备信息对象
        BeanUtils.copyProperties(siteDeviceForm, deviceInfo);
        deviceInfoRepository.save(deviceInfo);

        return 0;
    }

    @Override
    @Transactional
    public Integer siteUserDeviceRegister(SiteDeviceForm siteDeviceForm) {
        // 1.进行协议适配 设备信息储存
        int saveResult = deviceService.saveSiteUserDeviceInfo(siteDeviceForm);
        if (saveResult == 1){
            // 协议适配或信息储存失败
            return 1;
        }
        // 2.操作用户与设备关系
        // 2.1 查询用户是否已注册过该设备
        UserDeviceRelationship userDeviceRelationship = userDeviceRelationshipRepository.findBySnCodeAndUserName(siteDeviceForm.getSnCode(), siteDeviceForm.getUserName());
        if (userDeviceRelationship == null){
            // 2.2 没有 则添加用户 设备关系记录
            UserDeviceRelationship relationship = new UserDeviceRelationship();
            relationship.setId(KeyUtil.genUniqueKey());
            relationship.setSnCode(siteDeviceForm.getSnCode());
            relationship.setUserName(siteDeviceForm.getUserName());
            relationship.setRegisterTime(DateUtil.getDate());
            relationship.setIsUsed(1);

            userDeviceRelationshipRepository.save(relationship);
            return 0;

        }
        // 2.3 已注册 则修改注册时间 修改启用标志
        userDeviceRelationship.setRegisterTime(DateUtil.getDate());
        userDeviceRelationship.setIsUsed(1);
        userDeviceRelationshipRepository.save(userDeviceRelationship);

        return 0;

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
        List<UserDeviceRelationship> userDeviceRelationshipList = userDeviceRelationshipRepository.findByUserNameAndIsUsed(userName, 1);

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
