package com.cherry.repository;

import com.cherry.dataobject.UserInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 用户信息DAO层测试
 * Created by Administrator on 2017/11/07.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoRepositoryTest {

    @Autowired
    private UserInfoRepository repository;

    /**
     * 用户信息储存测试
     */
    @Test
    public void saveUser(){
        UserInfo userInfo = new UserInfo();
//        String userName = null;
//        userInfo.setUserName(userName);
        userInfo.setUserName("张三");
        userInfo.setUserPassword("abc123456");
        userInfo.setUserClass(1);
        userInfo.setUserPost("经理");
        userInfo.setUserMail("123@qq.com");
        userInfo.setUserCompany("深圳亿维自动化");
        userInfo.setUserTelephone("15927061684");

        UserInfo result = repository.save(userInfo);
        Assert.assertNotNull(result);

    }

    /**
     * 通过用户名查找对象
     * 用于用户注册校验（用户名唯一） 用户信息修改
     */
    @Test
    public void findByUserName(){
        UserInfo result = repository.findOne("张三");
        Assert.assertNotNull(result);
    }


}