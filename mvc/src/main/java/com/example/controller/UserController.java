package com.example.controller;

import com.example.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author czq
 * @date 2024/2/19 17:17
 * @Description:
 */
@RequestMapping("user")
@Controller
public class UserController {

    @RequestMapping("advice")
    @ResponseBody
    public User advice(@RequestBody User user) {

        return user;
    }
}
