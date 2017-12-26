package com.cherry.service.impl;

import com.cherry.form.AlarmQueryForm;
import com.cherry.form.AlarmRuleAddForm;
import com.cherry.form.AlarmRuleUpdateForm;
import com.cherry.form.AlarmUpdateForm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * 设备报警service层测试
 * Created by Administrator on 2017/12/07.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AlarmServiceImplTest {

    @Autowired
    private AlarmServiceImpl service;

    @Test
    public void getRecordList() throws Exception {

        AlarmQueryForm form = new AlarmQueryForm();
        form.setSnCode("HMITest002");
        form.setProtocolVersion("ywv1.1");
        form.setAlarmHandled(true);
        form.setOldDate("");
        form.setNewDate("");

        PageRequest request = new PageRequest(0, 5);

        Map<String, Object> result = service.getRecordList(form, request);

        Assert.assertNotEquals(0, result.get("records"));

    }

    @Test
    public void updateRecord() throws Exception {

        AlarmUpdateForm form = new AlarmUpdateForm();
        form.setId("1513427795742360861");
        form.setHandleStatus("已处理");
        form.setSnCode("HMITest002");
        form.setProtocolVersion("ywv1.1");
        form.setAlarmHandled(true);
        form.setOldDate("");
        form.setNewDate("");
        form.setHandleResult("报警解除");

        PageRequest request = new PageRequest(0, 5);

        Map<String, Object> result = service.updateRecord(form, request);

        Assert.assertNotEquals(0, result.get("records"));

    }

    @Test
    public void getThresholdList() throws Exception{

        PageRequest request = new PageRequest(0, 5);

        Map<String, Object> result = service.getThresholdList("HMITest002", "ywv1.1", request);

        Assert.assertNotNull(result.get("rows"));


    }

    @Test
    public void getThresholdSingle() throws Exception{

        Map<String, Object> result = service.getThresholdSingle("ywv1.1", 1);

        Assert.assertNotNull(result.get("minThreshold"));

    }

    @Test
    public void updateThreshold() throws Exception{

        AlarmRuleUpdateForm form = new AlarmRuleUpdateForm();
        form.setId("1513430723709140533");
        form.setDownThreshold("10");
        form.setUpThreshold("20");

        PageRequest request = new PageRequest(0,5);

        Map<String, Object> result = service.updateThreshold(form, request);

        Assert.assertNotNull(result.get("rows"));

    }

    @Test
    public void getThresholdLimitList() throws Exception{

        Map<String, Object> result = service.getThresholdLimitList("ywv1.1");

        Assert.assertNotNull(result.get("data"));

    }

    @Test
    public void addThreshold() throws Exception{

        AlarmRuleAddForm form = new AlarmRuleAddForm();
        form.setSnCode("HMITest002");
        form.setProtocolVersion("ywv1.1");
        form.setOffsetNumber(2);
        form.setDownThreshold("10");
        form.setUpThreshold("30");

        PageRequest request = new PageRequest(0, 5);

        Map<String, Object> result = service.addThreshold(form, request);

        Assert.assertNotNull(result.get("rows"));

    }

    @Test
    public void getAllDeviceAlarmRecord() throws Exception{

       // List<String> snCodeList = Arrays.asList("HMITest001","HMITest002");

        PageRequest request = new PageRequest(0, 3);

        Map<String, Object> result = service.getAllDeviceAlarmRecord("Test001", request);

        Assert.assertNotEquals(0, result.get("unreadNum"));

    }

    @Test
    public void decreaseUncheckedNumber() throws Exception{


        Map<String, Object> map = service.decreaseUncheckedNumber("15142587331681329641");

        Assert.assertNotNull(map);

    }

}