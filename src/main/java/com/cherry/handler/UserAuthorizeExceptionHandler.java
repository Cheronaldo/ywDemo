package com.cherry.handler;

import com.cherry.config.ProjectUrlConfig;
import com.cherry.exception.UserAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户身份验证 异地IP监测 异常处理类
 * Created by Administrator on 2018/01/10.
 */
@ControllerAdvice
public class UserAuthorizeExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /**
     * 拦截异常登录
     * @return
     */
    @ExceptionHandler(value = UserAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){

        // 返回登录界面
        //return new ModelAndView("redirect:".concat("http://127.0.0.1:8081/login"));
        return new ModelAndView("redirect:".concat(projectUrlConfig.getYwDemo()));

    }


}
