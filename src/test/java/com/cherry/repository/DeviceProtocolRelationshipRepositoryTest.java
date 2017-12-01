package com.cherry.repository;

import com.cherry.dataobject.DeviceProtocolRelationship;
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
 * 设备协议关系表DAO层测试
 * Created by Administrator on 2017/11/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceProtocolRelationshipRepositoryTest {

    @Autowired
    private DeviceProtocolRelationshipRepository relationshipRepository;

    @Test
    public void saveRelationship() throws Exception{

        DeviceProtocolRelationship relationship = new DeviceProtocolRelationship();
        relationship.setId(KeyUtil.genUniqueKey());
        relationship.setSnCode("1511962658712691673");
        relationship.setProtocolVersion("yw1234");
        relationship.setIsUsed(0);
        relationship.setUsedTime(DateUtil.getDate());

        DeviceProtocolRelationship result = relationshipRepository.save(relationship);

        Assert.assertNotNull(result);


    }

    @Test
    public void findBySnCodeAndIsUsed() throws Exception {

        DeviceProtocolRelationship result = relationshipRepository.findBySnCodeAndIsUsed("1511962658712691673",1);

        Assert.assertNotNull(result);
    }

    @Test
    public void findBySnCodeAndProtocolVersion() throws Exception {

        DeviceProtocolRelationship result = relationshipRepository.findBySnCodeAndProtocolVersion("1511962658712691673", "yw123");

        Assert.assertNotNull(result);

    }

}