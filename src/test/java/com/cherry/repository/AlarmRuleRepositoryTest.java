package com.cherry.repository;

import com.cherry.dataobject.AlarmRule;
import com.cherry.util.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 报警规则表DAO层测试
 * Created by Administrator on 2017/12/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AlarmRuleRepositoryTest {

    @Autowired
    private AlarmRuleRepository repository;

    @Test
    public void saveRule() throws Exception{
        AlarmRule rule = new AlarmRule();
        rule.setId(KeyUtil.genUniqueKey());
        rule.setSnCode("HMITest001");
        rule.setProtocolVersion("ywv1.1");
        rule.setOffsetNumber(1);
        rule.setDownThreshold("50");
        rule.setUpThreshold("60");

        repository.save(rule);
    }

    @Test
    public void findBySnCodeAndProtocolVersionOrderByOffsetNumberAsc() throws Exception {

        PageRequest request = new PageRequest(0, 5);

        Page<AlarmRule> result = repository.findBySnCodeAndProtocolVersionOrderByOffsetNumberAsc("HMITest002", "ywv1.1", request);

        Assert.assertNotEquals(0, result.getTotalElements());

    }

}