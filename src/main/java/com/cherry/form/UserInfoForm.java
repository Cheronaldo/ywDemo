package com.cherry.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 用户注册表单验证
 * Created by Administrator on 2017/11/07.
 */
public class UserInfoForm {

    /**  用户名 */
    @NotEmpty(message = "用户名必传")
    private String userName;
    /**  用户密码 (是否删除待定)*/
    private String userPassword;
    /**  用户类型 */
    private String userClass;
    /**  用户职务 前端是字符串 后台转化为code*/
    private String userPost;
    /**  用户邮箱 */
    private String userMail;
    /**  用户公司 */
    private String userCompany;
    /**  用户手机号 */
    private String userTelephone;
    /**  用户真实姓名 */
    private String realName;
    /**  用户地址 */
    private String userAddress;
    /**  现场类型 */
    private String industryType;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public String getUserPost() {
        return userPost;
    }

    public void setUserPost(String userPost) {
        this.userPost = userPost;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public String getUserTelephone() {
        return userTelephone;
    }

    public void setUserTelephone(String userTelephone) {
        this.userTelephone = userTelephone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    @Override
    public String toString() {
        return "UserInfoForm{" +
                "userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userClass='" + userClass + '\'' +
                ", userPost='" + userPost + '\'' +
                ", userMail='" + userMail + '\'' +
                ", userCompany='" + userCompany + '\'' +
                ", userTelephone='" + userTelephone + '\'' +
                ", realName='" + realName + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", industryType='" + industryType + '\'' +
                '}';
    }
}
