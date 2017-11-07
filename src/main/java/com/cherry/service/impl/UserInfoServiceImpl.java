package com.cherry.service.impl;

import com.cherry.dataobject.UserInfo;
import com.cherry.form.UserInfoForm;
import com.cherry.repository.UserInfoRepository;
import com.cherry.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/11/07.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService{

    @Autowired
    private UserInfoRepository repository;

    @Override
    public Integer saveUser(UserInfoForm userInfoForm) {

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoForm, userInfo);
        UserInfo result = repository.save(userInfo);
        if(result == null){
            return 1;
        }
        return 0;
    }

    @Override
    public Integer userLogin(String userName, String userPassword) {

        //1.查询密码是否存在
        UserInfo userInfo = repository.findOne(userName);
        if(userInfo == null){
            //该用户名不存在
            return 1;
        }

        //2.密码加密（与前端一致）



        //3.密码一致性校验
        if(!userPassword.equals(userInfo.getUserPassword())){
            //输入密码与数据库用户密码不一致
            return 1;
        }

        //4.添加用户信息至session(放到controller层)

        return 0;
    }

    @Override
    public UserInfo findOneByUserName(String userName) {
        return repository.findOne(userName);
    }
}
