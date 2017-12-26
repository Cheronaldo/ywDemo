package com.cherry.repository;

import com.cherry.dataobject.AlarmRecord;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 报警记录表DAO层测试
 * Created by Administrator on 2017/12/07.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AlarmRecordRepositoryTest {

    @Autowired
    private AlarmRecordRepository recordRepository;

    @Test
    public void saveRecord() throws Exception {
        AlarmRecord record = new AlarmRecord();
        record.setId(KeyUtil.genUniqueKey());
        record.setSnCode("HMITest001");
        record.setProtocolVersion("ywv1.1");
        record.setOffsetNumber(1);
        record.setActualValue("28");
        record.setDownThreshold("10");
        record.setUpThreshold("20");
        record.setAlarmCode(1);
        record.setAlarmTime(DateUtil.getDate());
        record.setHandleStatus(0);
        record.setHandleResult("无");
        record.setIsChecked(0);

        AlarmRecord result = recordRepository.save(record);

        Assert.assertNotNull(result);
    }

    @Test
    public void findBySnCodeAndAlarmTimeBetweenOrderByIdDesc() throws Exception {

        Date oldDate = DateUtil.convertString2Date("2017-01-01 00:00:00");
        Date newDate = DateUtil.convertString2Date("2017-12-16 21:52:40");

        PageRequest request = new PageRequest(0, 5);

        Page<AlarmRecord> result = recordRepository.findBySnCodeAndProtocolVersionAndHandleResultStartingWithAndAlarmTimeBetweenOrderByIdDesc("HMITest002",
                "ywv1.1",
                "无",
                oldDate,
                newDate,
                request);

        Assert.assertNotEquals(0, result.getTotalPages());

    }

    @Test
    public void findBySnCodeInOrderByAlarmTimeDesc() throws Exception{

        List<String> snCodeList = Arrays.asList("HMITest001","HMITest002");

        PageRequest request = new PageRequest(0, 3);

        Page<AlarmRecord> result = recordRepository.findBySnCodeInOrderByAlarmTimeDesc(snCodeList, request);

        Assert.assertNotEquals(0, result.getTotalElements());


    }

    @Test
    public void countBySnCodeInAndIsChecked() throws Exception{

        List<String> snCodeList = Arrays.asList("HMITest001","HMITest002");

        long number = recordRepository.countBySnCodeInAndIsChecked(snCodeList, 0);

        Assert.assertNotEquals(0, number);

    }

}