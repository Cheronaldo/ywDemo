package com.cherry.repository;

import com.cherry.dataobject.DeviceInfo;
import com.cherry.dataobject.UserInfo;
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
import java.util.List;

import static org.junit.Assert.*;

/**
 * 设备基本信息DAO层测试
 * Created by Administrator on 2017/11/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceInfoRepositoryTest {

    @Autowired
    private DeviceInfoRepository repository;

    @Test
    public void saveDevice(){
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setSnCode("HMITest012");
        deviceInfo.setDeviceMac(KeyUtil.genUniqueKey());
        deviceInfo.setDeviceType(1);
        deviceInfo.setDeviceModel("HMI0275");
        deviceInfo.setShipmentNumber(KeyUtil.genUniqueKey());
        deviceInfo.setResearchUnit("中国移动");

        DeviceInfo result = repository.save(deviceInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOneBySnCode(){
        DeviceInfo result = repository.findOne("1510311999826615905");
        Assert.assertNotNull(result);
    }

    @Test
    public void findBySnCodeIn() throws Exception {
        List<String> list = Arrays.asList("1510311999826615905","1510312472692722083");
        List<DeviceInfo> result = repository.findBySnCodeIn(list);

        Assert.assertNotEquals(0,result.size());
    }

    @Test
    public void getPage() throws Exception {

        PageRequest request = new PageRequest(1, 2);

        List<String> snCodeList = Arrays.asList("HMITest001","HMITest002","HMITest003","HMITest004");

        Page<DeviceInfo> result = repository.findBySnCodeIn(snCodeList, request);

        Assert.assertNotEquals(0, result.getTotalElements());

    }

}