package com.cherry.repository;

import com.cherry.dataobject.UserDeviceRelationship;
import com.cherry.util.DateUtil;
import com.cherry.util.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 用户设备关系表DAO层测试
 * Created by Administrator on 2017/11/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDeviceRelationshipRepositoryTest {

    @Autowired
    private UserDeviceRelationshipRepository relationshipRepository;

    @Test
    public void addRelationship(){
        UserDeviceRelationship relationship = new UserDeviceRelationship();
        relationship.setId(KeyUtil.genUniqueKey());
        relationship.setSnCode(KeyUtil.genUniqueKey());
        relationship.setUserName("abc1234");
        relationship.setRegisterTime(DateUtil.getDate());
        relationship.setIsUsed(0);

        UserDeviceRelationship result = relationshipRepository.save(relationship);
        Assert.assertNotNull(result);
    }

    @Test
    public void findBySnCodeAndUserName() throws Exception {

        UserDeviceRelationship result = relationshipRepository.findBySnCodeAndUserName("1510312472692722083","abc12345");

        Assert.assertNotNull(result);
    }

    @Test
    public void findByIsUsedAndUserName() throws Exception {
        List<UserDeviceRelationship> result = relationshipRepository.findByIsUsedAndUserName(1,"abc1234");

        Assert.assertNotEquals(0,result.size());
    }

}