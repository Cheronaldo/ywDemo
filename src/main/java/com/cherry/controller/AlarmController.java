package com.cherry.controller;

import com.cherry.enums.AlarmHandleEnum;
import com.cherry.form.AlarmQueryForm;
import com.cherry.form.AlarmUpdateForm;
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
 * 设备报警API层
 * Created by Administrator on 2017/12/07.
 */
@RestController
@RequestMapping("/alarm")
@Slf4j
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    /**
     * 查询设备报警记录
     * 分页
     * @param form
     * @param bindingResult
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/getList")
    public Map<String, Object> getRecord(@Valid AlarmQueryForm form,
                                         BindingResult bindingResult,
                                         @RequestParam(value = "page", defaultValue = "1")  Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size){

        Map<String, Object> map = new HashMap<>();
        // 1.验证查询参数是否合法
        if (bindingResult.hasErrors()){
            map.put("code", 1);
            map.put("msg", AlarmHandleEnum.QUERY_CRITERIA_ERROR.getMessage());
            return map;
        }
        // 2.封装分页查询参数
        PageRequest request = new PageRequest(page - 1, size);
        // 3.查询报警记录
        map = alarmService.getRecordList(form, request);

        return map;
    }

    /**
     * 修改报警记录
     * 分页
     * @param form
     * @param bindingResult
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/updateRecord")
    public Map<String, Object> updateRecord(@Valid AlarmUpdateForm form,
                                            BindingResult bindingResult,
                                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = "10") Integer size){
        Map<String, Object> map = new HashMap<>();
        // 1.验证查询参数是否合法
        if (bindingResult.hasErrors()){
            map.put("code", 1);
            map.put("msg", AlarmHandleEnum.UPDATE_CRITERIA_ERROR.getMessage());
            return map;
        }
        // 2.封装分页查询参数
        PageRequest request = new PageRequest(page - 1, size);
        // 3.查询报警记录
        map = alarmService.updateRecord(form, request);

        boolean isSuccess = Integer.parseInt(String.valueOf(map.get("code"))) == 0;
        if (isSuccess){
            map.remove("msg");
            map.put("msg", AlarmHandleEnum.UPDATE_SUCCESS.getMessage());
        }
        return map;

    }

}
