package com.cherry.repository;

import com.cherry.dataobject.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 用户信息DAO层
 * Created by Administrator on 2017/11/07.
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,String>{

    /**
     * 通过用户名和 启用标志 查询记录
     * 针对现场用户的注册
     * @param userName
     * @param isUsed
     * @return
     */
    UserInfo findByUserNameAndIsUsed(String userName, Integer isUsed);

    /**
     * 通过用户名列表 查询用户信息列表
     * 用于经销商查询 现场用户信息列表
     * @param userNameList
     * @return
     */
    Page<UserInfo> findByUserNameIn(List<String> userNameList, Pageable pageable);

}
