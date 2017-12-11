package com.cherry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面跳转API层
 * Created by Administrator on 2017/11/08.
 */
@Controller
public class ServletController {

    /**
     * 用户登录跳转
     * @return
     */
    @GetMapping("/login")
    public String userLogin(){
        return "login";
    }

    /**
     * 用户注册跳转
     * @return
     */
    @GetMapping("/register")
    public String userRegister(){
        return "register";
    }

    /**
     * 首页跳转
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    /**
     * 设备注册跳转
     * @return
     */
    @GetMapping("/device")
    public String deviceCheck(){
        return "device";
    }

    /**
     * 设备列表跳转
     * @return
     */
    @GetMapping("/devList")
    public String devList(){
        return "devList";
    }

    /**
     * 设备地图跳转
     * @return
     */
    @GetMapping("/deviceMap")
    public String deviceMap(){
        return "deviceMap";
    }

    /**
     * 设备协议配置跳转
     * @return
     */
    @GetMapping("/deviceConfig")
    public String deviceConfig(){
        return "deviceConfig";
    }


    /**
     * 设备实时数据跳转
     * @return
     */
    @GetMapping("/deviceRealtimeData")
    public String deviceRealtimeData(){
        return "deviceRealtimeData";
    }

    /**
     * 设备历史数据跳转
     * @return
     */
    @GetMapping("/deviceHistory")
    public String deviceHistory(){
        return "deviceHistory";
    }

}
