package com.cherry.service.impl;

import com.cherry.dataobject.UserInfo;
import com.cherry.form.UserInfoForm;
import com.cherry.form.UserUpdateForm;
import com.cherry.vo.UserInfoVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 用户信息服务层测试
 * Created by Administrator on 2017/11/07.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoServiceImplTest {

    @Autowired
    private UserInfoServiceImpl userInfoService;

    @Test
    public void saveUser() throws Exception {
        UserInfoForm userInfoForm = new UserInfoForm();
//        String userName = null;
//        userInfo.setUserName(userName);
        userInfoForm.setUserName("abc1234");
        userInfoForm.setUserPassword("abc123456");
        userInfoForm.setUserClass("经销商");
        userInfoForm.setUserPost("经理");
        userInfoForm.setUserMail("123@qq.com");
        userInfoForm.setUserCompany("深圳亿维自动化");
        userInfoForm.setUserTelephone("15927061684");

        int result = userInfoService.saveUser(userInfoForm);
        Assert.assertNotNull(result);
    }

    @Test
    public void userLogin() throws Exception {
        int result = userInfoService.userLogin("张三","1abc123456");
        Assert.assertEquals(0,result);
    }

    @Test
    public void getUserInfoVOByUserName() throws Exception{
        UserInfoVO result = userInfoService.getUserInfoVOByUserName("abc1234");

        Assert.assertNotNull(result);
    }

    @Test
    public void getUserInfoByUserName() throws Exception {
        UserInfo result = userInfoService.getUserInfoByUserName("abc1234");
        Assert.assertNotNull(result);

    }

    @Test
    public void updateUserInfo() throws Exception{

        UserUpdateForm form = new UserUpdateForm();
        form.setUserName("abc1234");
        form.setUserClass("经销商");
        form.setUserPost("经理");
        form.setUserMail("123@qq.com");
        form.setUserCompany("深圳亿维自动化");
        form.setUserTelephone("15927061684");

        int result = userInfoService.updateUserInfo(form);

        Assert.assertEquals(0,result);

    }

    @Test
    public void updateUserPassword() throws Exception{
        int result = userInfoService.updateUserPassword("abc1234","abc1234");

        Assert.assertEquals(0,result);
    }

}