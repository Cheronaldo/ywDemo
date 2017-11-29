package com.cherry.repository;

import com.cherry.dataobject.ProtocolDetail;
import com.cherry.util.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 协议祥表DAO层测试
 * Created by Administrator on 2017/11/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProtocolDetailRepositoryTest {

    @Autowired
    private ProtocolDetailRepository repository;

    @Test
    public void saveProtocol() throws Exception{

        ProtocolDetail detail = new ProtocolDetail();
        detail.setId(KeyUtil.genUniqueKey());
        detail.setSnCode("1511962706665703659");
        detail.setProtocolVersion("yw1234");
        detail.setOffsetNumber(2);
        detail.setDataName("PH值");
        detail.setAlarmThreshold("2");

        ProtocolDetail result = repository.save(detail);

        Assert.assertNotNull(result);


    }

    @Test
    public void findBySnCodeAndProtocolVersion() throws Exception {

        PageRequest request = new PageRequest(0, 2);

        Page<ProtocolDetail> result = repository.findBySnCodeAndProtocolVersion("1511962658712691673", "yw1234", request);

        Assert.assertNotEquals(0,result.getSize());

    }

    @Test
    public void findListBySnCodeAndProtocolVersion() throws Exception {

        List<ProtocolDetail> result = repository.findListBySnCodeAndProtocolVersion("1511962658712691673", "yw1234");

        Assert.assertNotEquals(0, result.size());

    }

}