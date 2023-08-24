package com.order.controller;

import com.order.config.UserClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author chenzhiqin
 * @date 21/8/2023 15:22
 * @info XX
 */
@RequestMapping("order")
@Controller
public class OrderController {

    @Resource
    private RestTemplate template;


    @Resource
    private UserClient userClient;

    @RequestMapping("name")
    @ResponseBody
    public String getName(){
//        String url="http://userservice/user/name";
//        String age = template.getForObject(url, String.class);
        String age = userClient.getAge();
        return age;
    }
}
