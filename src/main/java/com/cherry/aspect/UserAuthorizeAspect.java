package com.cherry.aspect;

import com.cherry.constant.CookieConstant;
import com.cherry.constant.RedisConstant;
import com.cherry.dataobject.IpStatus;
import com.cherry.exception.UserAuthorizeException;
import com.cherry.service.UserInfoService;
import com.cherry.util.CookieUtil;
import com.cherry.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户身份及异地IP登录监测
 * 切面处理
 * Created by Administrator on 2018/01/10.
 */
@Aspect
@Component
@Slf4j
public class UserAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserInfoService userInfoService;

    @Pointcut("execution(public * com.cherry.controller.*Controller.*(..))" +
    "&& !execution(public * com.cherry.controller.ServletController.userLogin())" +
    "&& !execution(public * com.cherry.controller.ServletController.userRegister())" +
    "&& !execution(public * com.cherry.controller.UserInfoController.userCheck(..))" +
    "&& !execution(public * com.cherry.controller.UserInfoController.userRegister(..))" +
    "&& !execution(public * com.cherry.controller.UserInfoController.userLogin(..))" +
    "&& !execution(public * com.cherry.controller.UserInfoController.userLogout(..))" +
    "&& !execution(public * com.cherry.controller.UserInfoController.sendCheckCode(..))"+
    "&& !execution(public * com.cherry.controller.AppController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify(){

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 1.查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);

        if (cookie == null){
            log.warn("cookie中查不到token!");
            throw new UserAuthorizeException();
        }
        // 2.查询redis   tokenValue中存的就是userName
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)){
            log.warn("redis中查不到token!");
            throw new UserAuthorizeException();
        }

        // 3.校验ip
        // 3.1 获取ip
        String userIp = IpUtil.getRealIp(request);
        int ipStatus = userInfoService.getIpStatus(tokenValue, userIp);
        if (ipStatus == 0){
            // ip记录不存在 或 状态为0
            log.warn("您已被迫下线!");
            throw new UserAuthorizeException();
        }

    }

}
