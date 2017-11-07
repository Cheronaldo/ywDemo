package com.cherry.service;

import com.cherry.dataobject.UserInfo;
import com.cherry.form.UserInfoForm;

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
     * 注册校验  信息修改
     * @param userName
     * @return
     */
    UserInfo findOneByUserName(String userName);

}
