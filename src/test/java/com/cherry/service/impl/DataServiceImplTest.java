package com.cherry.service.impl;

import com.cherry.dataobject.HistoricalData;
import com.cherry.form.AllDataQueryForm;
import com.cherry.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * 设备数据service层测试
 * Created by Administrator on 2017/12/01.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataServiceImplTest {

    @Autowired
    private DataServiceImpl service;

    @Test
    public void listGetAll() throws Exception {

        Date oldDate = DateUtil.convertString2Date("2017-12-01 21:51:00");
        Date newDate = DateUtil.convertString2Date("2017-12-01 21:53:00");

        AllDataQueryForm form = new AllDataQueryForm();
        form.setSnCode("1511962658712691673");
        form.setProtocolVersion("yw123");
        form.setOldDate(oldDate);
        form.setNewDate(newDate);

        PageRequest request = new PageRequest(0, 5);

        Page<HistoricalData> result = service.listGetAll(form, request);


        Assert.assertNotEquals(0, result.getTotalPages());
    }

    @Test
    public void listGetOne() throws Exception {
    }

}