package com.cherry.service.impl;

import com.cherry.converter.ProtocolConfigDetail2RealTimeProtocolVOConverter;
import com.cherry.converter.ProtocolDetail2HistoricalDataProtocolVO;
import com.cherry.converter.ProtocolDetail2ProtocolDetailVO;
import com.cherry.converter.ProtocolDetail2RealTimeProtocolVO;
import com.cherry.dataobject.*;
import com.cherry.dto.ProtocolAdaptDTO;
import com.cherry.dto.ProtocolStrategyDTO;
import com.cherry.enums.DataHandleEnum;
import com.cherry.enums.ProtocolEnum;
import com.cherry.exception.ProtocolException;
import com.cherry.form.ProtocolDetailForm;
import com.cherry.form.ProtocolQueryForm;
import com.cherry.repository.*;
import com.cherry.service.ProtocolService;
import com.cherry.util.DateUtil;
import com.cherry.util.KeyUtil;
import com.cherry.util.StringUtil;
import com.cherry.vo.HistoricalDataProtocolVO;
import com.cherry.vo.ProtocolDetailVO;
import com.cherry.vo.RealTimeProtocolVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/15.
 */
@Service
@Slf4j
public class ProtocolServiceImpl implements ProtocolService{

    @Autowired
    private ProtocolDetailRepository detailRepository;

    @Autowired
    private DeviceProtocolRelationshipRepository deviceProtocolRelationshipRepository;

    @Autowired
    private AlarmStrategyRepository alarmStrategyRepository;

    @Autowired
    private VisibleStrategyRepository visibleStrategyRepository;

    @Autowired
    private ProtocolService protocolService;

    @Override
    @Transactional
    public Map<String,Object> listFindCurrentBySnCode(ProtocolQueryForm queryForm, Pageable pageable) {

        Map<String,Object> map = new HashMap<String,Object>();

        // 1.判断是否需要进行适配
        if (queryForm.getIsAdapt() == 1){
            ProtocolAdaptDTO adaptDTO = new ProtocolAdaptDTO();
            BeanUtils.copyProperties(queryForm, adaptDTO);
            int adaptResult = protocolService.protocolAdapt(adaptDTO);
            if (adaptResult == 1){
                log.error("协议查询失败");
                return null;
            }
        }

        // 2.查询设备已启用的设备版本号
        DeviceProtocolRelationship relationship = deviceProtocolRelationshipRepository.findBySnCodeAndIsUsed(queryForm.getSnCode(), 1);
        if (relationship == null){
            return null;
        }
        String protocolVersion = relationship.getProtocolVersion();

        // 3.通过SN码 已启用的协议版本号查询 协议祥表list 分页
        Page<ProtocolDetail> detailPage = detailRepository.findByProtocolVersion(protocolVersion, pageable);

        if (detailPage.getSize() == 0){
            return null;
        }

        // 4. ProtocolDetail对象列表转换为ProtocolDetailVO 对象列表
        List<ProtocolDetailVO> detailVOList = ProtocolDetail2ProtocolDetailVO.convert(detailPage.getContent());

        // 5. 获取是否可视 报警 标志位 ProtocolStrategyDTO 对象列表
        VisibleStrategy visibleStrategy = visibleStrategyRepository.findByUserNameAndSnCodeAndProtocolVersionAndIsUsed(queryForm.getUserName(),
                                                                                                                        queryForm.getSnCode(),
                                                                                                                        queryForm.getProtocolVersion(),
                                                                                                                        1);

        AlarmStrategy alarmStrategy = alarmStrategyRepository.findByUserNameAndSnCodeAndProtocolVersionAndIsUsed(queryForm.getUserName(),
                                                                                                                queryForm.getSnCode(),
                                                                                                                queryForm.getProtocolVersion(),
                                                                                                                1);

        String visibleMask = visibleStrategy.getVisibleMask();
        String alarmMask = alarmStrategy.getAlarmMask();


        char[] visibleList = StringUtil.string2CharList(visibleMask);
        char[] alarmList = StringUtil.string2CharList(alarmMask);

        List<ProtocolStrategyDTO> strategyDTOList = new ArrayList<>();
        for (int i = 0; i<= visibleMask.length()-1; i++){
            ProtocolStrategyDTO strategyDTO = new ProtocolStrategyDTO();
            strategyDTO.setOffsetNumber(i + 1);
            strategyDTO.setIsVisible((int)visibleList[i] - 48);
            strategyDTO.setIsAlarmed((int)alarmList[i] - 48 );

            strategyDTOList.add(strategyDTO);
        }

        // 6.ProtocolStrategyDTO 对象列表转换为 ProtocolDetailVO 对象列表
        for (ProtocolDetailVO protocolDetailVO : detailVOList){
            for (ProtocolStrategyDTO protocolStrategyDTO : strategyDTOList){
                // 通过判断offset值来判断是否为匹配的处理对象
                boolean isSame = protocolDetailVO.getOffsetNumber() == protocolStrategyDTO.getOffsetNumber();
                if (isSame){
                    // 匹配 进行数据封装
                    BeanUtils.copyProperties(protocolStrategyDTO, protocolDetailVO);
                }
            }
        }

        map.put("code", 0);
        map.put("msg", DataHandleEnum.GET_DATA_SUCCESS.getMessage());
        map.put("total", detailPage.getTotalPages());
        map.put("records", detailPage.getTotalElements());
        map.put("data", detailVOList);

       return map;

       // return new PageImpl<ProtocolDetail>(detailPage.getContent(), pageable,detailPage.getTotalElements());


    }

