package com.cherry.dataobject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 用户等级代码表
 * Created by Administrator on 2017/11/09.
 */
@Entity
public class UserLevel {

    /** 自增ID  */
    @Id
    @GeneratedValue
    private Integer id;
    /** 用户等级代码 */
    private Integer userClass;
    /** 用户等级具体内容 */
    private String classInfo;

    public UserLevel(){}

    public UserLevel(Integer userClass, String classInfo){
        this.userClass = userClass;
        this.classInfo = classInfo;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserClass() {
        return userClass;
    }

    public void setUserClass(Integer userClass) {
        this.userClass = userClass;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }

    @Override
    public String toString() {
        return "UserLevel{" +
                "id=" + id +
                ", userClass=" + userClass +
                ", classInfo='" + classInfo + '\'' +
                '}';
    }
}
