package org.example.controller;

import org.example.entity.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author czq
 * @date 2024/2/19 17:44
 * @Description:
 */
@RequestMapping("user")
@RestController
public class UserController {

    @RequestMapping("advice")
    @ResponseBody
    public User advice( User user) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(300);
        return user;
    }
}
