package com.cherry.service.impl;

import com.cherry.form.AlarmQueryForm;
import com.cherry.form.AlarmUpdateForm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

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
        form.setOldDate("");
        form.setNewDate("");

        PageRequest request = new PageRequest(0, 2);

        Map<String, Object> result = service.getRecordList(form, request);

        Assert.assertNotEquals(0, result.get("records"));

    }

    @Test
    public void updateRecord() throws Exception {

        AlarmUpdateForm form = new AlarmUpdateForm();
        form.setId("1513427918946975748");
        form.setHandleStatus("已处理");
        form.setSnCode("HMITest002");
        form.setProtocolVersion("ywv1.1");
        form.setOldDate("");
        form.setNewDate("");
        form.setHandleResult("警报解除");

        PageRequest request = new PageRequest(0, 2);

        Map<String, Object> result = service.updateRecord(form, request);

        Assert.assertNotEquals(0, result.get("records"));

    }

}