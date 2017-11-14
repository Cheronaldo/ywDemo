package com.cherry.controller;

import com.cherry.converter.DeviceInfo2SiteDeviceListDTOConverter;
import com.cherry.converter.DeviceInfo2SiteDeviceMapDTOConverter;
import com.cherry.dataobject.DeviceInfo;
import com.cherry.dto.SiteDeviceListDTO;
import com.cherry.dto.SiteDeviceMapDTO;
import com.cherry.enums.DeviceHandleEnum;
import com.cherry.exception.DeviceException;
import com.cherry.form.SiteDeviceForm;
import com.cherry.service.DeviceService;
import com.cherry.util.ResultVOUtil;
import com.cherry.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备操作API层
 * Created by Administrator on 2017/11/11.
 */
@RestController
@RequestMapping("/device")
@Slf4j

public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    /**
     * 校验设备是否在现场用户手中
     * 并判断用户可进行的下一步操作
     * @param snCode
     * @param checkCode
     * @param userName
     * @return
     */
    @PostMapping("/site/check")
    public ResultVO checkDeviceIsOnHand(@RequestParam("snCode") String snCode,
                                        @RequestParam("checkCode") String checkCode,
                                        @RequestParam("userName") String userName){

        Map<String,Object> map  =  deviceService.checkSiteDeviceIsOnHand(snCode, checkCode, userName);
        int code = Integer.parseInt(String.valueOf(map.get("code")));
        String msg = (String)map.get("msg");
        Object data = map.get("data");

        return ResultVOUtil.result(code, msg, data);
    }


    /**
     * 现场用户设备注册
     * 用户 设备关系记录操作
     * @param form
     * @param bindingResult
     * @return
     */
    @PostMapping("/site/register")
    public ResultVO siteSaveDeviceInfo(@Valid SiteDeviceForm form,
                                       BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("设备信息填写错误");
            throw  new DeviceException((DeviceHandleEnum.DEVICE_INFO_ERROR));
        }
        try {
            // 1.设备信息储存
            deviceService.saveSiteUserDeviceInfo(form);

            // 2.设备信息储存成功，修改用户 设备关系记录
            deviceService.saveUserDeviceRelationshipHandle(form.getSnCode(), form.getUserName());
        }catch (DeviceException e){
            log.error("注册失败");
            throw new DeviceException(DeviceHandleEnum.REGISTER_FAIL);
        }

        return ResultVOUtil.success(DeviceHandleEnum.REGISTER_SUCCESS.getMessage());
    }

    /**
     * 现场用户设备信息修改
     * @param form
     * @param bindingResult
     * @return
     */
    @PostMapping("/site/update")
    public ResultVO siteUpdateDeviceInfo(@Valid SiteDeviceForm form,
                                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("设备信息填写错误");
            throw  new DeviceException((DeviceHandleEnum.DEVICE_INFO_ERROR));
        }
        try {
            // 1.设备信息储存
            deviceService.saveSiteUserDeviceInfo(form);
        }catch (DeviceException e){
            log.error("修改失败");
            throw new DeviceException(DeviceHandleEnum.UPDATE_FAIL);
        }

        return ResultVOUtil.success(DeviceHandleEnum.UPDATE_SUCCESS.getMessage());

    }

    /**
     * 经销商 现场用户 与设备解绑（注销设备）
     * @param userName
     * @param snCode
     * @return
     */
    @PostMapping("/unbind")
    public ResultVO userDeviceUnbind(@RequestParam("userName") String userName,
                                     @RequestParam("snCode") String snCode){

        try {
            // 修改用户 设备 关系记录
            deviceService.userDeviceUnbind(snCode, userName);
        }catch (DeviceException e){
            log.error("注销失败");
            throw new DeviceException(DeviceHandleEnum.DEVICE_UNBIND_FAIL);
        }

        return ResultVOUtil.success(DeviceHandleEnum.DEVICE_UNBIND_SUCCESS.getMessage());
    }


    /**
     * 现场用户通过用户名查询 所有启用设备
     * @param userName
     * @return
     */
    @PostMapping("/site/deviceList")
    public ResultVO querySiteDeviceList(@RequestParam("userName") String userName){

        // 1.查询设备列表
       List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
        try {
            deviceInfoList = deviceService.listFindByUser(userName);
        }catch (DeviceException e){
            log.error("查询失败");
            throw new DeviceException(DeviceHandleEnum.FIND_DEVICE_LIST_FAIL);
        }

        if (deviceInfoList.size() == 0){
            // 未查询到设备信息
            return ResultVOUtil.error(1,DeviceHandleEnum.FIND_NO_DEVICE.getMessage());
        }

        // 2.转换为现场用户 设备信息DTO列表
        List<SiteDeviceListDTO> siteDeviceListDTOList = DeviceInfo2SiteDeviceListDTOConverter.convert(deviceInfoList);

        // 3.将设备状态信息 写入设备信息DTO列表
        for (SiteDeviceListDTO siteDeviceListDTO : siteDeviceListDTOList){
            siteDeviceListDTO.setIsOnline(deviceService.getStatusBySnCode(siteDeviceListDTO.getSnCode()));
            //TODO 若需要返回给前端 设备类型 则也在此处进行转换 取值
        }

        return ResultVOUtil.success(DeviceHandleEnum.FIND_DEVICE_LIST_SUCCESS.getMessage(), siteDeviceListDTOList);

    }


    /**
     * 现场用户通过用户名查询 所有启用设备
     * 用于设备列表显示
     * @param userName
     * @return
     */
    @PostMapping("/site/mapList")
    public ResultVO querySiteMapList(@RequestParam("userName") String userName){

        //1.查询设备列表
        List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
        try {
            deviceInfoList = deviceService.listFindByUser(userName);
        }catch (DeviceException e){
            log.error("查询失败");
            throw new DeviceException(DeviceHandleEnum.FIND_DEVICE_LIST_FAIL);
        }

        if (deviceInfoList.size() == 0){
            // 未查询到设备信息
            return ResultVOUtil.error(1,DeviceHandleEnum.FIND_NO_DEVICE.getMessage());
        }

        // 2.转换为现场用户 设备信息DTO列表
        List<SiteDeviceMapDTO> siteDeviceMapDTOList = DeviceInfo2SiteDeviceMapDTOConverter.convert(deviceInfoList);

        // 3.将设备状态信息 写入设备信息DTO列表
        for (SiteDeviceMapDTO siteDeviceMapDTO : siteDeviceMapDTOList){
            siteDeviceMapDTO.setIsOnline(deviceService.getStatusBySnCode(siteDeviceMapDTO.getSnCode()));
            //TODO 若需要返回给前端 设备类型 则也在此处进行转换 取值
        }

        return ResultVOUtil.success(DeviceHandleEnum.FIND_DEVICE_LIST_SUCCESS.getMessage(), siteDeviceMapDTOList);

    }


}
