package com.cherry.controller;

import com.cherry.enums.AlarmHandleEnum;
import com.cherry.form.AlarmRuleAddForm;
import com.cherry.form.AlarmRuleUpdateForm;
import com.cherry.service.AlarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 设备阈值操作API
 * Created by Administrator on 2017/12/18.
 */
@RestController
@RequestMapping("/threshold")
@Slf4j
public class ThresholdController {

    @Autowired
    private AlarmService alarmService;

    /**
     * 查询阈值列表
     * @param snCode
     * @param protocolVersion
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/getList")
    public Map<String, Object> getThresholdList(@RequestParam("snCode") String snCode,
                                                @RequestParam("protocolVersion") String protocolVersion,
                                                @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                @RequestParam(value = "size", defaultValue = "10") Integer size){

        // 1.封装分页参数
        PageRequest request = new PageRequest(page - 1, size);
        // 2.返回结果
        return alarmService.getThresholdList(snCode, protocolVersion, request);

    }

    /**
     * 获取单个子项 阈值设置的 上、下 限
     * @param protocolVersion
     * @param offsetNumber
     * @return
     */
    @RequestMapping("/getSingle")
    public Map<String, Object> getThresholdSingle(@RequestParam("protocolVersion") String protocolVersion,
                                                  @RequestParam("offsetNumber") Integer offsetNumber){

        return alarmService.getThresholdSingle(protocolVersion, offsetNumber);

    }

    /**
     * 阈值修改
     * @param form
     * @param bindingResult
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("update")
    public Map<String, Object> updateThreshold(@Valid AlarmRuleUpdateForm form,
                                               BindingResult bindingResult,
                                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "size", defaultValue = "10") Integer size){

        Map<String, Object> map = new HashMap<>();
        // 1.验证查询参数是否合法
        if (bindingResult.hasErrors()){
            log.error("阈值修改参数错误！");
            map.put("code", 1);
            map.put("msg", AlarmHandleEnum.UPDATE_CRITERIA_ERROR.getMessage());
            return map;
        }
        // 2.封装分页参数
        PageRequest request = new PageRequest(page - 1, size);

        // 3.返回结果
        return alarmService.updateThreshold(form, request);
    }


    /**
     * 获取阈值设置 上、下限 列表
     * 阈值添加
     * @param protocolVersion
     * @return
     */
    @RequestMapping("/getAll")
    public Map<String, Object> getThresholdAll(@RequestParam("protocolVersion") String protocolVersion){

        return alarmService.getThresholdLimitList(protocolVersion);

    }

    /**
     * 阈值添加
     * @param form
     * @param bindingResult
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/add")
    public Map<String, Object> addThreshold(@Valid AlarmRuleAddForm form,
                                            BindingResult bindingResult,
                                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = "10") Integer size){

        Map<String, Object> map = new HashMap<>();
        // 1.验证查询参数是否合法
        if (bindingResult.hasErrors()){
            log.error("阈值添加参数错误！");
            map.put("code", 1);
            map.put("msg", AlarmHandleEnum.ADD_CRITERIA_ERROR.getMessage());
            return map;
        }
        // 2.封装分页参数
        PageRequest request = new PageRequest(page - 1, size);

        // 3.返回结果
        return alarmService.addThreshold(form, request);
    }

}