    @Override
    @Transactional
    public  Map<String,Object>  updateProtocolDetail(ProtocolDetailForm form, Pageable pageable) {

        Map<String,Object> map = new HashMap<String,Object>();

        // 1.通过用户名 SN码 协议版本号 查询当前启用的 可视及 报警策略记录
        AlarmStrategy alarmStrategy = alarmStrategyRepository.findByUserNameAndSnCodeAndProtocolVersionAndIsUsed(form.getUserName(),
                                                                                                                form.getSnCode(),
                                                                                                                form.getProtocolVersion(),
                                                                                                                1);

        VisibleStrategy visibleStrategy = visibleStrategyRepository.findByUserNameAndSnCodeAndProtocolVersionAndIsUsed(form.getUserName(),
                                                                                                                        form.getSnCode(),
                                                                                                                        form.getProtocolVersion(),
                                                                                                                        1);

        // 2.表单参数赋给查询到的记录(封装为一个函数)
        StringBuilder alarmBuilder = new StringBuilder(alarmStrategy.getAlarmMask());
        alarmBuilder.replace(form.getOffsetNumber() - 1,form.getOffsetNumber(),String.valueOf(form.getIsAlarmed()));

        StringBuilder visibleBuilder = new StringBuilder(visibleStrategy.getVisibleMask());
        visibleBuilder.replace(form.getOffsetNumber() - 1, form.getOffsetNumber(), String.valueOf(form.getIsVisible()));

        alarmStrategy.setAlarmMask(alarmBuilder.toString());
        visibleStrategy.setVisibleMask(visibleBuilder.toString());

        // 3.修改 保存祥表记录
        alarmStrategyRepository.save(alarmStrategy);
        visibleStrategyRepository.save(visibleStrategy);

        ProtocolQueryForm queryForm = new ProtocolQueryForm();
        BeanUtils.copyProperties(form, queryForm);
        // TODO 此处置为0 是为协议自动同步做预留
        queryForm.setIsAdapt(0);

        map = protocolService.listFindCurrentBySnCode(queryForm,pageable);

        return map;
        // 4.通过SN码 协议版本号 查询协议祥表list 分页
//        Page<ProtocolConfigDetail> configDetailPage = detailRepository.findBySnCodeAndProtocolVersion(
//                protocolConfigDetail.getSnCode(),
//                protocolConfigDetail.getProtocolVersion(),
//                pageable);

//        return  new PageImpl<ProtocolConfigDetail>(configDetailPage.getContent(), pageable,configDetailPage.getTotalElements());

 //       return null;
    }

