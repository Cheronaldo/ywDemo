package com.cherry.service;

import com.cherry.dataobject.ProtocolConfigDetail;
import com.cherry.dataobject.ProtocolDetail;
import com.cherry.dto.ProtocolAdaptDTO;
import com.cherry.form.ProtocolDetailForm;
import com.cherry.form.ProtocolQueryForm;
import com.cherry.vo.DataReadWriteProtocolVO;
import com.cherry.vo.HistoricalDataProtocolVO;
import com.cherry.vo.RealTimeProtocolVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 设备协议配置Service层
 * Created by Administrator on 2017/11/15.
 */
public interface ProtocolService {

    /**
     * 通过SN码 查询用户该设备当前启用的 协议祥表list
     * 分页查询
     * 再结合策略表返回给前端
     * @param queryForm
     * @param pageable
     * @return
     */
    Map<String,Object> listFindCurrentBySnCode(ProtocolQueryForm queryForm, Pageable pageable);

    /**
     * 修改协议祥表记录
     * 并返回协议祥表记录列表
     * 分页查询
     * @param form
     * @return
     */
    Map<String,Object> updateProtocolDetail(ProtocolDetailForm form, Pageable pageable);

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
     * 通过SN码 协议版本号 查询用户该设备需要显示的 实时数据协议详情列表
     * 用于实时数据显示协议查询
     * @param userName
     * @param snCode
     * @param protocolVersion
     * @return
     */
    List<RealTimeProtocolVO> listForRealTimeDisplay(String userName,String snCode, String protocolVersion);

    /**
     * 通过协议版本获取协议详情
     * 用于全项历史数据查询协议详情
     * @param protocolVersion
     * @return
     */
    List<HistoricalDataProtocolVO> listFindByProtocolVersion(String protocolVersion);

    /**
     * 通过协议版本号获取 数据读写 协议详情列表
     * @param protocolVersion
     * @return
     */
    List<DataReadWriteProtocolVO> listForDataReadWrite(String protocolVersion);

}
