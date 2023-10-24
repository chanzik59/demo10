package com.user.controller;

import com.user.service.UserService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author chenzhiqin
 * @date 21/8/2023 14:55
 * @info XX
 */
@Controller
@RequestMapping("user")
@RefreshScope
public class UserController {

    @Resource
    private UserService userService;


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


    @RequestMapping("deduct")
    @ResponseBody
    public boolean deduction(Long id, BigDecimal amt) {
        return userService.deduction(id, amt);
    }


}
