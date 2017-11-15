package com.cherry.service;

import com.cherry.dataobject.ProtocolConfigDetail;
import com.cherry.form.ProtocolDetailForm;
import com.cherry.form.ProtocolReAdaptForm;

import java.util.List;

/**
 * 设备协议配置Service层
 * Created by Administrator on 2017/11/15.
 */
public interface ProtocolService {

    /**
     * 通过SN码 查询设备当前启用的 协议祥表list
     * @param snCode
     * @return
     */
    List<ProtocolConfigDetail> listFindCurrentBySnCode(String snCode);

    /**
     * 修改协议祥表记录
     * 并返回协议祥表记录列表
     * @param form
     * @return
     */
    List<ProtocolConfigDetail> updateProtocolDetail(ProtocolDetailForm form);

    /**
     * 通过SN码 查询获取设备当前启用的协议版本号
     * 实时数据 数据储存
     * @param snCode
     * @return
     */
    String getProtocolVersionBySnCode(String snCode);

    /**
     * 协议查询适配
     * 实时数据 数据储存
     * @param form
     * @return
     */
    Integer protocolReAdapt(ProtocolReAdaptForm form);

}
