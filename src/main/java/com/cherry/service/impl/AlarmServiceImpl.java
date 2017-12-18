package com.cherry.service.impl;

import com.cherry.converter.AlarmRule2AlarmRuleVOConverter;
import com.cherry.converter.ProtocolDetail2AlarmThresholdVOConverter;
import com.cherry.dataobject.AlarmMessage;
import com.cherry.dataobject.AlarmRecord;
import com.cherry.dataobject.AlarmRule;
import com.cherry.dataobject.ProtocolDetail;
import com.cherry.enums.AlarmHandleEnum;
import com.cherry.form.AlarmQueryForm;
import com.cherry.form.AlarmRuleAddForm;
import com.cherry.form.AlarmRuleUpdateForm;
import com.cherry.form.AlarmUpdateForm;
import com.cherry.repository.AlarmMessageRepository;
import com.cherry.repository.AlarmRecordRepository;
import com.cherry.repository.AlarmRuleRepository;
import com.cherry.repository.ProtocolDetailRepository;
import com.cherry.service.AlarmService;
import com.cherry.util.DateUtil;
import com.cherry.util.KeyUtil;
import com.cherry.vo.AlarmRecordVO;
import com.cherry.vo.AlarmRuleVO;
import com.cherry.vo.AlarmThresholdVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 设备报警service层实现
 * Created by Administrator on 2017/12/07.
 */
@Service
public class AlarmServiceImpl implements AlarmService{

    @Autowired
    private AlarmRecordRepository recordRepository;

    @Autowired
    private AlarmMessageRepository messageRepository;

    @Autowired
    private ProtocolDetailRepository detailRepository;

    @Autowired
    private AlarmRuleRepository alarmRuleRepository;

    @Autowired
    private AlarmService alarmService;

    @Override
    public Map<String, Object> getRecordList(AlarmQueryForm form, Pageable pageable) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 1.判断页面时间参数是否为 null
        Date oldDate = DateUtil.oldDateHandle(form.getOldDate());
        Date newDate = DateUtil.newDateHandle(form.getNewDate());

        // 判断是否只查询 未解除报警
        String alarmHandled = "";
        if (form.getAlarmHandled()){
            alarmHandled = "无";
        }

        // 2.获取报警记录分页对象
        Page<AlarmRecord> recordPage = recordRepository.findBySnCodeAndProtocolVersionAndHandleResultStartingWithAndAlarmTimeBetweenOrderByIdDesc(form.getSnCode(),
                                                                                                                        form.getProtocolVersion(),
                                                                                                                        alarmHandled,
                                                                                                                        oldDate,
                                                                                                                        newDate,
                                                                                                                        pageable);

        if (recordPage.getTotalPages() == 0){
            // 未查询到相关记录
            map.put("code", 1);
            map.put("mag", AlarmHandleEnum.GET_RECORD_FAIL.getMessage());

            return map;
        }


        // 3.获取协议详情列表 用于获取本机数据名称
        List<ProtocolDetail> detailList = detailRepository.findListByProtocolVersion(form.getProtocolVersion());

        // 4.获取报警详情列表 用于获取报警详情
        List<AlarmMessage> messageList = messageRepository.findAll();

        // 5.封装为 AlarmRecordVO 对象
        List<AlarmRecord> recordList = recordPage.getContent();

        List<AlarmRecordVO> recordVOList = new ArrayList<>();
        for (AlarmRecord record : recordList){

            AlarmRecordVO recordVO = new AlarmRecordVO();
            // 从record 获取 id 实际值 处理时间 处理结果
            BeanUtils.copyProperties(record, recordVO);
            // 处理recordVO 报警时间 数据名称 报警详情 处理状态
            // 报警时间
            recordVO.setAlarmTime(DateUtil.convertDate2String(record.getAlarmTime()));

            // 获取数据名称
            for (ProtocolDetail protocolDetail : detailList ){
                if (protocolDetail.getOffsetNumber() == record.getOffsetNumber()){
                    recordVO.setDataName(protocolDetail.getDataName());
                }
            }

            // 获取报警详情
            for (AlarmMessage alarmMessage : messageList){
                if (alarmMessage.getAlarmCode() == record.getAlarmCode()){
                    recordVO.setAlarmInfo(alarmMessage.getAlarmInfo());
                }
            }

            // 处理状态
            if (record.getHandleStatus() == 0){
                recordVO.setHandleStatus(AlarmHandleEnum.ALARM_UNHANDLED.getMessage());
            }
            if (record.getHandleStatus() == 1){
                recordVO.setHandleStatus(AlarmHandleEnum.ALARM_HANDLED.getMessage());
            }

            recordVOList.add(recordVO);

        }

