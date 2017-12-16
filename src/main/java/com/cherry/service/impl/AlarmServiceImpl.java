package com.cherry.service.impl;

import com.cherry.dataobject.AlarmMessage;
import com.cherry.dataobject.AlarmRecord;
import com.cherry.dataobject.AlarmRule;
import com.cherry.dataobject.ProtocolDetail;
import com.cherry.enums.AlarmHandleEnum;
import com.cherry.form.AlarmQueryForm;
import com.cherry.form.AlarmRuleAddForm;
import com.cherry.form.AlarmUpdateForm;
import com.cherry.repository.AlarmMessageRepository;
import com.cherry.repository.AlarmRecordRepository;
import com.cherry.repository.AlarmRuleRepository;
import com.cherry.repository.ProtocolDetailRepository;
import com.cherry.service.AlarmService;
import com.cherry.util.DateUtil;
import com.cherry.vo.AlarmRecordVO;
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

        // 2.获取报警记录分页对象
        Page<AlarmRecord> recordPage = recordRepository.findBySnCodeAndProtocolVersionAndAlarmTimeBetweenOrderByIdDesc(form.getSnCode(),
                                                                                                                        form.getProtocolVersion(),
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
        map.put("data", recordVOList);

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

        // 1.查询报警规则 分页对象
        Page<AlarmRule> rulePage = alarmRuleRepository.findBySnCodeAndProtocolVersionOrderByOffsetNumberAsc(snCode, protocolVersion, pageable);


        // 2.封装为 报警规则视图对象 AlarmRuleVO

        return null;
    }

    @Override
    public Map<String, Object> getThresholdSingle(String id) {
        return null;
    }

    @Override
    public Integer updateThreshold(AlarmUpdateForm form) {
        return null;
    }

    @Override
    public Map<String, Object> getThresholdList(String protocolVersion) {
        return null;
    }

    @Override
    public Integer addThreshold(AlarmRuleAddForm form) {
        return null;
    }
}
