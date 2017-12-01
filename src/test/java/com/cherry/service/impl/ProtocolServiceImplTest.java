package com.cherry.service.impl;

import com.cherry.dataobject.ProtocolConfigDetail;
import com.cherry.dataobject.ProtocolDetail;
import com.cherry.dto.ProtocolAdaptDTO;
import com.cherry.form.ProtocolDetailForm;
import com.cherry.form.ProtocolQueryForm;
import com.cherry.util.KeyUtil;
import com.cherry.vo.RealTimeProtocolVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

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
        queryForm.setUserName("abc1234");
        queryForm.setSnCode("1511962658712691673");
        queryForm.setIsAdapt(0);
        queryForm.setProtocolVersion("yw123");
        queryForm.setProtocolContent("温度_湿度_浓度");

        PageRequest request = new PageRequest(0, 5);
        Map<String,Object> result = service.listFindCurrentBySnCode(queryForm, request);

        Assert.assertNotEquals(0,result.get("records"));

    }

    @Test
    public void updateProtocolDetail() throws Exception {
        ProtocolDetailForm form = new ProtocolDetailForm();
        //form.setId("1510732127485461440");
        form.setUserName("abc1234");
        form.setSnCode("1511962658712691673");
        form.setProtocolVersion("yw123");
        form.setOffsetNumber(1);
        form.setIsVisible(0);
        form.setIsAlarmed(0);

        PageRequest request = new PageRequest(0, 2);
        Map<String,Object> result = service.updateProtocolDetail(form, request);

        Assert.assertNotEquals(0,result.get("records"));
    }

    @Test
    public void getProtocolVersionBySnCode() throws Exception {
        String result = service.getProtocolVersionBySnCode("1511962658712691673");

        Assert.assertNotNull(result);
    }

    @Test
    public void protocolAdapt() throws Exception {

        ProtocolAdaptDTO protocolAdaptDTO = new ProtocolAdaptDTO();
        protocolAdaptDTO.setUserName("abc1234");
        protocolAdaptDTO.setSnCode("1511962658712691673");
        protocolAdaptDTO.setProtocolVersion("yw123");
        protocolAdaptDTO.setProtocolContent("温度_湿度_浓度");

        int result = service.protocolAdapt(protocolAdaptDTO);

        Assert.assertEquals(0,result);

    }

    @Test
    public void listForRealTimeDisplay() throws Exception{
        List<RealTimeProtocolVO> result = service.listForRealTimeDisplay("abc1234","1511962658712691673", "yw123");

        Assert.assertNotEquals(0, result.size());
    }

}