    @Override
    public String getProtocolVersionBySnCode(String snCode) {

        DeviceProtocolRelationship relationship = deviceProtocolRelationshipRepository.findBySnCodeAndIsUsed(snCode, 1);
        if (relationship == null){
            return null;
        }
        return relationship.getProtocolVersion();

    }

    @Override
    @Transactional
    public Integer protocolAdapt(ProtocolAdaptDTO adaptDTO) {

        // 接收要适配的协议内容
        String[] arrayDataName = adaptDTO.getProtocolContent().split("_");
        // 用于策略默认设置
        String strategyDefault = "";

        // 0.获取策略默认字符串
        for(int j = 0; j<= arrayDataName.length - 1; j++){
            strategyDefault += "1";
        }

        // 1.查询设备当前启用协议
        DeviceProtocolRelationship relationshipOld = deviceProtocolRelationshipRepository.findBySnCodeAndIsUsed(adaptDTO.getSnCode(), 1);

        if (relationshipOld != null){
            // 2.1 若存在已启用协议 将已启用的协议 设置为未启用
            relationshipOld.setIsUsed(0);
            deviceProtocolRelationshipRepository.save(relationshipOld);

            // 2.2 将已启用的策略 设置为未启用
            VisibleStrategy visibleStrategyOld = visibleStrategyRepository.findByUserNameAndSnCodeAndProtocolVersionAndIsUsed(adaptDTO.getUserName(),
                                                                                                                                adaptDTO.getSnCode(),
                                                                                                                                relationshipOld.getProtocolVersion(),
                                                                                                                                1);
            visibleStrategyOld.setIsUsed(0);
            visibleStrategyRepository.save(visibleStrategyOld);

            AlarmStrategy alarmStrategyOld = alarmStrategyRepository.findByUserNameAndSnCodeAndProtocolVersionAndIsUsed(adaptDTO.getUserName(),
                                                                                                                        adaptDTO.getSnCode(),
                                                                                                                        relationshipOld.getProtocolVersion(),
                                                                                                                        1);

            alarmStrategyOld.setIsUsed(0);
            alarmStrategyRepository.save(alarmStrategyOld);

        }

        // 3.查询要适配的协议是否存在
        DeviceProtocolRelationship relationshipNew = deviceProtocolRelationshipRepository.findBySnCodeAndProtocolVersion(adaptDTO.getSnCode(), adaptDTO.getProtocolVersion());
        if (relationshipNew != null){
            // 4.1存在 协议重新启用
            relationshipNew.setIsUsed(1);
            relationshipNew.setUsedTime(DateUtil.getDate());
            deviceProtocolRelationshipRepository.save(relationshipNew);

            // 4.2 修改 可视 报警策略为默认（即启用默认 策略）
            VisibleStrategy visibleStrategyDefault = visibleStrategyRepository.findByUserNameAndSnCodeAndProtocolVersionAndVisibleMask(adaptDTO.getUserName(),
                    adaptDTO.getSnCode(),
                    relationshipNew.getProtocolVersion(),
                    strategyDefault);

            visibleStrategyDefault.setIsUsed(1);
            visibleStrategyDefault.setUsedTime(DateUtil.getDate());
            visibleStrategyRepository.save(visibleStrategyDefault);

            AlarmStrategy alarmStrategyDefault = alarmStrategyRepository.findByUserNameAndSnCodeAndProtocolVersionAndAlarmMask(adaptDTO.getUserName(),
                    adaptDTO.getSnCode(),
                    relationshipNew.getProtocolVersion(),
                    strategyDefault);
            alarmStrategyDefault.setIsUsed(1);
            alarmStrategyDefault.setUsedTime(DateUtil.getDate());
            alarmStrategyRepository.save(alarmStrategyDefault);

            return 0;
        }

        // 5.不存在 则添加设备协议关系记录
        DeviceProtocolRelationship relationship= new DeviceProtocolRelationship();
        relationship.setId(KeyUtil.genUniqueKey());
        relationship.setSnCode(adaptDTO.getSnCode());
        relationship.setProtocolVersion(adaptDTO.getProtocolVersion());
        relationship.setIsUsed(1);
        relationship.setUsedTime(DateUtil.getDate());
        deviceProtocolRelationshipRepository.save(relationship);

        // 6.添加协议祥表记录
        // 6.1 先提取协议中的数据名称

        // 6.2 储存协议祥表
        for (int i = 0; i <= arrayDataName.length - 1; i ++){
            ProtocolDetail detail = new ProtocolDetail();
            detail.setId(KeyUtil.genUniqueKey());
            detail.setProtocolVersion(adaptDTO.getProtocolVersion());
            detail.setOffsetNumber(i + 1);
            detail.setDataName(arrayDataName[i]);

            detailRepository.save(detail);
        }

        // 6.3 添加 可视 报警策略为默认
        VisibleStrategy visibleStrategyNew = new VisibleStrategy();
        visibleStrategyNew.setId(KeyUtil.genUniqueKey());
        visibleStrategyNew.setUserName(adaptDTO.getUserName());
        visibleStrategyNew.setSnCode(adaptDTO.getSnCode());
        visibleStrategyNew.setProtocolVersion(adaptDTO.getProtocolVersion());
        visibleStrategyNew.setVisibleMask(strategyDefault);
        visibleStrategyNew.setIsUsed(1);
        visibleStrategyNew.setUsedTime(DateUtil.getDate());

        visibleStrategyRepository.save(visibleStrategyNew);

        AlarmStrategy alarmStrategyNew = new AlarmStrategy();
        alarmStrategyNew.setId(KeyUtil.genUniqueKey());
        alarmStrategyNew.setUserName(adaptDTO.getUserName());
        alarmStrategyNew.setSnCode(adaptDTO.getSnCode());
        alarmStrategyNew.setProtocolVersion(adaptDTO.getProtocolVersion());
        alarmStrategyNew.setAlarmMask(strategyDefault);
        alarmStrategyNew.setIsUsed(1);
        alarmStrategyNew.setUsedTime(DateUtil.getDate());

        alarmStrategyRepository.save(alarmStrategyNew);

        return 0;
    }

