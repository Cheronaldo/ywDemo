package com.cherry.controller;

import com.cherry.converter.DeviceInfo2SiteDeviceMapDTOConverter;
import com.cherry.dataobject.DeviceInfo;
import com.cherry.dto.SiteDeviceMapDTO;
import com.cherry.enums.DeviceHandleEnum;
import com.cherry.enums.ProtocolEnum;
import com.cherry.enums.UserEnum;
import com.cherry.exception.DeviceException;
import com.cherry.exception.ProtocolException;
import com.cherry.service.DeviceService;
import com.cherry.service.ProtocolService;
import com.cherry.service.UserInfoService;
import com.cherry.util.ResultVOUtil;
import com.cherry.vo.DataReadWriteProtocolVO;
import com.cherry.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * APP接口
 * Created by Administrator on 2018/01/24.
 */
@RestController
@RequestMapping("/app")
@Slf4j
public class AppController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ProtocolService protocolService;

    /**
     * 用户登录
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/login")
    public ResultVO userLogin(HttpServletRequest request, HttpServletResponse response){
        // 1.验证是否是否有效
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");
        int result = userInfoService.userLogin(userName, userPassword);

        response.setHeader("Access-Control-Allow-Origin","*");

        if (result == 1){
            return ResultVOUtil.error(1, UserEnum.USER_LOGIN_FAIL.getMessage());
        }

        // 2.返回登录结果
        return ResultVOUtil.success(UserEnum.USER_LOGIN_SUCCESS.getMessage());
    }

    /**
     * 获取设备启用协议版本号
     * @param snCode
     * @param response
     * @return
     */
    @PostMapping("/getProtocolVersion")
    public ResultVO getProtocolVersion (@RequestParam("snCode") String snCode,
                                                HttpServletResponse response){

        response.setHeader("Access-Control-Allow-Origin","*");

        String protocolVersion = protocolService.getProtocolVersionBySnCode(snCode);
        if (StringUtils.isEmpty(protocolVersion)){
            log.error("协议版本查询失败！");
            throw new ProtocolException(ProtocolEnum.GET_PROTOCOL_VERSION_FAIL);
        }

        return ResultVOUtil.success(ProtocolEnum.GET_PROTOCOL_VERSION_SUCCESS.getMessage(), protocolVersion);

    }

    /**
     * 获取设备列表
     * @param userName
     * @param response
     * @return
     */
    @PostMapping("/deviceList")
    public ResultVO querySiteMapList(@RequestParam("userName") String userName,
                                     HttpServletResponse response){

        response.setHeader("Access-Control-Allow-Origin","*");
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

    /**
     * 获取数据读写协议
     * @param protocolVersion
     * @param response
     * @return
     */
    @PostMapping("/readWrite")
    public ResultVO getDataReadWriteProtocol(@RequestParam("protocolVersion") String protocolVersion,
                                                HttpServletResponse response){

        response.setHeader("Access-Control-Allow-Origin","*");

        List<DataReadWriteProtocolVO> readWriteProtocolVOList = protocolService.listForDataReadWrite(protocolVersion);

        if (readWriteProtocolVOList == null){
            log.error("未查询到相关协议！");
            throw new ProtocolException(ProtocolEnum.GET_PROTOCOL_FAIL);
        }

        return ResultVOUtil.success(ProtocolEnum.GET_PROTOCOL_SUCCESS.getMessage(), readWriteProtocolVOList);

    }

}
