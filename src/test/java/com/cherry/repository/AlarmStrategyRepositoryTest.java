package com.cherry.repository;

import com.cherry.dataobject.AlarmStrategy;
import com.cherry.util.DateUtil;
import com.cherry.util.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 报警策略表DAO层测试
 * Created by Administrator on 2017/12/01.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AlarmStrategyRepositoryTest {

    @Autowired
    private AlarmStrategyRepository repository;

    @Test
    public void saveStrategy() throws Exception{
        AlarmStrategy strategy = new AlarmStrategy();
        strategy.setId(KeyUtil.genUniqueKey());
        strategy.setUserName("abc1234");
        strategy.setSnCode("1511962706665703659");
        strategy.setProtocolVersion("yw123");
        strategy.setAlarmMask("110");
        strategy.setIsUsed(1);
        strategy.setUsedTime(DateUtil.getDate());

        AlarmStrategy result = repository.save(strategy);

        Assert.assertNotNull(result);

    }

    @Test
    public void findByUserNameAndSnCodeAndProtocolVersionAndIsUsed() throws Exception {

        AlarmStrategy result = repository.findByUserNameAndSnCodeAndProtocolVersionAndIsUsed("abc1234","1511962658712691673","yw123",1);

        Assert.assertNotNull(result);
    }

    @Test
    public void findByUserNameAndSnCodeAndProtocolVersionAndAlarmMask() throws Exception{
        AlarmStrategy result = repository.findByUserNameAndSnCodeAndProtocolVersionAndAlarmMask("abc1234","1511962658712691673","yw123","1101");

        Assert.assertNotNull(result);

    }

}