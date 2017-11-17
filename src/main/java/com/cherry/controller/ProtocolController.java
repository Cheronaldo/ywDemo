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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 分页
     * @param snCode
     * @return
     */
    @PostMapping("/site/get")
    public Map<String, Object> getSiteProtocol(@RequestParam("snCode") String snCode,
                                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "rows", defaultValue = "10") Integer rows){

        Map<String, Object> map = new HashMap<String, Object>();
        // 1.封装查询参数
        PageRequest request = new PageRequest(page - 1, rows);

        // 2.获取分页查询对象
        Page<ProtocolConfigDetail> configDetailPage = protocolService.listFindCurrentBySnCode(snCode, request);
        if (configDetailPage == null){
            // TODO 查询异常也要返回对应的map?
            log.error("未查询到相关协议！");
            throw new ProtocolException(ProtocolEnum.GET_PROTOCOL_FAIL);
        }
        if (configDetailPage.getSize() == 0){
            log.error("未查询到相关协议！");
            throw new ProtocolException(ProtocolEnum.GET_PROTOCOL_FAIL);
        }

        map.put("total", configDetailPage.getTotalPages());
        map.put("records", configDetailPage.getTotalElements());
        map.put("rows", configDetailPage.getContent());

        return map;
       // return ResultVOUtil.success(ProtocolEnum.GET_PROTOCOL_SUCCESS.getMessage(), detailList);
    }

    /**
     * 现场用户修改设备协议
     * 是否实时显示 是否进行报警监控
     * 分页
     * @param form
     * @param bindingResult
     * @return
     */
    @PostMapping("/site/update")
    public Map<String, Object> updateSiteProtocol(@Valid ProtocolDetailForm form,
                                       BindingResult bindingResult,
                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "rows", defaultValue = "10") Integer rows){
        if (bindingResult.hasErrors()){
            log.error("协议信息填写错误");
            // TODO 查询异常也要返回对应的map?
            throw  new ProtocolException((ProtocolEnum.PROTOCOL_INFO_ERROR));
        }

        Map<String, Object> map = new HashMap<String, Object>();
        // 1.封装查询参数
        PageRequest request = new PageRequest(page - 1, rows);
        // 2.获取分页查询对象
        Page<ProtocolConfigDetail> configDetailPage = protocolService.updateProtocolDetail(form, request);
        if (configDetailPage == null){
            log.error("协议修改失败！");
            throw new ProtocolException(ProtocolEnum.UPDATE_PROTOCOL_FAIL);
        }
        if (configDetailPage.getSize() == 0){
            log.error("协议修改失败！");
            throw new ProtocolException(ProtocolEnum.UPDATE_PROTOCOL_FAIL);
        }

        map.put("total", configDetailPage.getTotalPages());
        map.put("records", configDetailPage.getTotalElements());
        map.put("rows", configDetailPage.getContent());

        return map;
        //return ResultVOUtil.success(ProtocolEnum.UPDATE_PROTOCOL_SUCCESS.getMessage(), detailList);
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
