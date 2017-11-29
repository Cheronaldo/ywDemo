package com.cherry.repository;

import com.cherry.dataobject.ProtocolMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 协议主表DAO层测试
 * Created by Administrator on 2017/11/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProtocolMasterRepositoryTest {

    @Autowired
    private ProtocolMasterRepository repository;

    @Test
    public void saveProtocol() throws Exception{
        ProtocolMaster master = new ProtocolMaster();
        master.setProtocolVersion("yw1234");
        master.setAgencyCompany("中国移动");

        ProtocolMaster result = repository.save(master);

        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() throws Exception{

        ProtocolMaster result = repository.findOne("yw123");

        Assert.assertNotNull(result);

    }

}