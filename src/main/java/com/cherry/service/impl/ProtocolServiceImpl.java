package com.cherry.service.impl;

import com.cherry.dataobject.ProtocolConfigDetail;
import com.cherry.dataobject.ProtocolConfigMaster;
import com.cherry.form.ProtocolDetailForm;
import com.cherry.form.ProtocolReAdaptForm;
import com.cherry.repository.ProtocolConfigDetailRepository;
import com.cherry.repository.ProtocolConfigMasterRepository;
import com.cherry.service.ProtocolService;
import com.cherry.util.DateUtil;
import com.cherry.util.KeyUtil;
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
public class ProtocolServiceImpl implements ProtocolService{

    @Autowired
    private ProtocolConfigDetailRepository detailRepository;

    @Autowired
    private ProtocolConfigMasterRepository masterRepository;

    @Override
    public Page<ProtocolConfigDetail> listFindCurrentBySnCode(String snCode, Pageable pageable) {

        // 1.查询设备已启用的设备版本号
        ProtocolConfigMaster protocolConfigMaster = masterRepository.findBySnCodeAndIsUsed(snCode, 1);
        if (protocolConfigMaster == null){
            return null;
        }
        String protocolVersion = protocolConfigMaster.getProtocolVersion();

        // 2.通过SN码 已启用的协议版本号查询 协议祥表list 分页
        Page<ProtocolConfigDetail> configDetailPage = detailRepository.findBySnCodeAndProtocolVersion(snCode, protocolVersion, pageable);

        //return detailList;
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

        //return detailList;
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
    public Integer protocolReAdapt(ProtocolReAdaptForm form) {

        // 1.查询设备当前启用协议
        ProtocolConfigMaster protocolConfigMasterOld = masterRepository.findBySnCodeAndIsUsed(form.getSnCode(), 1);

        if (protocolConfigMasterOld != null){
            // 2.若存在已启用协议 将已启用的协议 设置为未启用
            protocolConfigMasterOld.setIsUsed(0);
            masterRepository.save(protocolConfigMasterOld);
        }

        // 3.查询要适配的协议是否存在
        ProtocolConfigMaster protocolConfigMasterNew = masterRepository.findBySnCodeAndProtocolVersion(form.getSnCode(), form.getProtocolVersion());
        if (protocolConfigMasterNew != null){
            // 4.存在 协议重新启用
            protocolConfigMasterNew.setIsUsed(1);
            masterRepository.save(protocolConfigMasterNew);

            return 0;
        }

        // 5.不存在 则添加协议主表
        ProtocolConfigMaster protocolConfigMaster = new ProtocolConfigMaster();
        protocolConfigMaster.setId(KeyUtil.genUniqueKey());
        protocolConfigMaster.setSnCode(form.getSnCode());
        protocolConfigMaster.setProtocolVersion(form.getProtocolVersion());
        protocolConfigMaster.setIsUsed(1);
        protocolConfigMaster.setUsedTime(DateUtil.getDate());
        masterRepository.save(protocolConfigMaster);

        // 6.添加协议祥表记录
        // 6.1 先提取协议中的数据名称
        String[] arrayDataName = form.getItems().split("_");
        // 6.2 储存协议祥表
        for (int i = 0; i <= arrayDataName.length - 1; i ++){
            ProtocolConfigDetail configDetail = new ProtocolConfigDetail();
            configDetail.setId(KeyUtil.genUniqueKey());
            configDetail.setSnCode(form.getSnCode());
            configDetail.setProtocolVersion(form.getProtocolVersion());
            configDetail.setOffsetNumber(i + 1);
            configDetail.setDataName(arrayDataName[i]);
            configDetail.setIsVisible(1);
            configDetail.setIsAlarmed(1);

            detailRepository.save(configDetail);
        }

        return 0;
    }
}
