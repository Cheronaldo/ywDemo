package com.cherry.controller;

import com.cherry.converter.ProtocolConfigDetail2RealTimeProtocolVOConverter;
import com.cherry.dataobject.ProtocolConfigDetail;
import com.cherry.dto.ProtocolAdaptDTO;
import com.cherry.enums.ProtocolEnum;
import com.cherry.exception.ProtocolException;
import com.cherry.form.ProtocolAdaptForm;
import com.cherry.service.ProtocolService;
import com.cherry.util.ResultVOUtil;
import com.cherry.vo.RealTimeProtocolVO;
import com.cherry.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
 * 数据处理API层
 * Created by Administrator on 2017/11/24.
 */
@RestController
@RequestMapping("/data")
@Slf4j
public class DataController {

    @Autowired
    private ProtocolService protocolService;


    /**
     * 实时数据显示 协议版本查询
     * @param snCode
     * @return
     */
    @PostMapping("/getProtocolVersion")
    public ResultVO getRealTimeProtocolVersion (@RequestParam("snCode") String snCode){

        String protocolVersion = protocolService.getProtocolVersionBySnCode(snCode);
        if (StringUtils.isEmpty(protocolVersion)){
            log.error("协议版本查询失败！");
            throw new ProtocolException(ProtocolEnum.GET_PROTOCOL_VERSION_FAIL);
        }

        return ResultVOUtil.success(ProtocolEnum.GET_PROTOCOL_VERSION_SUCCESS.getMessage(), protocolVersion);

    }

    /**
     * 实时数据显示 协议适配
     * @param form
     * @param bindingResult
     * @return
     */
    @PostMapping("/protocolAdapt")
    public ResultVO realTimeProtocolAdapt (@Valid ProtocolAdaptForm form,
                                           BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("协议版本查询失败！");
            throw new ProtocolException(ProtocolEnum.GET_PROTOCOL_VERSION_FAIL);
        }
        // 1.获取ProtocolAdaptDTO对象
        ProtocolAdaptDTO adaptDTO = new ProtocolAdaptDTO();
        BeanUtils.copyProperties(form, adaptDTO);

        // 2.协议适配
        int adaptResult = protocolService.protocolAdapt(adaptDTO);
        if (adaptResult == 1){
            log.error("协议适配失败！");
            throw new ProtocolException(ProtocolEnum.PROTOCOL_ADAPT_FAIL);
        }

        return ResultVOUtil.success(ProtocolEnum.PROTOCOL_ADAPT_SUCCESS.getMessage());
    }

    @PostMapping("/getProtocol")
    public ResultVO getRealTimeProtocol(@RequestParam("userName") String userName,
                                        @RequestParam("snCode") String snCode,
                                        @RequestParam("protocolVersion") String protocolVersion){

        // 1.查询需要显示的协议详情列表
        List<RealTimeProtocolVO> protocolVOList = protocolService.listForRealTimeDisplay(userName, snCode, protocolVersion);

        if(protocolVOList.size() == 0){
            log.error("协议查询失败！");
            throw new ProtocolException(ProtocolEnum.GET_PROTOCOL_FAIL);
        }

       return ResultVOUtil.success(ProtocolEnum.GET_PROTOCOL_SUCCESS.getMessage(), protocolVOList);

    }


}
