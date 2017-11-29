package com.cherry.repository;

import com.cherry.dataobject.ProtocolDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 设备协议祥表DAO层
 * Created by Administrator on 2017/11/29.
 */
public interface ProtocolDetailRepository extends JpaRepository<ProtocolDetail,String>{

    /**
     * 通过SN码和版本号查询协议祥表List
     * 分页功能
     * @param snCode
     * @param protocolVersion
     * @return
     */
    Page<ProtocolDetail> findBySnCodeAndProtocolVersion(String snCode, String protocolVersion, Pageable pageable);

    /**
     * 通过SN码 和协议版本 查询需要显示的 协议详情列表
     * 实时数据显示 协议查询
     * 将查询的结果与可见策略表结合再回传给前端
     * @param snCode
     * @param protocolVersion
     * @return
     */
    List<ProtocolDetail> findListBySnCodeAndProtocolVersion(String snCode, String protocolVersion);

}