    @Override
    public List<RealTimeProtocolVO> listForRealTimeDisplay(String userName,String snCode, String protocolVersion) {
        // 1.获取协议详情列表
        List<ProtocolDetail> detailList = detailRepository.findListByProtocolVersion(protocolVersion);

        if (detailList.size() == 0){
            return null;
        }

        // 2.获取需显示ProtocolDetail 对象列表
        // 2.1 获取可视策略记录
        VisibleStrategy visibleStrategy = visibleStrategyRepository.findByUserNameAndSnCodeAndProtocolVersionAndIsUsed(userName, snCode, protocolVersion, 1);

        String visibleMask = visibleStrategy.getVisibleMask();
        char[] visibleList = StringUtil.string2CharList(visibleMask);

        // 2.2 封装为可视 ProtocolDetail 对象列表
        List<ProtocolDetail> detailListShow = new ArrayList<ProtocolDetail>();
        for (ProtocolDetail protocolDetail : detailList){
            // 判断数据是否需要显示
            boolean isShow = visibleList[protocolDetail.getOffsetNumber() - 1] == '1';
            if (isShow){
                detailListShow.add(protocolDetail);
            }
        }

        // 3.将 ProtocolDetail 对象列表 封装为 RealTimeProtocolVO 对象列表
        return ProtocolDetail2RealTimeProtocolVO.convert(detailListShow);

    }

    @Override
    public List<HistoricalDataProtocolVO> listFindByProtocolVersion(String protocolVersion) {

        // 1.获取协议详情 列表
        List<ProtocolDetail> detailList = detailRepository.findListByProtocolVersion(protocolVersion);

        // 2.转换为HistoricalDataProtocolVO 列表
        List<HistoricalDataProtocolVO> protocolVOList = ProtocolDetail2HistoricalDataProtocolVO.convert(detailList);

        return protocolVOList;
    }
}
