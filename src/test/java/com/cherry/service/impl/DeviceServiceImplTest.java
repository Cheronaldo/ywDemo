package com.cherry.service.impl;

import com.cherry.dataobject.DeviceInfo;
import com.cherry.dto.SiteDeviceInfoDTO;
import com.cherry.form.SiteDeviceForm;
import com.cherry.util.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.image.Kernel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * 设备service层测试
 * Created by Administrator on 2017/11/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceServiceImplTest {

    @Autowired
    private DeviceServiceImpl service;

    @Test
    public void checkSiteDeviceIsOnHand() throws Exception {
        Map<String,Object> map = service.checkSiteDeviceIsOnHand("3","34fr6k","abc1234");

        int code = Integer.parseInt(String.valueOf(map.get("code")));
        String msg = (String)map.get("msg");
        Object data = map.get("data");

        Assert.assertNotNull(msg);
    }

    @Test
    public void saveSiteUserDeviceInfo() throws Exception {

        SiteDeviceForm siteDeviceForm = new SiteDeviceForm();
        siteDeviceForm.setUserName("abc1234");
        siteDeviceForm.setSnCode("1510311999826615905");
        siteDeviceForm.setDeviceAddress("武汉大学");
        siteDeviceForm.setDeviceLongitude("114.368107");
        siteDeviceForm.setDeviceLatitude("30.543083");
        siteDeviceForm.setSiteType("饲料生产");
        siteDeviceForm.setSiteName("车间搅拌线");
        siteDeviceForm.setSiteIcon("/12345");
        siteDeviceForm.setIsAdapt(1);
        siteDeviceForm.setProtocolVersion("abc12345");
        siteDeviceForm.setProtocolContent("PH值");// 注意若协议内容只有一个值 即没有 _ 隔开的情况

        int result = service.saveSiteUserDeviceInfo(siteDeviceForm);

       Assert.assertEquals(0,result);

    }

    @Test
    public void siteUserDeviceRegister() throws Exception{
        SiteDeviceForm siteDeviceForm = new SiteDeviceForm();
        siteDeviceForm.setUserName("abc1234");
        siteDeviceForm.setSnCode("1510311999826615905");
        siteDeviceForm.setDeviceAddress("武汉大学");
        siteDeviceForm.setDeviceLongitude("114.368107");
        siteDeviceForm.setDeviceLatitude("30.543083");
        siteDeviceForm.setSiteType("饲料生产");
        siteDeviceForm.setSiteName("车间搅拌线");
        siteDeviceForm.setSiteIcon("/12345");
        siteDeviceForm.setIsAdapt(1);
        siteDeviceForm.setProtocolVersion("abc12345");
        siteDeviceForm.setProtocolContent("PH值");

        int result = service.siteUserDeviceRegister(siteDeviceForm);

        Assert.assertEquals(0, result);
    }

    @Test
    public void userDeviceUnbind() throws Exception {

        Map<String,Object> map = new HashMap<String,Object>();

        map = service.userDeviceUnbind("1510368792904640171","abc1234");

        int code = Integer.parseInt(String.valueOf(map.get("code")));
        String msg = (String)map.get("msg");


        Assert.assertNotNull(msg);


    }

    @Test
    public void listFindByUser() throws Exception {

        List<DeviceInfo> result = service.listFindByUser("cherry1");
        Assert.assertNotEquals(0,result.size());

    }

    @Test
    public void getStatusBySnCode() throws Exception {

        int result = service.getStatusBySnCode("1510543259454686637");

        Assert.assertEquals(0,result);

    }

}