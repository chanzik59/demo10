package com.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenzhiqin
 * @date 21/8/2023 14:55
 * @info XX
 */
@Controller
@RequestMapping("user")
@RefreshScope
public class UserController {



//    @Value("${user.age}")
    private String age;


    @ResponseBody
    @RequestMapping("name")
    public String getName() {
        return "aa";
    }


    @ResponseBody
    @RequestMapping("age")
    public String getAge() {
        return age;
    }
}