        map.put("code", 0);
        map.put("msg", AlarmHandleEnum.GET_RECORD_SUCCESS.getMessage());
        map.put("total", recordPage.getTotalPages());
        map.put("records", recordPage.getTotalElements());
        map.put("rows", recordVOList);

        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> updateRecord(AlarmUpdateForm form, Pageable pageable) {

        // 1.获取记录
        AlarmRecord updateRecord = recordRepository.findOne(form.getId());

        // 2.修改记录并保存
        boolean isHandled = form.getHandleStatus().equals(AlarmHandleEnum.ALARM_HANDLED.getMessage());
        if (isHandled){
            updateRecord.setHandleStatus(1);
            updateRecord.setHandleTime(DateUtil.convertDate2String(DateUtil.getDate()));
        }else {
            updateRecord.setHandleStatus(0);
        }
        updateRecord.setHandleResult(form.getHandleResult());
        recordRepository.save(updateRecord);

        // 3.查询分页对象
        AlarmQueryForm queryForm = new AlarmQueryForm();
        BeanUtils.copyProperties(form, queryForm);

        return alarmService.getRecordList(queryForm, pageable);


    }

    @Override
    public Map<String, Object> getThresholdList(String snCode, String protocolVersion, Pageable pageable) {

        Map<String, Object> map = new HashMap<>();

        // 1.查询报警规则 分页对象

        Page<AlarmRule> rulePage  = alarmRuleRepository.findBySnCodeAndProtocolVersionOrderByOffsetNumberAsc(snCode, protocolVersion, pageable);

        if (rulePage.getContent() == null){
            map.put("code", 1);
            map.put("msg", AlarmHandleEnum.GET_THRESHOLD_FAIL.getMessage());

            return map;
        }
        // 2.封装为 报警规则视图对象 AlarmRuleVO
        List<AlarmRuleVO> alarmRuleVOList = AlarmRule2AlarmRuleVOConverter.convert(rulePage.getContent());

        List<ProtocolDetail> detailList = detailRepository.findListByProtocolVersion(protocolVersion);
        // 3.获取 数据名称
        for (AlarmRuleVO alarmRuleVO : alarmRuleVOList){

            for (ProtocolDetail protocolDetail : detailList){
                if (alarmRuleVO.getOffsetNumber() == protocolDetail.getOffsetNumber()){
                    alarmRuleVO.setDataName(protocolDetail.getDataName());
                }
            }
        }

        map.put("code", 0);
        map.put("msg", AlarmHandleEnum.GET_THRESHOLD_SUCCESS.getMessage());
        map.put("total", rulePage.getTotalPages());
        map.put("records", rulePage.getTotalElements());
        map.put("data", alarmRuleVOList);

        return map;
    }

