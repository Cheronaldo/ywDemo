package com.cherry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/11/05.
 */
@Controller
@RequestMapping("/test")
public class TestController {


    @GetMapping("/run")
    public String test(){
        return "test";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


}
