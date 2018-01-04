package com.cherry.repository;

import com.cherry.dataobject.DeviceStatus;
import com.cherry.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 设备状态表DAO层测试
 * Created by Administrator on 2017/11/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceStatusRepositoryTest {

    @Autowired
    private DeviceStatusRepository repository;

    @Test
    public void addStatus(){
        DeviceStatus deviceStatus = new DeviceStatus();
        deviceStatus.setSnCode("HMITest012");
        deviceStatus.setIsOnline(0);
        deviceStatus.setHeartTime(DateUtil.getDate());

        DeviceStatus result = repository.save(deviceStatus);
        Assert.assertNotNull(result);
    }

    @Test
    public void findBySnCode(){
        DeviceStatus result = repository.findOne("1510543259454686637");

        Assert.assertNotNull(result);


    }

}