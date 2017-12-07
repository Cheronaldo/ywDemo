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

import java.util.Date;

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
        record.setSnCode("1511962706665703659");
        record.setProtocolVersion("yw123");
        record.setOffsetNumber(1);
        record.setAlarmCode(2);
        record.setHandleStatus(0);
        record.setAlarmTime(DateUtil.getDate());

        AlarmRecord result = recordRepository.save(record);

        Assert.assertNotNull(result);
    }

    @Test
    public void findBySnCodeAndAlarmTimeBetweenOrderByIdDesc() throws Exception {

        Date oldDate = DateUtil.convertString2Date("2017-01-01 00:00:00");
        Date newDate = DateUtil.convertString2Date("2017-12-07 21:52:40");

        PageRequest request = new PageRequest(0, 5);

        Page<AlarmRecord> result = recordRepository.findBySnCodeAndProtocolVersionAndAlarmTimeBetweenOrderByIdDesc("1510311999826615905",
                "yw123",
                oldDate,
                newDate,
                request);

        Assert.assertNotEquals(0, result.getTotalPages());

    }

}