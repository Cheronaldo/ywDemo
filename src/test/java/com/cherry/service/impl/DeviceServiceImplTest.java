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
        Map<String,Object> map = service.checkSiteDeviceIsOnHand("1510311999826615905","34fr6t","abc1234");

        int code = Integer.parseInt(String.valueOf(map.get("code")));
        String msg = (String)map.get("msg");
        Object data = map.get("data");

        Assert.assertNotNull(msg);
    }

    @Test
    public void saveSiteUserDeviceInfo() throws Exception {

        Map<String,Object> map = new HashMap<String,Object>();

        SiteDeviceInfoDTO siteDeviceInfoDTO = new SiteDeviceInfoDTO();
        siteDeviceInfoDTO.setSnCode("1510311999826615905");
        siteDeviceInfoDTO.setDeviceAddress("武汉大学");
        siteDeviceInfoDTO.setDeviceLongitude("114.368107");
        siteDeviceInfoDTO.setDeviceLatitude("30.543083");
        siteDeviceInfoDTO.setSiteType("饲料生产");
        siteDeviceInfoDTO.setSiteName("车间搅拌线");
        siteDeviceInfoDTO.setSiteIcon("/12345");

        map = service.saveSiteUserDeviceInfo(siteDeviceInfoDTO);
        int code = Integer.parseInt(String.valueOf(map.get("code")));
        String msg = (String)map.get("msg");


        Assert.assertNotNull(msg);

    }

    @Test
    public void saveUserDeviceRelationshipHandle() throws Exception {

        Map<String,Object> map = new HashMap<String,Object>();

        map = service.saveUserDeviceRelationshipHandle(KeyUtil.genUniqueKey(),"abc1234");

        int code = Integer.parseInt(String.valueOf(map.get("code")));
        String msg = (String)map.get("msg");


        Assert.assertNotNull(msg);

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

        List<DeviceInfo> result = service.listFindByUser("abc1234567");
        Assert.assertNotEquals(0,result.size());

    }

    @Test
    public void getStatusBySnCode() throws Exception {

        int result = service.getStatusBySnCode("1510543259454686637");

        Assert.assertEquals(0,result);

    }

}