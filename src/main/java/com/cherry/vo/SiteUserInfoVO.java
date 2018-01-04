package com.cherry.vo;

/**
 * 现场用户信息 视图对象
 * Created by Administrator on 2018/01/04.
 */
public class SiteUserInfoVO {

    /**  用户名 */
    private String userName;
    /**  用户邮箱 */
    private String userMail;
    /**  用户公司 */
    private String userCompany;
    /**  用户手机号 */
    private String userTelephone;
    /**  用户真实姓名 */
    private String realName;
    /**  现场类型 */
    private String industryType;

    public SiteUserInfoVO(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    @Override
    public String toString() {
        return "SiteUserInfoVO{" +
                "userName='" + userName + '\'' +
                ", userMail='" + userMail + '\'' +
                ", userCompany='" + userCompany + '\'' +
                ", userTelephone='" + userTelephone + '\'' +
                ", realName='" + realName + '\'' +
                ", industryType='" + industryType + '\'' +
                '}';
    }
}
