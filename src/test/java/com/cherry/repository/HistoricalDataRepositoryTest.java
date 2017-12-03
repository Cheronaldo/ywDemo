package com.cherry.repository;

import com.cherry.dataobject.HistoricalData;
import com.cherry.util.DateUtil;
import com.cherry.util.KeyUtil;
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

import static org.junit.Assert.*;

/**
 * 历史数据DAO层测试
 * Created by Administrator on 2017/12/01.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HistoricalDataRepositoryTest {

    @Autowired
    private HistoricalDataRepository repository;

    @Test
    public void saveData() throws Exception{
        HistoricalData data = new HistoricalData();
        data.setId(KeyUtil.genUniqueKey());
        data.setSnCode("1511962658712691673");
        data.setProtocolVersion("yw1234");
        data.setDeviceData("2.1_5.1");
        data.setDataTime(DateUtil.getDate());

        HistoricalData result = repository.save(data);

        Assert.assertNotNull(result);
    }

    @Test
    public void findBySnCodeAndProtocolVersionAndDataTime() throws Exception {
        Date oldDate = DateUtil.convertString2Date("2017-01-01 00:00:00");
        Date newDate = DateUtil.convertString2Date("2017-12-01 21:52:40");

        PageRequest request = new PageRequest(0, 5);

        Page<HistoricalData> result = repository.findBySnCodeAndProtocolVersionAndDataTime("1511962658712691673",
                "yw123",
                oldDate,
                newDate,
                request);


        Assert.assertNotEquals(0, result.getTotalPages());


    }

    @Test
    public void findBySnCodeAndProtocolVersionAndDataTimeBetweenOrderByIdDesc() throws Exception{

        Date oldDate = DateUtil.convertString2Date("2017-01-01 00:00:00");
        Date newDate = DateUtil.convertString2Date("2017-12-01 21:52:40");

        PageRequest request = new PageRequest(0, 5);

        Page<HistoricalData> result = repository.findBySnCodeAndProtocolVersionAndDataTimeBetweenOrderByIdDesc("1511962658712691673",
                "yw123",
                oldDate,
                newDate,
                request);

        Assert.assertNotEquals(0, result.getTotalPages());

    }



    @Test
    public void findBySnCodeAndProtocolVersionAndDataTimeBetween() throws Exception {

        Date oldDate = DateUtil.convertString2Date("2017-12-01 21:51:30");
        Date newDate = DateUtil.convertString2Date("2017-12-01 21:52:35");

        List<HistoricalData> result = repository.findBySnCodeAndProtocolVersionAndDataTimeBetween("1511962658712691673", "yw123", oldDate, newDate);

        Assert.assertNotEquals(0, result.size());

    }

}