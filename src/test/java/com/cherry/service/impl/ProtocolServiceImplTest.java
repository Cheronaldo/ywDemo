package com.cherry.service.impl;

import com.cherry.dataobject.ProtocolConfigDetail;
import com.cherry.dto.ProtocolAdaptDTO;
import com.cherry.form.ProtocolDetailForm;
import com.cherry.form.ProtocolQueryForm;
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

        ProtocolQueryForm queryForm = new ProtocolQueryForm();
        queryForm.setSnCode("1510311999826615905");
        queryForm.setIsAdapt(1);
        queryForm.setProtocolVersion("1234abc");
        queryForm.setProtocolContent("温度_湿度_压强");

        PageRequest request = new PageRequest(0, 1);
        Page<ProtocolConfigDetail> result = service.listFindCurrentBySnCode(queryForm, request);

        Assert.assertNotEquals(0,result.getSize());

    }

    @Test
    public void updateProtocolDetail() throws Exception {
        ProtocolDetailForm form = new ProtocolDetailForm();
        form.setId("1510732127485461440");
//        form.setSnCode("1510730959647775198");
//        form.setProtocolVersion("1234");
//        form.setOffsetNumber(2);
//        form.setDataName("湿度");
        form.setIsVisible(1);
        form.setIsAlarmed(1);

        PageRequest request = new PageRequest(0, 2);
        Page<ProtocolConfigDetail> result = service.updateProtocolDetail(form, request);

        Assert.assertNotEquals(0,result.getSize());
    }

    @Test
    public void getProtocolVersionBySnCode() throws Exception {
        String result = service.getProtocolVersionBySnCode("1510730959647775198");

        Assert.assertNotNull(result);
    }

    @Test
    public void protocolAdapt() throws Exception {

        ProtocolAdaptDTO protocolAdaptDTO = new ProtocolAdaptDTO();
        protocolAdaptDTO.setSnCode("1510311999826615905");
        protocolAdaptDTO.setProtocolVersion("1234561");
        protocolAdaptDTO.setProtocolContent("温度_湿度_压强");

        int result = service.protocolAdapt(protocolAdaptDTO);

        Assert.assertEquals(0,result);

    }

}