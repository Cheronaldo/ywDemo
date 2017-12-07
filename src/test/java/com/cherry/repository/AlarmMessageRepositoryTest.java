package com.cherry.repository;

import com.cherry.dataobject.AlarmMessage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 报警信息代码表DAO层测试
 * Created by Administrator on 2017/12/07.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AlarmMessageRepositoryTest {

    @Autowired
    private AlarmMessageRepository repository;

    @Test
    public void saveMessage() throws Exception{
        AlarmMessage result = repository.save(new AlarmMessage(2, "低于下阈值"));

        Assert.assertNotNull(result);
    }

    @Test
    public void findByAlarmCode() throws Exception {

        AlarmMessage result = repository.findByAlarmCode(1);
        Assert.assertNotNull(result);

    }

}