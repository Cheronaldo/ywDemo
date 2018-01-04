package com.cherry.repository;

import com.cherry.dataobject.AgencySiteRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 经销商 现场 用户从属关系表 DAO层
 * Created by Administrator on 2018/01/04.
 */
public interface AgencySiteRelationshipRepository extends JpaRepository<AgencySiteRelationship, String>{

    /**
     * 通过 经销商用户名 启用标志查询记录
     * @param agencyUserName
     * @param isUsed
     * @return
     */
    List<AgencySiteRelationship> findByAgencyUserNameAndIsUsed(String agencyUserName, Integer isUsed);

    /**
     * 通过现场用户名查询 从属关系记录
     * @param siteUserName
     * @return
     */
    AgencySiteRelationship findBySiteUserName(String siteUserName);


}
