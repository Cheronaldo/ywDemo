package com.cherry.service.impl;

import com.cherry.dataobject.HistoricalData;
import com.cherry.form.AllDataQueryForm;
import com.cherry.form.SingleDataQueryForm;
import com.cherry.util.DateUtil;
import com.cherry.vo.HistoricalDataVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

        AllDataQueryForm form = new AllDataQueryForm();
        form.setSnCode("1511962658712691673");
        form.setProtocolVersion("yw123");
        form.setOldDate("2017-12-01 21:51:00");
        form.setNewDate("2017-12-01 21:53:00");

        PageRequest request = new PageRequest(0, 1);

        Map<String, Object> result = service.listGetAll(form, request);


        Assert.assertNotEquals(0, result.get("total"));
    }

    @Test
    public void listGetOne() throws Exception {

        SingleDataQueryForm form = new SingleDataQueryForm();
        form.setSnCode("1511962658712691673");
        form.setProtocolVersion("yw123");
        form.setOffsetNumber("2");
        form.setOldDate("");
        form.setNewDate("");

        List<HistoricalDataVO> result = service.listGetOne(form);

        Assert.assertNotEquals(0, result.size());
    }


    @Test
    public void exportExcel() throws Exception{

        AllDataQueryForm form = new AllDataQueryForm();
        form.setSnCode("HMITest003");
        form.setProtocolVersion("ywv1.1");
        form.setOldDate("2017-12-20 15:09:00");
        form.setNewDate("2017-12-24 15:09:00");

        String headers = "温度℃_湿度%";

        String result = service.exportExcel(form, headers, "Test001");

        Assert.assertNotNull(result);

    }

}