package com.cherry.repository;

import com.cherry.dataobject.ProtocolConfigMaster;
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
 * 设备协议配置主表DAO层测试
 * Created by Administrator on 2017/11/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProtocolConfigMasterRepositoryTest {

    @Autowired
    private ProtocolConfigMasterRepository repository;

    @Test
    public void saveProtocolMaster(){
        ProtocolConfigMaster protocolConfigMaster = new ProtocolConfigMaster();
        protocolConfigMaster.setId(KeyUtil.genUniqueKey());
        protocolConfigMaster.setSnCode("1510312407397859686");
        protocolConfigMaster.setProtocolVersion("123456");
        protocolConfigMaster.setIsUsed(1);
        protocolConfigMaster.setUsedTime(DateUtil.getDate());

        ProtocolConfigMaster result = repository.save(protocolConfigMaster);

        Assert.assertNotNull(result);

    }

    @Test
    public void findBySnCodeAndIsUsed() throws Exception {
        ProtocolConfigMaster result = repository.findBySnCodeAndIsUsed("1510730959647775198",1);

        Assert.assertNotNull(result);
    }

    @Test
    public void findBySnCodeAndProtocolVersion() throws Exception {
        ProtocolConfigMaster result = repository.findBySnCodeAndProtocolVersion("1510730959647775198","123456");

        Assert.assertNotNull(result);
    }

}