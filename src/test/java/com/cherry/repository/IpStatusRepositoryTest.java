package com.cherry.repository;

import com.cherry.dataobject.IpStatus;
import com.cherry.util.DateUtil;
import com.cherry.util.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * IP状态表 DAO层测试
 * Created by Administrator on 2018/01/09.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IpStatusRepositoryTest {

    @Autowired
    private IpStatusRepository repository;

    @Test
    public void save() throws Exception{

        IpStatus status = new IpStatus();
        status.setId(KeyUtil.genUniqueKey());
        status.setUserName("Test002");
        status.setUserIp("192.168.1.103");
        status.setLoginTime(DateUtil.getDate());
        status.setIsUsed(0);

        IpStatus result = repository.save(status);

        Assert.assertNotNull(result);


    }

    @Test
    public void findByUserNameAndIsUsed() throws Exception {

        IpStatus result = repository.findByUserNameAndIsUsed("Test001", 1);

        Assert.assertNotNull(result);

    }

    @Test
    public void findByUserNameAndUserIp() throws Exception {

        IpStatus result = repository.findByUserNameAndUserIp("Test001", "127.0.0.1");

        Assert.assertNotNull(result);

    }

    @Test
    public void findByUserIpAndIsUsed() throws Exception{

        List<IpStatus> result = repository.findByUserIpAndIsUsed("127.0.0.1", 1);

        Assert.assertNotNull(result);

    }

}