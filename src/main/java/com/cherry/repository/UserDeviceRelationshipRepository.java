package com.cherry.repository;

import com.cherry.dataobject.UserDeviceRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 用户设备关系表DAO层
 * Created by Administrator on 2017/11/10.
 */
public interface UserDeviceRelationshipRepository extends JpaRepository<UserDeviceRelationship,String>{

    /**
     * 通过SN码 及 用户名 查询关系记录
     * @param snCode
     * @param userName
     * @return
     */
    UserDeviceRelationship findBySnCodeAndUserName(String snCode, String userName);

    /**
     * 通过用户名和启用状态 查询该用户启用的全部设备SN码列表
     * @param isUsed
     * @param userName
     * @return
     */
    List<UserDeviceRelationship> findByIsUsedAndUserName(Integer isUsed, String userName);
}
