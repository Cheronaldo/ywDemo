package com.cherry.repository;

import com.cherry.dataobject.IpStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

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

    /**
     * 查询当前IP是否已存在登录用户
     * @param userIp
     * @param isUsed
     * @return
     */
    List<IpStatus> findByUserIpAndIsUsed(String userIp, Integer isUsed);

    /**
     * 获取 用户在对应IP的记录
     * @param userName
     * @param userIp
     * @param isUsed
     * @return
     */
    IpStatus findByUserNameAndUserIpAndIsUsed(String userName, String userIp, Integer isUsed);

}
