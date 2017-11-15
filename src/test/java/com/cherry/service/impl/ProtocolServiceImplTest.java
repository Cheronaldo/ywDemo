package com.cherry.service.impl;

import com.cherry.dataobject.ProtocolConfigDetail;
import com.cherry.form.ProtocolDetailForm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 协议配置Service层测试
 * Created by Administrator on 2017/11/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProtocolServiceImplTest {

    @Autowired
    private ProtocolServiceImpl service;

    @Test
    public void listFindCurrentBySnCode() throws Exception {

        List<ProtocolConfigDetail> result = service.listFindCurrentBySnCode("1510730959647775198");

        Assert.assertNotEquals(0,result.size());

    }

    @Test
    public void updateProtocolDetail() throws Exception {
        ProtocolDetailForm form = new ProtocolDetailForm();
        form.setId("1510732127485461440");
        form.setSnCode("1510730959647775198");
        form.setProtocolVersion("1234");
        form.setOffsetNumber(2);
        form.setDataName("湿度");
        form.setIsVisible(1);
        form.setIsAlarmed(1);

        List<ProtocolConfigDetail> result = service.updateProtocolDetail(form);

        Assert.assertNotEquals(0,result.size());
    }

    @Test
    public void getProtocolVersionBySnCode() throws Exception {
        String result = service.getProtocolVersionBySnCode("1510730959647775198");

        Assert.assertNotNull(result);
    }

    @Test
    public void protocolReAdapt() throws Exception {
    }

}