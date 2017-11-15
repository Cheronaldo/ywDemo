package com.cherry.repository;

import com.cherry.dataobject.ProtocolConfigDetail;
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
 * 设备协议配置祥表DAO层测试
 * Created by Administrator on 2017/11/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProtocolConfigDetailRepositoryTest {

    @Autowired
    private ProtocolConfigDetailRepository repository;

    @Test
    public void saveProtocolDetail(){

        ProtocolConfigDetail protocolConfigDetail = new ProtocolConfigDetail();
        protocolConfigDetail.setId(KeyUtil.genUniqueKey());
        protocolConfigDetail.setSnCode("1510730959647775198");
        protocolConfigDetail.setProtocolVersion("1234");
        protocolConfigDetail.setOffsetNumber(1);
        protocolConfigDetail.setDataName("温度");
        protocolConfigDetail.setIsVisible(0);
        protocolConfigDetail.setIsAlarmed(1);

        ProtocolConfigDetail result = repository.save(protocolConfigDetail);

        Assert.assertNotNull(result);

    }

    @Test
    public void findOneById(){
        ProtocolConfigDetail result = repository.findOne("1510732023327895991");

        Assert.assertNotNull(result);
    }

    @Test
    public void findBySnCodeAndProtocolVersion() throws Exception {

        List<ProtocolConfigDetail> result = repository.findBySnCodeAndProtocolVersion("1510730959647775198","1234");

        Assert.assertNotEquals(0,result.size());
    }

}