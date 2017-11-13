package com.cherry.repository;

import com.cherry.dataobject.DeviceVerify;
import com.cherry.util.DateUtil;
import com.cherry.util.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 设备查找校验表DAO层测试
 * Created by Administrator on 2017/11/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceVerifyRepositoryTest {

    @Autowired
    private DeviceVerifyRepository deviceVerifyRepository;

    /**
     * 储存设备校验记录
     */
    @Test
    public void saveVerify(){

        DeviceVerify deviceVerify = new DeviceVerify();
        deviceVerify.setId(KeyUtil.genUniqueKey());
        deviceVerify.setSnCode(KeyUtil.genUniqueKey());
        deviceVerify.setCheckCode("34fr6t");
        deviceVerify.setGenerateTime(DateUtil.getDate());

        //boolean b = DateUtil.ifDateValid(deviceVerify.getGenerateTime());

        DeviceVerify result = deviceVerifyRepository.save(deviceVerify);

        Assert.assertNotNull(result);

    }

    /**
     * 通过SN码查询设备校验记录
     * @throws Exception
     */
    @Test
    public void findBySnCode() throws Exception {
        DeviceVerify result = deviceVerifyRepository.findBySnCode("1510308680143773784");

        Assert.assertNotNull(result);

    }

}