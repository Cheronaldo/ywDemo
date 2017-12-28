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
import com.cherry.util.BeanUtil;
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
    private ProtocolMasterRepository masterRepository;

    @Autowired
    private ProtocolDetailRepository detailRepository;

    @Autowired
    private DeviceProtocolRelationshipRepository deviceProtocolRelationshipRepository;

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

        // 5. 获取是否可视 报警 策略 并封装为 ProtocolStrategyDTO 对象列表
        VisibleStrategy visibleStrategy = visibleStrategyRepository.findByUserNameAndSnCodeAndProtocolVersionAndIsUsed(queryForm.getUserName(),
                                                                                                                        queryForm.getSnCode(),
                                                                                                                        queryForm.getProtocolVersion(),
                                                                                                                        1);

        // 定义掩码字符串
        String visibleMask = "";
        String alarmMask = "";
        // 5.1 判断用户对当前协议的策略是否存在 (可视和报警策略 状态一致)
        if (visibleStrategy == null){
            // 不存在 添加默认记录
            // 5.2 设置默认掩码字符串
            for (int j = 0; j< detailPage.getTotalElements(); j++){
                visibleMask += "1";
                alarmMask += "1";
            }
            // 5.3 将用户当前启用的策略 设置为未启用
            VisibleStrategy visibleStrategyNow = visibleStrategyRepository.findByUserNameAndSnCodeAndIsUsed(queryForm.getUserName(),
                    queryForm.getSnCode(),
                    1);
            if (visibleStrategyNow != null){
                visibleStrategyNow.setIsUsed(0);

                visibleStrategyRepository.save(visibleStrategyNow);
            }

            // 5.4 再判断该用户是否存在默认策略

            VisibleStrategy defaultVisible = visibleStrategyRepository.findByUserNameAndSnCodeAndProtocolVersionAndVisibleMaskAndAlarmMask(queryForm.getUserName(),
                                                                                                                                            queryForm.getSnCode(),
                                                                                                                                            queryForm.getProtocolVersion(),
                                                                                                                                            visibleMask,
                                                                                                                                            alarmMask);

            if (defaultVisible == null){
                // 5.4 默认策略不存在 添加记录
                VisibleStrategy addVisibleDefault = BeanUtil.addVisibleStrategy(queryForm.getUserName(),
                        queryForm.getSnCode(),
                        queryForm.getProtocolVersion(),
                        visibleMask);

                visibleStrategyRepository.save(addVisibleDefault);
            }else {
                // 5.5 默认策略存在 启用记录
                defaultVisible.setIsUsed(1);
                defaultVisible.setUsedTime(DateUtil.getDate());

                visibleStrategyRepository.save(defaultVisible);
            }

        } else {
            // 策略表存在
            visibleMask = visibleStrategy.getVisibleMask();
            alarmMask = visibleStrategy.getAlarmMask();

        }

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

        //map.put("code", 0);
        //map.put("msg", ProtocolEnum.GET_PROTOCOL_SUCCESS.getMessage());
        map.put("total", detailPage.getTotalPages());
        map.put("records", detailPage.getTotalElements());
        map.put("rows", detailVOList);

       return map;

       // return new PageImpl<ProtocolDetail>(detailPage.getContent(), pageable,detailPage.getTotalElements());
    }

    @Override
    @Transactional
    public  Map<String,Object>  updateProtocolDetail(ProtocolDetailForm form, Pageable pageable) {

        Map<String,Object> map = new HashMap<String,Object>();

        // 1.通过用户名 SN码 协议版本号 查询当前启用的 可视及 报警策略记录

        VisibleStrategy visibleStrategy = visibleStrategyRepository.findByUserNameAndSnCodeAndProtocolVersionAndIsUsed(form.getUserName(),
                                                                                                                        form.getSnCode(),
                                                                                                                        form.getProtocolVersion(),
                                                                                                                        1);

        // 2.表单参数赋给查询到的记录(封装为一个函数)
        StringBuilder alarmBuilder = new StringBuilder(visibleStrategy.getAlarmMask());
        alarmBuilder.replace(form.getOffsetNumber() - 1,form.getOffsetNumber(),String.valueOf(form.getIsAlarmed()));

        StringBuilder visibleBuilder = new StringBuilder(visibleStrategy.getVisibleMask());
        visibleBuilder.replace(form.getOffsetNumber() - 1, form.getOffsetNumber(), String.valueOf(form.getIsVisible()));

        visibleStrategy.setAlarmMask(alarmBuilder.toString());
        visibleStrategy.setVisibleMask(visibleBuilder.toString());

        // 3.修改 保存祥表记录
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
        String[] protocolContent = adaptDTO.getProtocolContent().split("_");

        // 分别获取 数据名称 数据单位 数据阈值下限 数据阈值上限
        String[] arrayDataName = new String[protocolContent.length / 4];
        String[] arrayDataUnit= new String[protocolContent.length / 4];
        String[] arrayDataMinThreshold = new String[protocolContent.length / 4];
        String[] arrayDataMaxThreshold = new String[protocolContent.length / 4];

        for (int i = 0; i < protocolContent.length; i++){
            // 取余数 对应不同的情况
            int j = i % 4;
            if (j == 0){
                // 属于数据名称
                arrayDataName[i / 4] = protocolContent[i];
            }
            if (j == 1){
                // 属于时间单位
                arrayDataUnit[i / 4] = protocolContent[i];
            }
            if (j == 2){
                // 属于数据阈值下限
                arrayDataMinThreshold[i / 4] = protocolContent[i];
            }
            if (j == 3){
                // 属于数据阈值上限
                arrayDataMaxThreshold[i / 4] = protocolContent[i];
            }
        }

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

            // 2.2 将该用户已启用的策略(可视 报警) 设置为未启用
            VisibleStrategy visibleStrategyOld = visibleStrategyRepository.findByUserNameAndSnCodeAndIsUsed(adaptDTO.getUserName(),
                    adaptDTO.getSnCode(),
                    1);
            if (visibleStrategyOld != null){
                // 存在该用户对应的设备掩码
                visibleStrategyOld.setIsUsed(0);
                visibleStrategyRepository.save(visibleStrategyOld);
            }

        }

        // 3.查询要适配的协议是否存在
        ProtocolMaster masterNew = masterRepository.findOne(adaptDTO.getProtocolVersion());

        if (masterNew != null){
            // 要适配的协议存在
            // 4. 再判断该设备和协议是否有记录
            DeviceProtocolRelationship relationshipNew = deviceProtocolRelationshipRepository.findBySnCodeAndProtocolVersion(adaptDTO.getSnCode(), adaptDTO.getProtocolVersion());
            if (relationshipNew != null){
                // 4.1 设备协议关系存在 协议重新启用
                relationshipNew.setIsUsed(1);
                relationshipNew.setUsedTime(DateUtil.getDate());
                deviceProtocolRelationshipRepository.save(relationshipNew);
            } else {
                // 4.1 设备协议关系不存在 添加记录
                DeviceProtocolRelationship addRelation = new DeviceProtocolRelationship();
                addRelation.setId(KeyUtil.genUniqueKey());
                addRelation.setSnCode(adaptDTO.getSnCode());
                addRelation.setProtocolVersion(adaptDTO.getProtocolVersion());
                addRelation.setIsUsed(1);
                addRelation.setUsedTime(DateUtil.getDate());

                deviceProtocolRelationshipRepository.save(addRelation);
            }

            // 4.2 修改 可视 报警策略为默认（即启用默认 策略）
            VisibleStrategy visibleStrategyDefault = visibleStrategyRepository.findByUserNameAndSnCodeAndProtocolVersionAndVisibleMaskAndAlarmMask(adaptDTO.getUserName(),
                                                                                                                                                    adaptDTO.getSnCode(),
                                                                                                                                                    adaptDTO.getProtocolVersion(),
                                                                                                                                                    strategyDefault,
                                                                                                                                                    strategyDefault);

            if (visibleStrategyDefault != null){
                // 存在默认记录 修改为启用
                visibleStrategyDefault.setIsUsed(1);
                visibleStrategyDefault.setUsedTime(DateUtil.getDate());
                visibleStrategyRepository.save(visibleStrategyDefault);
            }else {
                // 不存在默认记录 添加记录
                VisibleStrategy addDefault = BeanUtil.addVisibleStrategy(adaptDTO.getUserName(),
                        adaptDTO.getSnCode(),
                        adaptDTO.getProtocolVersion(),
                        strategyDefault);

                visibleStrategyRepository.save(addDefault);
            }

            return 0;


        }

        // 5. 不存在 则添加设备主表记录
        ProtocolMaster master = new ProtocolMaster();
        master.setProtocolVersion(adaptDTO.getProtocolVersion());
        // TODO 目前公司名采用默认值 后期为实时读取
        master.setAgencyCompany("亿维自动化");

        masterRepository.save(master);

        // 6.不存在 则添加设备协议关系记录
        DeviceProtocolRelationship relationship= new DeviceProtocolRelationship();
        relationship.setId(KeyUtil.genUniqueKey());
        relationship.setSnCode(adaptDTO.getSnCode());
        relationship.setProtocolVersion(adaptDTO.getProtocolVersion());
        relationship.setIsUsed(1);
        relationship.setUsedTime(DateUtil.getDate());
        deviceProtocolRelationshipRepository.save(relationship);

        // 7.添加协议祥表记录
        // 7.1 先提取协议中的数据名称

        // 7.2 储存协议祥表
        for (int i = 0; i <= arrayDataName.length - 1; i ++){
            ProtocolDetail detail = new ProtocolDetail();
            detail.setId(KeyUtil.genUniqueKey());
            detail.setProtocolVersion(adaptDTO.getProtocolVersion());
            detail.setOffsetNumber(i + 1);
            detail.setDataName(arrayDataName[i]);
            detail.setDataUnit(arrayDataUnit[i]);
            detail.setMinThreshold(arrayDataMinThreshold[i]);
            detail.setMaxThreshold(arrayDataMaxThreshold[i]);

            detailRepository.save(detail);
        }

        // 7.3 添加 可视 报警策略为默认

        VisibleStrategy visibleStrategyNew = BeanUtil.addVisibleStrategy(adaptDTO.getUserName(),
                                                                            adaptDTO.getSnCode(),
                                                                            adaptDTO.getProtocolVersion(),
                                                                            strategyDefault);

        visibleStrategyRepository.save(visibleStrategyNew);

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
        // TODO 这里也要考虑用户的可视策略不存在的情况  设置为默认
        // 2.1 获取可视策略记录
        String visibleMask = "";
        String alarmMask = "";
        VisibleStrategy visibleStrategy = visibleStrategyRepository.findByUserNameAndSnCodeAndProtocolVersionAndIsUsed(userName, snCode, protocolVersion, 1);

        if (visibleStrategy == null){
            // 用户在当前协议下没有策略记录
            // 2.1 先非能已启用可视策略
            VisibleStrategy visibleStrategyNow = visibleStrategyRepository.findByUserNameAndSnCodeAndIsUsed(userName,snCode,1);
            if (visibleStrategyNow != null){
                visibleStrategyNow.setIsUsed(0);

                visibleStrategyRepository.save(visibleStrategyNow);
            }

            // 2.2 用户是否存在当前协议的默认策略
            for (int i = 0; i< detailList.size(); i++){
                visibleMask += "1";
                alarmMask += "1";
            }

            VisibleStrategy visibleStrategyDefault = visibleStrategyRepository.findByUserNameAndSnCodeAndProtocolVersionAndVisibleMaskAndAlarmMask(userName,snCode,protocolVersion,visibleMask,alarmMask);

            if (visibleStrategyDefault != null){
                // 启用协议
                visibleStrategyDefault.setIsUsed(1);
                visibleStrategyDefault.setUsedTime(DateUtil.getDate());

                visibleStrategyRepository.save(visibleStrategyDefault);
            } else {
                // 添加掩码

                VisibleStrategy addVisibleDefault = BeanUtil.addVisibleStrategy(userName, snCode, protocolVersion, visibleMask);

                visibleStrategyRepository.save(addVisibleDefault);
            }

        } else {
            // 用户当前协议掩码策略存在 直接获取即可
            visibleMask = visibleStrategy.getVisibleMask();
        }

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
