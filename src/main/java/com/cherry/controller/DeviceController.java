package com.cherry.controller;

import com.cherry.converter.DeviceInfo2SiteDeviceListDTOConverter;
import com.cherry.converter.DeviceInfo2SiteDeviceMapDTOConverter;
import com.cherry.dataobject.DeviceInfo;
import com.cherry.dataobject.DeviceStatus;
import com.cherry.dto.ProtocolAdaptDTO;
import com.cherry.dto.SiteDeviceInfoDTO;
import com.cherry.dto.SiteDeviceListDTO;
import com.cherry.dto.SiteDeviceMapDTO;
import com.cherry.enums.DeviceHandleEnum;
import com.cherry.exception.DeviceException;
import com.cherry.form.SiteDeviceForm;
import com.cherry.repository.DeviceInfoRepository;
import com.cherry.repository.DeviceStatusRepository;
import com.cherry.service.DeviceService;
import com.cherry.service.ProtocolService;
import com.cherry.util.DateUtil;
import com.cherry.util.KeyUtil;
import com.cherry.util.ResultVOUtil;
import com.cherry.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private DeviceInfoRepository deviceInfoRepository;

    @Autowired
    private DeviceStatusRepository deviceStatusRepository;
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ProtocolService protocolService;

    /**
     * 模拟经销商注册设备
     * 用于测试
     * @param snCode
     * @return
     */
    @PostMapping("/addRegister")
    public ResultVO addAgencyRegister(@RequestParam("snCode") String snCode){

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setSnCode(snCode);
        deviceInfo.setDeviceMac(KeyUtil.genUniqueKey());
        deviceInfo.setDeviceType(1);
        deviceInfo.setDeviceModel("HMI0275");
        deviceInfo.setShipmentNumber(KeyUtil.genUniqueKey());
        deviceInfo.setResearchUnit("亿维自动化");

        deviceInfoRepository.save(deviceInfo);

        DeviceStatus deviceStatus = new DeviceStatus();
        deviceStatus.setSnCode(snCode);
        deviceStatus.setIsOnline(1);
        deviceStatus.setHeartTime(DateUtil.getDate());

        deviceStatusRepository.save(deviceStatus);

        return ResultVOUtil.success("添加成功！");


    }


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
        int registerResult = 1;
        try {
            // 进行协议适配 信息储存 关系操作
           registerResult = deviceService.siteUserDeviceRegister(form);
        }catch (DeviceException e){
            log.error("注册失败");
            throw new DeviceException(DeviceHandleEnum.REGISTER_FAIL);
        }

        if (registerResult == 1){
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
        int registerResult = 1;
        try {
            // 进行协议适配 信息储存
            registerResult = deviceService.saveSiteUserDeviceInfo(form);
        }catch (DeviceException e){
            log.error("修改失败");
            throw new DeviceException(DeviceHandleEnum.UPDATE_FAIL);
        }
        if (registerResult == 1){
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
            // TODO 改进：通过sn list查询到对应设备状态记录的list 再进行取值处理，一般不在for循环中操作数据库
            siteDeviceListDTO.setIsOnline(deviceService.getStatusBySnCode(siteDeviceListDTO.getSnCode()));
            //TODO 若需要返回给前端 设备类型 则也在此处进行转换 取值
        }

        return ResultVOUtil.success(DeviceHandleEnum.FIND_DEVICE_LIST_SUCCESS.getMessage(), siteDeviceListDTOList);

    }

    /**
     * 获取用户设备列表
     * 网页接口
     * @param userName
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/getPage")
    public Map<String, Object> pageGetByUser(@RequestParam("userName") String userName,
                                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                                             @RequestParam(value = "size", defaultValue = "4") Integer size){

        // 1.封装分页查询条件
        PageRequest request = new PageRequest(page - 1, size);

        // 2.返回数据
        return deviceService.pageGetByUser(userName, request);

    }


    /**
     * 现场用户通过用户名查询 所有启用设备
     * 用于设备列表显示
     * @param userName
     * @return
     */
    @PostMapping("/site/mapList")
    public ResultVO querySiteMapList(@RequestParam("userName") String userName,
                                     HttpServletResponse response){

        //1.查询设备列表
        List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();

        response.setHeader("Access-Control-Allow-Origin","*");

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


    /**
     * 获取现场用户所有设备列表
     * 分页
     * @param userName
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/site/getAll")
    public Map<String, Object> pageSiteGet(@RequestParam("userName") String userName,
                                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size){

        // 1.封装分页参数
        PageRequest request = new PageRequest(page - 1, size);
        // 2.返回分页结果

        return deviceService.pageSiteGet(userName, request);

    }

    /**
     * 获取经销商名下 且现场用户未绑定的设备列表 分页
     * @param agencyName
     * @param siteName
     * @return
     */
    @PostMapping("/agency/getAll")
    public Map<String, Object> pageAgencyGet(@RequestParam("agencyName") String agencyName,
                                             @RequestParam("siteName") String siteName){

        // 1.返回分页结果

        return deviceService.listAgencyGet(agencyName, siteName);
    }

    /**
     * 现场用户与设备绑定
     * @param userName
     * @param snCode
     * @return
     */
    @PostMapping("/site/bindDevice")
    public ResultVO siteDeviceBind(@RequestParam("userName") String userName,
                                   @RequestParam("snCode") String snCode){

        int result = deviceService.siteDeviceBind(userName, snCode);

        if (result ==  1){
            log.error("设备绑定失败！");
            return ResultVOUtil.error(1, DeviceHandleEnum.DEVICE_BIND_FAIL.getMessage());
        }

        return ResultVOUtil.success(DeviceHandleEnum.DEVICE_BIND_SUCCESS.getMessage());
    }

    /**
     * 现场用户注销设备
     * @param userName
     * @param snCode
     * @return
     */
    @PostMapping("/site/unbind")
    public ResultVO siteDeviceUnbind(@RequestParam("userName") String userName,
                                     @RequestParam("snCode") String snCode){

        int result = deviceService.siteDeviceUnbind(userName, snCode);

        if (result == 1){
            log.error("设备注销失败！");
            return ResultVOUtil.error(1, DeviceHandleEnum.DEVICE_UNBIND_FAIL.getMessage());
        }

        return ResultVOUtil.success(DeviceHandleEnum.DEVICE_UNBIND_SUCCESS.getMessage());
    }

}
