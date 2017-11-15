package com.cherry.service;

import com.cherry.dataobject.UserInfo;
import com.cherry.dataobject.UserLevel;
import com.cherry.form.UserInfoForm;
import com.cherry.vo.UserInfoVO;

/**
 * 用户信息服务接口
 * Created by Administrator on 2017/11/07.
 */
public interface UserInfoService {

    /**
     * 用户信息注册 修改保存
     * @param userInfoForm
     * @return
     */
    Integer saveUser(UserInfoForm userInfoForm);


    /**
     * 用户登录
     * @param userName
     * @param userPassword
     * @return
     */
    Integer userLogin(String userName, String userPassword);

    /**
     * 通过用户名查询用户信息
     * 信息修改
     * @param userName
     * @return
     */
    UserInfoVO getUserInfoVOByUserName(String userName);

    /**
     * 通过用户名获取用户等级码
     * 用于用户名合法性校验 用户登录成功后的跳转
     * @param userName
     * @return
     */
    UserInfo getUserInfoByUserName(String userName);
}
