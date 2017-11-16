package com.cherry.service.impl;

import com.cherry.dataobject.UserInfo;
import com.cherry.form.UserInfoForm;
import com.cherry.form.UserUpdateForm;
import com.cherry.repository.UserInfoRepository;
import com.cherry.repository.UserLevelRepository;
import com.cherry.service.UserInfoService;
import com.cherry.vo.UserInfoVO;
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

    @Autowired
    private UserLevelRepository levelRepository;

    @Override
    public Integer saveUser(UserInfoForm userInfoForm) {

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoForm, userInfo);
        // 注意将用户类型转换为对应的 类型码再储存 否则userInfo中 userClass为 null
        int userClass = levelRepository.findByClassInfo(userInfoForm.getUserClass()).getUserClass();
        userInfo.setUserClass(userClass);

        try {
            repository.save(userInfo);
        }catch (Exception e){
            return 1;
        }
        return 0;
    }

    @Override
    public Integer userLogin(String userName, String userPassword) {

        // 1.查询密码是否存在
        UserInfo userInfo = repository.findOne(userName);
        if (userInfo == null){
            //该用户名不存在
            return 1;
        }

        // 2.密码一致性校验
        if (!userPassword.equals(userInfo.getUserPassword())){
            //输入密码与数据库用户密码不一致
            return 1;
        }

        // 3.添加用户信息至session(放到controller层)

        return 0;
    }

    @Override
    public UserInfoVO getUserInfoVOByUserName(String userName) {

        UserInfo userInfo = repository.findOne(userName);
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userInfo, userInfoVO);
        userInfoVO.setUserClass(levelRepository.findByUserClass(userInfo.getUserClass()).getClassInfo());

        return userInfoVO;

    }

    @Override
    public UserInfo getUserInfoByUserName(String userName) {
        return repository.findOne(userName);
    }

    @Override
    public Integer updateUserInfo(UserUpdateForm form) {
        // 1.查询获取用户基本信息记录
        UserInfo userInfo = repository.findOne(form.getUserName());
        // 2.将表单属性赋给基本信息记录
        BeanUtils.copyProperties(form, userInfo);
        // 3.注意将用户类型转换为对应的 类型码再储存 否则userInfo中 userClass为 null
        int userClass = levelRepository.findByClassInfo(form.getUserClass()).getUserClass();
        userInfo.setUserClass(userClass);
        // 4.保存修改后的信息记录
        try {
            repository.save(userInfo);
        }catch (Exception e){
            return 1;
        }
        return 0;
    }

    @Override
    public Integer updateUserPassword(String userName, String userPassword) {
        // 1.查询获取用户基本信息记录
        UserInfo userInfo = repository.findOne(userName);
        // 2.修改密码
        userInfo.setUserPassword(userPassword);
        // 3.保存记录
        try {
            repository.save(userInfo);
        }catch (Exception e){
            return 1;
        }
        return 0;
    }
}
