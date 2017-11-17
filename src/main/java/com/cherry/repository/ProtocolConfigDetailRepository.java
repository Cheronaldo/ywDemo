package com.cherry.repository;

import com.cherry.dataobject.ProtocolConfigDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 设备协议配置祥表DAO层
 * Created by Administrator on 2017/11/15.
 */
public interface ProtocolConfigDetailRepository extends JpaRepository<ProtocolConfigDetail,String>{

    /**
     * 通过SN码和版本号查询协议祥表List
     * 分页功能
     * @param snCode
     * @param protocolVersion
     * @return
     */
    Page<ProtocolConfigDetail> findBySnCodeAndProtocolVersion(String snCode, String protocolVersion, Pageable pageable);
}
