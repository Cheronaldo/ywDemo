package com.cherry.repository;

import com.cherry.dataobject.VisibleStrategy;
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
 * 可视策略表DAO层测试
 * Created by Administrator on 2017/12/01.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VisibleStrategyRepositoryTest {

    @Autowired
    private VisibleStrategyRepository repository;

    @Test
    public void saveStrategy() throws Exception{
        VisibleStrategy strategy = new VisibleStrategy();
        strategy.setId(KeyUtil.genUniqueKey());
        strategy.setUserName("abc1234");
        strategy.setSnCode("1511962658712691673");
        strategy.setProtocolVersion("yw123");
        strategy.setVisibleMask("011");
        strategy.setIsUsed(0);
        strategy.setUsedTime(DateUtil.getDate());

        VisibleStrategy result = repository.save(strategy);

        Assert.assertNotNull(result);

    }

    @Test
    public void findByUserNameAndSnCodeAndProtocolVersionAndIsUsed() throws Exception {

        VisibleStrategy result = repository.findByUserNameAndSnCodeAndProtocolVersionAndIsUsed("abc1234","1511962658712691673","yw123",1);

        Assert.assertNotNull(result);

    }

    @Test
    public void findByUserNameAndSnCodeAndProtocolVersionAndVisibleMask() throws Exception{

        VisibleStrategy result = repository.findByUserNameAndSnCodeAndProtocolVersionAndVisibleMask("abc1234","1511962658712691673","yw123","101");

        Assert.assertNotNull(result);

    }

}