package com.cherry.service.impl;

import com.cherry.constant.CookieConstant;
import com.cherry.constant.RedisConstant;
import com.cherry.converter.UserInfo2SiteUserInfoVOConverter;
import com.cherry.dataobject.AgencySiteRelationship;
import com.cherry.dataobject.IpStatus;
import com.cherry.dataobject.UserInfo;
import com.cherry.enums.UserEnum;
import com.cherry.form.UserInfoForm;
import com.cherry.form.UserUpdateForm;
import com.cherry.repository.AgencySiteRelationshipRepository;
import com.cherry.repository.IpStatusRepository;
import com.cherry.repository.UserInfoRepository;
import com.cherry.repository.UserLevelRepository;
import com.cherry.service.UserInfoService;
import com.cherry.util.CookieUtil;
import com.cherry.util.DateUtil;
import com.cherry.util.KeyUtil;
import com.cherry.util.MailUtil;
import com.cherry.vo.SiteUserInfoVO;
import com.cherry.vo.UserInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/11/07.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService{

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private UserLevelRepository levelRepository;

    @Autowired
    private AgencySiteRelationshipRepository relationshipRepository;

    @Autowired
    private IpStatusRepository ipStatusRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public Integer saveUser(UserInfoForm userInfoForm) {

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoForm, userInfo);
        if (userInfo.getUserClass() == null){
            // 说明是 经销商注册 默认为 级别是经销商
            userInfo.setUserClass(2);
        }else {
            // 说明是 用户修改
            //注意将用户类型转换为对应的 类型码再储存 否则userInfo中 userClass为 null
            int userClass = levelRepository.findByClassInfo(userInfoForm.getUserClass()).getUserClass();
            userInfo.setUserClass(userClass);
        }


        try {
            repository.save(userInfo);
        }catch (Exception e){
            return 1;
        }
        return 0;
    }

    @Override
    public Integer userLogin(String userName, String userPassword) {

        // 1.查询密码是否存在
        UserInfo userInfo = repository.findOne(userName);
        if (userInfo == null){
            //该用户名不存在
            return 1;
        }

        // 2.密码一致性校验
        if (!userPassword.equals(userInfo.getUserPassword())){
            //输入密码与数据库用户密码不一致
            return 1;
        }

        // 3.添加用户信息至session(放到controller层)

        return 0;
    }

    @Override
    public UserInfoVO getUserInfoVOByUserName(String userName) {

        UserInfo userInfo = repository.findOne(userName);
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userInfo, userInfoVO);
        userInfoVO.setUserClass(levelRepository.findByUserClass(userInfo.getUserClass()).getClassInfo());

        return userInfoVO;

    }

    @Override
    public UserInfo getUserInfoByUserName(String userName) {
        return repository.findOne(userName);
    }

    @Override
    public Integer updateUserInfo(UserUpdateForm form) {
        // 1.查询获取用户基本信息记录
        UserInfo userInfo = repository.findOne(form.getUserName());
        // 2.将表单属性赋给基本信息记录
        BeanUtils.copyProperties(form, userInfo);
        // 3.注意将用户类型转换为对应的 类型码再储存 否则userInfo中 userClass为 null
        int userClass = levelRepository.findByClassInfo(form.getUserClass()).getUserClass();
        userInfo.setUserClass(userClass);
        // 4.保存修改后的信息记录
        try {
            repository.save(userInfo);
        }catch (Exception e){
            return 1;
        }
        return 0;
    }

    @Override
    public Integer updateUserPassword(String userName, String userPassword) {
        // 1.查询获取用户基本信息记录
        UserInfo userInfo = repository.findOne(userName);
        // 2.修改密码
        userInfo.setUserPassword(userPassword);
        // 3.保存记录
        try {
            repository.save(userInfo);
        }catch (Exception e){
            return 1;
        }
        return 0;
    }

    @Override
    public Map<String, Object> getSiteUserList(String userName, Pageable pageable) {

        Map<String, Object> map = new HashMap<>();
        // 1.通过经销商用户名 获取 现场用户列表
        List<AgencySiteRelationship> relationshipList = relationshipRepository.findByAgencyUserNameAndIsUsed(userName, 1);
        if (relationshipList.size() == 0){
            // 没有记录
            map.put("code", 1);
            map.put("msg", UserEnum.NO_RECORDS_FOUND.getMessage());

            return map;
        }

        // 2.根据用户名列表 获取相关信息
        List<String> siteUserList = relationshipList.stream().map(e ->
                e.getSiteUserName()
        ).collect(Collectors.toList());

        Page<UserInfo> userInfoPage = repository.findByUserNameIn(siteUserList, pageable);

        if (userInfoPage.getContent() == null){
            // 没有记录
            map.put("code", 1);
            map.put("msg", UserEnum.NO_RECORDS_FOUND.getMessage());

            return map;
        }

        // 3.封装为 现场用户 VO对象
        List<SiteUserInfoVO> siteUserInfoVOList = UserInfo2SiteUserInfoVOConverter.convert(userInfoPage.getContent());

        map.put("code", 0);
        map.put("msg", UserEnum.GET_RECORDS_SUCCESS.getMessage());
        map.put("total", userInfoPage.getTotalPages());
        map.put("records", userInfoPage.getTotalElements());
        map.put("data", siteUserInfoVOList);

        return map;
    }

    @Override
    @Transactional
    public Integer addSiteUser(UserInfoForm form, String agencyName) {

        // 1.查询用户名是否存在且启用
        UserInfo userInfo = repository.findByUserNameAndIsUsed(form.getUserName(), 1);
        if (userInfo != null){
            // 用户名存在
            return 1;
        }

        // 2.生成6位随机码
        String initCode = KeyUtil.genRandomCode();

        // 3.MD5加密
        String encryptCode = KeyUtil.genMD5Code(initCode);

        // 4.添加用户信息表记录
        UserInfo userInfoOld = repository.findOne(form.getUserName());
        if (userInfoOld != null){
            // 用户名存在 修改信息 重新启用
            BeanUtils.copyProperties(form, userInfoOld);
            userInfoOld.setUserPassword(encryptCode);
            userInfoOld.setIsUsed(1);
            userInfoOld.setUserClass(3);
            repository.save(userInfoOld);
        } else {
            // 用户名不存在 添加用户信息表
            UserInfo userInfoNew = new UserInfo();
            BeanUtils.copyProperties(form, userInfoNew);
            userInfoNew.setUserPassword(encryptCode);
            userInfoNew.setIsUsed(1);
            userInfoNew.setUserClass(3);
            repository.save(userInfoNew);
        }

        // 5.添加经销商 现场 用户关系表 记录
        AgencySiteRelationship relationship = relationshipRepository.findBySiteUserName(form.getUserName());
        if (relationship != null){
            // 关系记录存在 修改信息 重新启用
            relationship.setAgencyUserName(agencyName);
            relationship.setIsUsed(1);
            relationship.setRegisterTime(DateUtil.getDate());
            relationshipRepository.save(relationship);

        } else {
            // 关系记录不存在 添加记录
            AgencySiteRelationship relationshipAdd = new AgencySiteRelationship();
            relationshipAdd.setId(KeyUtil.genUniqueKey());
            relationshipAdd.setSiteUserName(form.getUserName());
            relationshipAdd.setAgencyUserName(agencyName);
            relationshipAdd.setIsUsed(1);
            relationshipAdd.setRegisterTime(DateUtil.getDate());

            relationshipRepository.save(relationshipAdd);
        }

        // 6. 邮件发送用户名及初始密码
        int sendResult = MailUtil.sendMail(form.getUserMail(), form.getUserName(), initCode);

        return sendResult;
    }

    @Override
    @Transactional
    public Integer unbindSiteUser(String userName) {
        // 1.查询从属关系记录 并修改 启用标志
        AgencySiteRelationship relationship = relationshipRepository.findBySiteUserName(userName);
        if (relationship != null){
            // 置启用标志
            relationship.setIsUsed(0);

            relationshipRepository.save(relationship);
        }
        // 2.查询信息表 并修改 启用标志
        UserInfo userInfo = repository.findOne(userName);
        if (userInfo != null){
            // 置启用标志
            userInfo.setIsUsed(0);

            repository.save(userInfo);
        }

        return 0;
    }

    @Override
    @Transactional
    public Integer ipHandle(String userName, String userIp, HttpServletRequest request, HttpServletResponse response) {

        // 1.获取当前ip 是否存在已登录用户(即一个用户同一时间只允许使用一个IP登录)
        //   同一浏览器同一时间只允许一个用户登录
        //   同一IP的不同的浏览器可以登录多个用户
        List<IpStatus> ipStatus = ipStatusRepository.findByUserIpAndIsUsed(userIp, 1);
        if (ipStatus != null){
            // ip存在已登录用户
            // 1.1 判断cookie是否存在
            Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);

            if (cookie != null){
                // 1.2 再判断cookie中的用户是否登录（可能是被踢下的过期用户）
                String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
                // 判断cookie中的用户是否在线
                IpStatus cookieStatus= ipStatusRepository.findByUserNameAndUserIpAndIsUsed(tokenValue, userIp,1);
                if (cookieStatus != null){
                    // 该浏览器已存在登录用户
                    return 1;
                }else {
                    // 该用户的cookie无效 清除掉 redis及 cookie
                    // 1.3 清除redis
                    redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
                    // 1.4 清除cookie
                    CookieUtil.set(response, CookieConstant.TOKEN, null, 0);

                }

            }
            // 1.5 cookie不存在 可能是其他为登录用户的浏览器或用户清除cookie(允许登录)

        }

        // 2.获取用户当前启用的IP记录 是否在其他地方已登录
        IpStatus statusOld = ipStatusRepository.findByUserNameAndIsUsed(userName, 1);
        // 3.将已登录的用户踢下
        if (statusOld != null){
            // 2.1 判断IP是否相同
            if (statusOld.getUserIp().equals(userIp)){
                // 同一IP的不同浏览器（或缓存已清） 允许登录
                return 0;
            }else {
                // IP信息置位 （踢人操作）
                statusOld.setIsUsed(0);
                ipStatusRepository.save(statusOld);
            }
        }

        // 4.将新登陆的IP信息启用
        // 4.1 判断用户和IP 信息是否存在
        IpStatus statusNew = ipStatusRepository.findByUserNameAndUserIp(userName, userIp);
        if (statusNew != null){
            // 记录存在 重新启用
            statusNew.setIsUsed(1);
            statusNew.setLoginTime(DateUtil.getDate());

            ipStatusRepository.save(statusNew);

            return 0;
        }

        // 4.2不存在 添加新纪录
        IpStatus statusAdd = new IpStatus();
        statusAdd.setId(KeyUtil.genUniqueKey());
        statusAdd.setUserName(userName);
        statusAdd.setUserIp(userIp);
        statusAdd.setIsUsed(1);
        statusAdd.setLoginTime(DateUtil.getDate());

        ipStatusRepository.save(statusAdd);

        return 0;
    }

    @Override
    public Integer getIpStatus(String userName, String userIp) {

        // 1.获取记录
        IpStatus status = ipStatusRepository.findByUserNameAndUserIp(userName, userIp);

        // 2.获取状态
        if (status == null){
            return 0;
        }

        if (status.getIsUsed() == 1){
            return 1;
        }

        return 0;
    }

    @Override
    public Integer ipReset(String userName, String userIp) {

        // 1.获取IP 启用记录
        IpStatus status = ipStatusRepository.findByUserNameAndUserIpAndIsUsed(userName,userIp, 1);

        // 2.IP置位
        if (status != null){
            status.setIsUsed(0);
            ipStatusRepository.save(status);
        }

        return 0;
    }
}
