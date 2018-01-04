package com.cherry.repository;

import com.cherry.dataobject.AgencySiteRelationship;
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
 * 经销商 现场 用户 从属关系表 DAO层测试
 * Created by Administrator on 2018/01/04.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AgencySiteRelationshipRepositoryTest {

    @Autowired
    private AgencySiteRelationshipRepository repository;

    @Test
    public void save() throws Exception{

        AgencySiteRelationship relationship = new AgencySiteRelationship();

        relationship.setId(KeyUtil.genUniqueKey());
        relationship.setAgencyUserName("Test002");
        relationship.setSiteUserName("abc");
        relationship.setIsUsed(1);
        relationship.setRegisterTime(DateUtil.getDate());

        AgencySiteRelationship result = repository.save(relationship);

        Assert.assertNotNull(result);

    }


    @Test
    public void findByAgencyUserNameAndIsUsed() throws Exception {

        List<AgencySiteRelationship> result = repository.findByAgencyUserNameAndIsUsed("Test001", 1);

        Assert.assertNotEquals(0, result.size());

    }

    @Test
    public void findBySiteUserName() throws Exception {
        AgencySiteRelationship result = repository.findBySiteUserName("abc123");

        Assert.assertNotNull(result);
    }

}