    @Override
    public Map<String, Object> getThresholdSingle(String protocolVersion, Integer offsetNumber) {

        Map<String, Object> map = new HashMap<>();
        // 1.查询相关协议详情记录
        ProtocolDetail protocolDetail = detailRepository.findByProtocolVersionAndOffsetNumber(protocolVersion, offsetNumber);

        if (protocolDetail == null){
            map.put("code", 1);
            map.put("msg", AlarmHandleEnum.GET_THRESHOLD_FAIL.getMessage());

            return map;
        }

        // 2.返回 最大限 最小限
        map.put("code", 0);
        map.put("msg", AlarmHandleEnum.GET_THRESHOLD_SUCCESS.getMessage());
        map.put("minThreshold", protocolDetail.getMinThreshold());
        map.put("maxThreshold", protocolDetail.getMaxThreshold());

        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> updateThreshold(AlarmRuleUpdateForm form, Pageable pageable) {

        Map<String, Object> map = new HashMap<>();
        // 1.通过ID获取记录
        AlarmRule alarmRuleOld = alarmRuleRepository.findOne(form.getId());
        if (alarmRuleOld == null){
            map.put("code", 1);
            map.put("msg", AlarmHandleEnum.UPDATE_THRESHOLD_FAIL.getMessage());
            return map;
        }
        // 2.判断要修改的记录是否存在
        AlarmRule alarmRuleNew = alarmRuleRepository.findBySnCodeAndProtocolVersionAndOffsetNumberAndDownThresholdAndUpThreshold(alarmRuleOld.getSnCode(),
                alarmRuleOld.getProtocolVersion(),
                alarmRuleOld.getOffsetNumber(),
                form.getDownThreshold(),
                form.getUpThreshold());

        if (alarmRuleNew != null){
            // 记录已存在
            map.put("code", 1);
            map.put("msg",AlarmHandleEnum.THRESHOLD_RECORD_EXIST.getMessage());

            return map;
        }

        // 3.修改记录
        alarmRuleOld.setDownThreshold(form.getDownThreshold());
        alarmRuleOld.setUpThreshold(form.getUpThreshold());

        alarmRuleRepository.save(alarmRuleOld);

        // 4.查询阈值列表
        map = alarmService.getThresholdList(alarmRuleOld.getSnCode(),
                                            alarmRuleOld.getProtocolVersion(),
                                            pageable);
        // 修改返回信息
        map.remove("msg");
        if (map.get("data") != null){
            map.put("msg", AlarmHandleEnum.UPDATE_THRESHOLD_SUCCESS.getMessage());

        }else {
            map.put("msg", AlarmHandleEnum.UPDATE_THRESHOLD_FAIL.getMessage());
        }
        return map;
    }

    @Override
    public Map<String, Object> getThresholdLimitList(String protocolVersion) {

        Map<String, Object> map = new HashMap<>();
        // 1.查询协议祥表 获取阈值记录列表
        List<ProtocolDetail> detailList = detailRepository.findListByProtocolVersion(protocolVersion);

        if (detailList == null){
            map.put("code", 1);
            map.put("msg", AlarmHandleEnum.ADD_THRESHOLD_ENABLE.getMessage());

            return map;
        }

        // 2.封装为 AlarmThresholdVo 列表
        List<AlarmThresholdVO> thresholdVOList = ProtocolDetail2AlarmThresholdVOConverter.convert(detailList);

        map.put("code", 0);
        map.put("msg", AlarmHandleEnum.GET_THRESHOLD_SUCCESS.getMessage());
        map.put("data", thresholdVOList);

        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> addThreshold(AlarmRuleAddForm form, Pageable pageable) {

        Map<String, Object> map = new HashMap<>();
        // 1.添加记录
        // 2.注意判断记录是否已存在
        AlarmRule alarmRuleOld = alarmRuleRepository.findBySnCodeAndProtocolVersionAndOffsetNumberAndDownThresholdAndUpThreshold(form.getSnCode(),
                form.getProtocolVersion(),
                form.getOffsetNumber(),
                form.getDownThreshold(),
                form.getUpThreshold());

        if (alarmRuleOld != null){
            // 记录已存在
            map.put("code", 1);
            map.put("msg",AlarmHandleEnum.THRESHOLD_RECORD_EXIST.getMessage());

            return map;
        }

        AlarmRule alarmRuleAdd = new AlarmRule();
        BeanUtils.copyProperties(form, alarmRuleAdd);
        alarmRuleAdd.setId(KeyUtil.genUniqueKey());
        try {
            alarmRuleRepository.save(alarmRuleAdd);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", AlarmHandleEnum.THRESHOLD_RECORD_EXIST.getMessage());

            return map;
        }
        // 3.查询阈值列表
        map = alarmService.getThresholdList(form.getSnCode(),
                                            form.getProtocolVersion(),
                                            pageable);
        map.remove("msg");
        if (map.get("data") != null){
            map.put("msg", AlarmHandleEnum.ADD_THRESHOLD_SUCCESS.getMessage());
        }else {
            map.put("msg", AlarmHandleEnum.ADD_THRESHOLD_FAIL.getMessage());
        }

        return map;
    }
}
