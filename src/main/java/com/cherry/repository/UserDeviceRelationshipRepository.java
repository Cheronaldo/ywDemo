package com.cherry.repository;

import com.cherry.dataobject.UserDeviceRelationship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * @param userName
     * @param isUsed
     * @return
     */
    List<UserDeviceRelationship> findByUserNameAndIsUsed(String userName,Integer isUsed);

    /**
     * 通过 现场用户名获取 设备列表分页对象
     * SN码 分页对象
     * @param userName
     * @param isUsed
     * @param pageable
     * @return
     */
    Page<UserDeviceRelationship> findByUserNameAndIsUsed(String userName, Integer isUsed, Pageable pageable);

    /**
     * 通过 经销商用户名和启用标志 获取 经销商名下 且对应现场用户 未绑定的  用户设备关系记录列表
     * @param userName
     * @param isUsed
     * @param snCodeList
     * @return
     */
    List<UserDeviceRelationship> findByUserNameAndIsUsedAndSnCodeNotIn(String userName, Integer isUsed, List<String> snCodeList);
}
