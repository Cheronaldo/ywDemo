package com.cherry.repository;

import com.cherry.dataobject.UserLevel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户等级代码表DAO层
 * Created by Administrator on 2017/11/09.
 */
public interface UserLevelRepository extends JpaRepository<UserLevel, Integer>{

    /**
     * 通过用户等级内容获取用户等级码
     * 用于注册时等级码储存
     * @param classInfo
     * @return
     */
    UserLevel findByClassInfo(String classInfo);

    /**
     * 通过用户等级码获取用户等级具体内容
     * 用于用户信息修改时 返回给前端
     * @param userClass
     * @return
     */
    UserLevel findByUserClass(Integer userClass);
}
