package com.cherry.service;

import com.cherry.dataobject.ProtocolConfigDetail;
import com.cherry.dto.ProtocolAdaptDTO;
import com.cherry.form.ProtocolDetailForm;
import com.cherry.form.ProtocolQueryForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 设备协议配置Service层
 * Created by Administrator on 2017/11/15.
 */
public interface ProtocolService {

    /**
     * 通过SN码 查询设备当前启用的 协议祥表list
     * 分页查询
     * @param queryForm
     * @param pageable
     * @return
     */
    Page<ProtocolConfigDetail> listFindCurrentBySnCode(ProtocolQueryForm queryForm, Pageable pageable);

    /**
     * 修改协议祥表记录
     * 并返回协议祥表记录列表
     * 分页查询
     * @param form
     * @return
     */
    Page<ProtocolConfigDetail> updateProtocolDetail(ProtocolDetailForm form, Pageable pageable);

    /**
     * 通过SN码 查询获取设备当前启用的协议版本号
     * 实时数据 数据储存
     * @param snCode
     * @return
     */
    String getProtocolVersionBySnCode(String snCode);

    /**
     * 协议查询适配
     * 设备注册 修改
     * 实时数据查询 历史数据储存
     * @param adaptDTO
     * @return
     */
    Integer protocolAdapt(ProtocolAdaptDTO adaptDTO);

    /**
     * 通过SN码 协议版本号 查询需要显示的 实时数据协议详情列表
     * 用于实时数据显示协议查询
     * @param snCode
     * @param protocolVersion
     * @return
     */
    List<ProtocolConfigDetail> listForRealTimeDisplay(String snCode, String protocolVersion);

}
