package com.cherry.service.impl;

import com.cherry.dataobject.ProtocolConfigDetail;
import com.cherry.dataobject.ProtocolConfigMaster;
import com.cherry.dto.ProtocolAdaptDTO;
import com.cherry.enums.ProtocolEnum;
import com.cherry.exception.ProtocolException;
import com.cherry.form.ProtocolDetailForm;
import com.cherry.form.ProtocolQueryForm;
import com.cherry.repository.ProtocolConfigDetailRepository;
import com.cherry.repository.ProtocolConfigMasterRepository;
import com.cherry.service.ProtocolService;
import com.cherry.util.DateUtil;
import com.cherry.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
@Service
@Slf4j
public class ProtocolServiceImpl implements ProtocolService{

    @Autowired
    private ProtocolConfigDetailRepository detailRepository;

    @Autowired
    private ProtocolConfigMasterRepository masterRepository;

    @Autowired
    private ProtocolService protocolService;

    @Override
    @Transactional
    public Page<ProtocolConfigDetail> listFindCurrentBySnCode(ProtocolQueryForm queryForm, Pageable pageable) {

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
        ProtocolConfigMaster protocolConfigMaster = masterRepository.findBySnCodeAndIsUsed(queryForm.getSnCode(), 1);
        if (protocolConfigMaster == null){
            return null;
        }
        String protocolVersion = protocolConfigMaster.getProtocolVersion();

        // 3.通过SN码 已启用的协议版本号查询 协议祥表list 分页
        Page<ProtocolConfigDetail> configDetailPage = detailRepository.findBySnCodeAndProtocolVersion(queryForm.getSnCode(), protocolVersion, pageable);

        return new PageImpl<ProtocolConfigDetail>(configDetailPage.getContent(), pageable,configDetailPage.getTotalElements());
    }

    @Override
    public Page<ProtocolConfigDetail> updateProtocolDetail(ProtocolDetailForm form, Pageable pageable) {

        // 1.通过ID查询祥表记录
        ProtocolConfigDetail protocolConfigDetail = detailRepository.findOne(form.getId());

        // 2.表单参数赋给查询到的记录
        BeanUtils.copyProperties(form, protocolConfigDetail);

        // 3.修改 保存祥表记录
        detailRepository.save(protocolConfigDetail);

        // 4.通过SN码 协议版本号 查询协议祥表list 分页
        Page<ProtocolConfigDetail> configDetailPage = detailRepository.findBySnCodeAndProtocolVersion(
                protocolConfigDetail.getSnCode(),
                protocolConfigDetail.getProtocolVersion(),
                pageable);

        return  new PageImpl<ProtocolConfigDetail>(configDetailPage.getContent(), pageable,configDetailPage.getTotalElements());
    }

    @Override
    public String getProtocolVersionBySnCode(String snCode) {

        ProtocolConfigMaster protocolConfigMaster = masterRepository.findBySnCodeAndIsUsed(snCode, 1);
        if (protocolConfigMaster == null){
            return null;
        }
        return masterRepository.findBySnCodeAndIsUsed(snCode, 1).getProtocolVersion();
    }

    @Override
    @Transactional
    public Integer protocolAdapt(ProtocolAdaptDTO adaptDTO) {

        // 1.查询设备当前启用协议
        ProtocolConfigMaster protocolConfigMasterOld = masterRepository.findBySnCodeAndIsUsed(adaptDTO.getSnCode(), 1);

        if (protocolConfigMasterOld != null){
            // 2.若存在已启用协议 将已启用的协议 设置为未启用
            protocolConfigMasterOld.setIsUsed(0);
            masterRepository.save(protocolConfigMasterOld);
        }

        // 3.查询要适配的协议是否存在
        ProtocolConfigMaster protocolConfigMasterNew = masterRepository.findBySnCodeAndProtocolVersion(adaptDTO.getSnCode(), adaptDTO.getProtocolVersion());
        if (protocolConfigMasterNew != null){
            // 4.存在 协议重新启用
            protocolConfigMasterNew.setIsUsed(1);
            masterRepository.save(protocolConfigMasterNew);

            return 0;
        }

        // 5.不存在 则添加协议主表
        ProtocolConfigMaster protocolConfigMaster = new ProtocolConfigMaster();
        protocolConfigMaster.setId(KeyUtil.genUniqueKey());
        protocolConfigMaster.setSnCode(adaptDTO.getSnCode());
        protocolConfigMaster.setProtocolVersion(adaptDTO.getProtocolVersion());
        protocolConfigMaster.setIsUsed(1);
        protocolConfigMaster.setUsedTime(DateUtil.getDate());
        masterRepository.save(protocolConfigMaster);

        // 6.添加协议祥表记录
        // 6.1 先提取协议中的数据名称
        String[] arrayDataName = adaptDTO.getProtocolContent().split("_");
        // 6.2 储存协议祥表
        for (int i = 0; i <= arrayDataName.length - 1; i ++){
            ProtocolConfigDetail configDetail = new ProtocolConfigDetail();
            configDetail.setId(KeyUtil.genUniqueKey());
            configDetail.setSnCode(adaptDTO.getSnCode());
            configDetail.setProtocolVersion(adaptDTO.getProtocolVersion());
            configDetail.setOffsetNumber(i + 1);
            configDetail.setDataName(arrayDataName[i]);
            configDetail.setIsVisible(1);
            configDetail.setIsAlarmed(1);

            detailRepository.save(configDetail);
        }

        return 0;
    }

    @Override
    public List<ProtocolConfigDetail> listForRealTimeDisplay(String snCode, String protocolVersion) {
        return detailRepository.findBySnCodeAndProtocolVersionAndIsVisible(snCode, protocolVersion, 1);
    }
}
