package com.cherry.service.impl;

import com.cherry.dataobject.ProtocolConfigDetail;
import com.cherry.dataobject.ProtocolConfigMaster;
import com.cherry.form.ProtocolDetailForm;
import com.cherry.form.ProtocolReAdaptForm;
import com.cherry.repository.ProtocolConfigDetailRepository;
import com.cherry.repository.ProtocolConfigMasterRepository;
import com.cherry.service.ProtocolService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ProtocolConfigDetail> listFindCurrentBySnCode(String snCode) {

        // 1.查询设备已启用的设备版本号
        ProtocolConfigMaster protocolConfigMaster = masterRepository.findBySnCodeAndIsUsed(snCode, 1);
        if (protocolConfigMaster == null){
            return null;
        }
        String protocolVersion = protocolConfigMaster.getProtocolVersion();

        // 2.通过SN码 已启用的协议版本号查询 协议祥表list
        List<ProtocolConfigDetail> detailList = detailRepository.findBySnCodeAndProtocolVersion(snCode, protocolVersion);

        return detailList;
    }

    @Override
    public List<ProtocolConfigDetail> updateProtocolDetail(ProtocolDetailForm form) {

        // 1.通过ID查询祥表记录
        ProtocolConfigDetail protocolConfigDetail = detailRepository.findOne(form.getId());

        // 2.表单参数赋给查询到的记录
        BeanUtils.copyProperties(form, protocolConfigDetail);

        // 3.修改 保存祥表记录
        detailRepository.save(protocolConfigDetail);

        // 4.通过SN码 协议版本号 查询协议祥表list
        List<ProtocolConfigDetail> detailList = detailRepository.findBySnCodeAndProtocolVersion(form.getSnCode(), form.getProtocolVersion());

        return detailList;
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

        // 2.将已启用的协议 设置为未启用

        // 3.查询要适配的协议是否存在

        // 4.协议重新启用

        // 5.添加协议主表

        // 6.添加协议祥表记录

        return null;
    }
}
