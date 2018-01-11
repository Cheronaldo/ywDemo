package com.cherry.service;

import com.cherry.dataobject.IpStatus;
import com.cherry.dataobject.UserInfo;
import com.cherry.dataobject.UserLevel;
import com.cherry.form.UserInfoForm;
import com.cherry.form.UserUpdateForm;
import com.cherry.vo.UserInfoVO;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 用户信息服务接口
 * Created by Administrator on 2017/11/07.
 */
public interface UserInfoService {

    /**
     * 用户信息注册 (经销商)
     * 包括储存密码
     * @param userInfoForm
     * @return
     */
    Integer saveUser(UserInfoForm userInfoForm);


    /**
     * 用户登录
     * @param userName
     * @param userPassword
     * @return
     */
    Integer userLogin(String userName, String userPassword);

    /**
     * 通过用户名查询用户信息
     * 信息修改时返回给前端视图对象
     * @param userName
     * @return
     */
    UserInfoVO getUserInfoVOByUserName(String userName);

    /**
     * 通过用户名获取用户等级码
     * 用于用户名合法性校验 用户登录成功后的跳转
     * @param userName
     * @return
     */
    UserInfo getUserInfoByUserName(String userName);

    /**
     * 用户基本信息修改
     * 不包括修改密码
     * @param form
     * @return
     */
    Integer updateUserInfo(UserUpdateForm form);

    /**
     * 修改用户密码
     * @param userName
     * @param userPassword
     * @return
     */
    Integer updateUserPassword(String userName, String userPassword);

    /**
     * 通过经销商用户名 查询所有启用的 现场用户信息
     * 分页查询
     * @param userName
     * @param pageable
     * @return
     */
    Map<String, Object> getSiteUserList(String userName, Pageable pageable);

    /**
     * 经销商 注册 现场用户
     * @param form
     * @param agencyName
     * @return
     */
    Integer addSiteUser(UserInfoForm form, String agencyName);

    /**
     * 经销商 注销 现场用户
     * @param userName
     * @return
     */
    Integer unbindSiteUser(String userName);

    /**
     * 完成新用户的 IP信息录入
     * 异地IP用户的 踢人操作
     * @param userName
     * @param userIp
     * @return
     */
    Integer ipHandle(String userName, String userIp, HttpServletRequest request, HttpServletResponse response);

    /**
     * 通过获取用户名 用户IP 获取IP状态记录
     * @param userName
     * @param userIp
     * @return
     */
    Integer getIpStatus(String userName, String userIp);

    /**
     * 用户退出时将 IP置位
     * @param userName
     * @param userIp
     * @return
     */
    Integer ipReset(String userName, String userIp);

}
