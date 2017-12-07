package com.cherry.controller;

import com.cherry.converter.ProtocolConfigDetail2RealTimeProtocolVOConverter;
import com.cherry.dataobject.ProtocolConfigDetail;
import com.cherry.dto.ProtocolAdaptDTO;
import com.cherry.enums.DataHandleEnum;
import com.cherry.enums.ProtocolEnum;
import com.cherry.exception.DataException;
import com.cherry.exception.ProtocolException;
import com.cherry.form.AllDataQueryForm;
import com.cherry.form.ProtocolAdaptForm;
import com.cherry.form.SingleDataQueryForm;
import com.cherry.service.DataService;
import com.cherry.service.ProtocolService;
import com.cherry.util.ResultVOUtil;
import com.cherry.vo.HistoricalDataProtocolVO;
import com.cherry.vo.HistoricalDataVO;
import com.cherry.vo.RealTimeProtocolVO;
import com.cherry.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private DataService dataService;


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
            log.error("适配条件异常！");
            throw new ProtocolException(ProtocolEnum.ADAPT_CRITERIA_ERROR);
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

    /**
     * 获取实时数据协议详情
     * @param userName
     * @param snCode
     * @param protocolVersion
     * @return
     */
    @PostMapping("/getProtocol/realTime")
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

    /**
     * 查询单项历史数据
     * @param form
     * @param bindingResult
     * @return
     */
    @PostMapping("/getData/single")
    public ResultVO getSingleHistoricalData(@Valid SingleDataQueryForm form,
                                            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("查询条件异常！");
            throw new DataException(DataHandleEnum.QUERY_CRITERIA_ERROR);
        }

        List<HistoricalDataVO> dataVOList = dataService.listGetOne(form);

        if (dataVOList == null){
            log.error("未查询到相关数据记录！");
            throw new DataException(DataHandleEnum.GET_DATA_FAIL);
        }

        return ResultVOUtil.success(DataHandleEnum.GET_DATA_SUCCESS.getMessage(), dataVOList);
    }

    /**
     * 获取历史数据 协议详情
     * @param protocolVersion
     * @return
     */
    @PostMapping("/getProtocol/history")
    public ResultVO getHistoryDataProtocol(@RequestParam("protocolVersion") String protocolVersion){

        List<HistoricalDataProtocolVO> protocolVOList = protocolService.listFindByProtocolVersion(protocolVersion);

        if (protocolVOList.size() == 0){
            log.error("查询协议失败！");
            throw new ProtocolException(ProtocolEnum.GET_PROTOCOL_FAIL);
        }

        return ResultVOUtil.success(ProtocolEnum.GET_PROTOCOL_SUCCESS.getMessage(), protocolVOList);

    }

    /**
     * 全项历史数据查询
     * @param form
     * @param bindingResult
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/getData/all")
    public Map<String, Object> getAllHistoricalData(@Valid AllDataQueryForm form,
                                                    BindingResult bindingResult,
                                                    @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(value = "size", defaultValue = "10") Integer size){

        if (bindingResult.hasErrors()){
            log.error("查询条件异常！");
            throw new DataException(DataHandleEnum.QUERY_CRITERIA_ERROR);
        }
        // 1.封装分页查询参数
        PageRequest request = new PageRequest(page - 1, size);
        // 2.获取数据查询 分页对象
        Map<String, Object> map = dataService.listGetAll(form, request);

        if (map == null){
            log.error("未查询到相关数据记录！");
            throw new DataException(DataHandleEnum.GET_DATA_FAIL);
        }

        return map;
    }

}
