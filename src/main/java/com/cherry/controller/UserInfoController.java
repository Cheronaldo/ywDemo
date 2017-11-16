package com.cherry.controller;

import com.cherry.dataobject.UserInfo;
import com.cherry.enums.UserEnum;
import com.cherry.exception.UserException;
import com.cherry.form.UserInfoForm;
import com.cherry.form.UserUpdateForm;
import com.cherry.service.UserInfoService;
import com.cherry.util.ResultVOUtil;
import com.cherry.util.ShortMessagingServiceUtil;
import com.cherry.vo.ResultVO;
import com.cherry.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

/**
 * 用户操作API层
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
        UserInfo userInfo = userInfoService.getUserInfoByUserName(userName);
        if (userInfo != null){
            log.error("用户名已存在");
            throw new UserException(UserEnum.USER_ALREADY_EXIST);
            // 异常捕获处理 已完成
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
        if (bindingResult.hasErrors()){
            log.error("用户信息填写错误");
            throw  new UserException((UserEnum.USER_INFORMATION_ERROR));
        }
        int result = userInfoService.saveUser(form);
        if (result == 1){
            return ResultVOUtil.error(1,UserEnum.USER_REGISTER_FAIL.getMessage());
        }

        return ResultVOUtil.success(UserEnum.USER_REGISTER_SUCCESS.getMessage());

    }

    @PostMapping("/login")
    public ResultVO userLogin(HttpServletRequest request, HttpServletResponse response){
        // 1.验证是否是否有效
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");
        int result = userInfoService.userLogin(userName, userPassword);
        if (result == 1){
            return ResultVOUtil.error(1,UserEnum.USER_LOGIN_FAIL.getMessage());
        }
        // 2.验证IP，是否异地登录
        //TODO 后期做ip检测 防止异地登录


        // 3.将用户信息添加至 session
        //TODO 后期使用 redis
        request.getSession().setAttribute("userName", userName);
        request.getSession().setAttribute("userPassword", userPassword);

        log.info((String)request.getSession().getAttribute("userName"));

        // 4.根据用户的等级 跳转至不同的首页 查询用户的等级直接将等级码作为 data 传给界面
        int userClass = userInfoService.getUserInfoByUserName(userName).getUserClass();

        // 5.返回结果
        return ResultVOUtil.success(UserEnum.USER_LOGIN_SUCCESS.getMessage(),userClass);
    }

    /**
     * 通过用户名获取用户信息
     * @param userName
     * @return
     */
    @PostMapping("/getUser")
    public ResultVO getUser(@RequestParam("userName") String userName){
        UserInfoVO userInfoVO = userInfoService.getUserInfoVOByUserName(userName);
        // 密码直接上传，解码过程在前端页面
        // 目的：这样数据传输过程中即使数据被截获，也不会看到密码明文
        if (userInfoVO == null){
            return ResultVOUtil.error(1,UserEnum.USER_GET_FAIL.getMessage());
        }

        return ResultVOUtil.success(UserEnum.USER_GET_SUCCESS.getMessage(),userInfoVO);
    }

    /**
     * 用户基本信息修改
     * @param form
     * @param bindingResult
     * @return
     */
    @PostMapping("/updateInfo")
    public ResultVO userUpdateInfo(@Valid UserUpdateForm form,
                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("用户信息填写错误");
            throw  new UserException((UserEnum.USER_INFORMATION_ERROR));
        }
        int result = userInfoService.updateUserInfo(form);

        if (result == 1){
            return ResultVOUtil.error(1,UserEnum.USER_UPDATE_FAIL.getMessage());
        }

        return ResultVOUtil.success(UserEnum.USER_UPDATE_SUCCESS.getMessage());
    }

    /**
     * 用户密码修改
     * @param userName
     * @param userPassword
     * @return
     */
    @PostMapping("/updatePassword")
    public ResultVO userUpdatePassword(@RequestParam("userName") String userName,
                                       @RequestParam("userPassword") String userPassword){

        int result = userInfoService.updateUserPassword(userName, userPassword);

        if (result == 1){
            return ResultVOUtil.error(1,UserEnum.PASSWORD_UPDATE_FAIL.getMessage());
        }

        return ResultVOUtil.success(UserEnum.PASSWORD_UPDATE_SUCCESS.getMessage());
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

    @PostMapping("/askCode")
    public ResultVO sendCheckCode(@RequestParam("userTelephone") String userTelephone){
        //TODO 向用户和页面发送 短信校验码 修改返回参数（因为会对应多种情况 如：多次注册）

        ShortMessagingServiceUtil messagingServiceUtil = new ShortMessagingServiceUtil();

        Map<String, Object> map = messagingServiceUtil.sendShortMessage(userTelephone);
        int code = Integer.parseInt(String.valueOf(map.get("code")));

        Object data = map.get("data");

        if(code == 0){
            return ResultVOUtil.success(UserEnum.SEND_CODE_SUCCESS.getMessage(),data);
        }

        if (code == 1){
            return ResultVOUtil.error(1, UserEnum.REQUEST_TOO_FREQUENT.getMessage());
        }

       return ResultVOUtil.error(1,UserEnum.SEND_CODE_FAIL.getMessage());
    }

}
