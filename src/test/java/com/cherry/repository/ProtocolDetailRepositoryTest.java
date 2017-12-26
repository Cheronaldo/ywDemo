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
//        detail.setSnCode("1511962706665703659");
        detail.setProtocolVersion("ywv1.1");
        detail.setOffsetNumber(1);
        detail.setDataName("PH值");
        //detail.setAlarmThreshold("2");

        ProtocolDetail result = repository.save(detail);

        Assert.assertNotNull(result);


    }

    @Test
    public void findBySnCodeAndProtocolVersion() throws Exception {

        PageRequest request = new PageRequest(0, 2);

        Page<ProtocolDetail> result = repository.findByProtocolVersion( "yw123", request);

        Assert.assertNotEquals(0,result.getSize());

    }

    @Test
    public void findListBySnCodeAndProtocolVersion() throws Exception {

        List<ProtocolDetail> result = repository.findListByProtocolVersion( "yw1234");

        Assert.assertNotEquals(0, result.size());

    }

    @Test
    public void findByProtocolVersionAndOffsetNumber() throws Exception{

        ProtocolDetail result = repository.findByProtocolVersionAndOffsetNumber("ywv1.1", 1);

        Assert.assertNotNull(result);

    }

}