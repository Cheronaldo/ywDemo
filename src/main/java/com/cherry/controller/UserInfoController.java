package com.cherry.controller;

import com.cherry.dataobject.UserInfo;
import com.cherry.enums.UserEnum;
import com.cherry.exception.UserException;
import com.cherry.form.UserInfoForm;
import com.cherry.service.UserInfoService;
import com.cherry.util.ResultVOUtil;
import com.cherry.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by Administrator on 2017/11/07.
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 用户注册校验用户是否已存在
     * @param userName
     * @return
     */
    @PostMapping("/check")
    public ResultVO userCheck(@RequestParam("userName") String userName){
        UserInfo userInfo = userInfoService.findOneByUserName(userName);
        if(userInfo != null){
            log.error("用户名已存在");
            throw new UserException(UserEnum.USER_ALREADY_EXIST);
            //异常捕获处理 已完成
        }
        return ResultVOUtil.success(UserEnum.USER_VALID.getMessage());
    }

    /**
     * 用户注册 储存用户信息
     * @param form
     * @param bindingResult
     * @return
     */
    @PostMapping("/register")
    public ResultVO userRegister(@Valid UserInfoForm form,
                                 BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("用户信息填写错误");
            throw  new UserException((UserEnum.USER_INFORMATION_ERROR));
        }
        int result = userInfoService.saveUser(form);
        return ResultVOUtil.success(UserEnum.USER_REGISTER_SUCCESS.getMessage());

    }

    @PostMapping("/login")
    public ResultVO userLogin(HttpServletRequest request, HttpServletResponse response){
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");
        int result = userInfoService.userLogin(userName, userPassword);
        if(result == 1){
            return ResultVOUtil.error(1,UserEnum.USER_LOGIN_FAIL.getMessage());
        }
        //TODO 后期做ip检测 防止异地登录
        request.getSession().setAttribute("userName", userName);
        request.getSession().setAttribute("userPassword", userPassword);

        log.info((String)request.getSession().getAttribute("userName"));

        return ResultVOUtil.success(UserEnum.USER_LOGIN_SUCCESS.getMessage());
    }

    /**
     * 通过用户名获取用户信息
     * @param userName
     * @return
     */
    @PostMapping("/getUser")
    public ResultVO getUser(@RequestParam("userName") String userName){
        UserInfo userInfo = userInfoService.findOneByUserName(userName);
        //TODO 这里还有一个密码解码过程 是否有必要能够让用户看到密码明文？
        if(userInfo == null){
            return ResultVOUtil.error(1,UserEnum.USER_GET_FAIL.getMessage());
        }
        return ResultVOUtil.success(UserEnum.USER_GET_SUCCESS.getMessage(),userInfo);
    }

    /**
     * 用户信息修改
     * @param form
     * @param bindingResult
     * @return
     */
    @PostMapping("/update")
    public ResultVO userUpdate(@Valid UserInfoForm form,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("用户信息填写错误");
            throw  new UserException((UserEnum.USER_INFORMATION_ERROR));
        }
        int result = userInfoService.saveUser(form);
        return ResultVOUtil.success(UserEnum.USER_UPDATE_SUCCESS.getMessage());
    }


    /**
     * 用户注销
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/logout")
    public ResultVO userLogout(HttpServletRequest request, HttpServletResponse response){

        request.getSession().removeAttribute("userName");
        request.getSession().removeAttribute("userPassword");
        log.info((String)request.getSession().getAttribute("userName"));
        return ResultVOUtil.success(UserEnum.USER_LOGOUT_SUCCESS.getMessage());

    }

    @PostMapping("/send")
    public ResultVO sendCheckCode(@RequestParam("userTelephone") String userTelephone){
        //TODO 向用户和页面发送 短信校验码
        return ResultVOUtil.success(UserEnum.SEND_CODE_SUCCESS.getMessage());

    }

}
