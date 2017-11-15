package com.cherry.repository;

import com.cherry.dataobject.UserLevel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 用户等级代码表DAO层测试
 * Created by Administrator on 2017/11/09.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserLevelRepositoryTest {

    @Autowired
    private UserLevelRepository repository;

    /**
     * 添加用户等级记录
     */
    @Test
    public void addClass(){
        UserLevel userLevel = new UserLevel(3,"现场");
        UserLevel result = repository.save(userLevel);
        Assert.assertNotNull(result);
    }


    /**
     * 通过用户等级内容查询用户等级码
     * @throws Exception
     */
    @Test
    public void findByClassInfo() throws Exception {

        int result = repository.findByClassInfo("管理员").getUserClass();
        Assert.assertEquals(1,result);

    }

    @Test
    public void findByUserClass() throws Exception{

        String result = repository.findByUserClass(2).getClassInfo();

        Assert.assertEquals("经销商",result);
    }

}