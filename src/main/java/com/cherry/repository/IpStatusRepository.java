package com.cherry.repository;

import com.cherry.dataobject.IpStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * IP状态表 DAO层
 * Created by Administrator on 2018/01/09.
 */
public interface IpStatusRepository extends JpaRepository<IpStatus, String>{

    /**
     * 查询用户当前在线的IP
     * @param userName
     * @param isUsed
     * @return
     */
    IpStatus findByUserNameAndIsUsed(String userName, Integer isUsed);

    /**
     * 通过用户名 和用户IP 获取IP状态记录
     * @param userName
     * @param userIp
     * @return
     */
    IpStatus findByUserNameAndUserIp(String userName, String userIp);

}
