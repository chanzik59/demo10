package com.order.controller;

import com.order.config.UserClient;
import com.order.entity.Order;
import com.order.service.OrderService;
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
    private OrderService orderService;

    @Resource
    private UserClient userClient;

    @Resource
    private RestTemplate template;


    @RequestMapping("name")
    @ResponseBody
    private String getName() {
        return userClient.getAge();
    }


    @RequestMapping("place")
    @ResponseBody
    public String placeOrder(Order order) {
        int i = orderService.placeOrder(order);
        return 1 == i ? "成功" : "失败";
    }


}
