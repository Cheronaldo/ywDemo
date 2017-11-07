package com.cherry.repository;

import com.cherry.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户信息DAO层
 * Created by Administrator on 2017/11/07.
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,String>{

}
