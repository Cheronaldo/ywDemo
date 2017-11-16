package com.cherry.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 用户级别信息修改 表单验证
 * 不包括对密码修改
 * Created by Administrator on 2017/11/16.
 */
public class UserUpdateForm {
    /**  用户名 */
    @NotEmpty(message = "用户名必传")
    private String userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    @Override
    public String toString() {
        return "UserUpdateForm{" +
                "userName='" + userName + '\'' +
                ", userClass='" + userClass + '\'' +
                ", userPost='" + userPost + '\'' +
                ", userMail='" + userMail + '\'' +
                ", userCompany='" + userCompany + '\'' +
                ", userTelephone='" + userTelephone + '\'' +
                '}';
    }
}
