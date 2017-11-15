package com.cherry.controller;

import com.cherry.dataobject.ProtocolConfigDetail;
import com.cherry.enums.ProtocolEnum;
import com.cherry.exception.ProtocolException;
import com.cherry.form.ProtocolDetailForm;
import com.cherry.service.ProtocolService;
import com.cherry.util.ResultVOUtil;
import com.cherry.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 协议操作API层
 * Created by Administrator on 2017/11/15.
 */
@RestController
@RequestMapping("/protocol")
@Slf4j
public class ProtocolController {

    @Autowired
    private ProtocolService protocolService;

    /**
     * 现场用户查询设备当前启用的协议列表
     * @param snCode
     * @return
     */
    @PostMapping("/site/get")
    public ResultVO getSiteProtocol(@RequestParam("snCode") String snCode){

        List<ProtocolConfigDetail> detailList = protocolService.listFindCurrentBySnCode(snCode);
        if (detailList == null){
            log.error("未查询到相关协议！");
            throw new ProtocolException(ProtocolEnum.GET_PROTOCOL_FAIL);
        }
        if (detailList.size() == 0){
            log.error("未查询到相关协议！");
            throw new ProtocolException(ProtocolEnum.GET_PROTOCOL_FAIL);
        }

        return ResultVOUtil.success(ProtocolEnum.GET_PROTOCOL_SUCCESS.getMessage(), detailList);
    }

    /**
     * 现场用户修改设备协议
     * 是否实时显示 是否进行报警监控
     * @param form
     * @param bindingResult
     * @return
     */
    @PostMapping("/site/update")
    public ResultVO updateSiteProtocol(@Valid ProtocolDetailForm form,
                                       BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("协议信息填写错误");
            throw  new ProtocolException((ProtocolEnum.PROTOCOL_INFO_ERROR));
        }
        List<ProtocolConfigDetail> detailList = protocolService.updateProtocolDetail(form);
        if (detailList == null){
            log.error("协议修改失败！");
            throw new ProtocolException(ProtocolEnum.UPDATE_PROTOCOL_FAIL);
        }
        if (detailList.size() == 0){
            log.error("协议修改失败！");
            throw new ProtocolException(ProtocolEnum.UPDATE_PROTOCOL_FAIL);
        }

        return ResultVOUtil.success(ProtocolEnum.UPDATE_PROTOCOL_SUCCESS.getMessage(), detailList);
    }

    /**
     * 通过SN码获取当前设备启用的协议版本号
     * @param snCode
     * @return
     */
    @PostMapping("/getVersion")
    public ResultVO getProtocolVersion(@RequestParam("snCode") String snCode){

        String protocolVersion = protocolService.getProtocolVersionBySnCode(snCode);
        if (StringUtils.isEmpty(protocolVersion)){
            log.error("协议版本查询失败！");
            throw new ProtocolException(ProtocolEnum.GET_PROTOCOL_VERSION_FAIL);
        }

        return ResultVOUtil.success(ProtocolEnum.GET_PROTOCOL_VERSION_SUCCESS.getMessage(), protocolVersion);

    }


}
