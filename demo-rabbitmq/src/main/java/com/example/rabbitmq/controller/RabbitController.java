package com.example.rabbitmq.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chenzhiqin
 * @date 10/5/2023 10:08
 * @info 调用测试
 */
@Controller
public class RabbitController {

    @Resource
    private RabbitTemplate rabbitTemplate;


    @ResponseBody
    @RequestMapping("rabbit")
    public String send(HttpServletRequest request) {
        Map<String, String> collect = requestConvert(request);
        String exchange = collect.remove("exchange");
        String key = collect.remove("key");
        String message = JSONObject.toJSONString(collect);
        rabbitTemplate.convertAndSend(exchange, key, message);
        return "已发送,消息处理:";
    }


    /**
     * 使用Receive 接口 要单一队列消费 ，获取到单一返回值，否则会出现异常，只有正常接口返回值才算被确认消息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("rabbit/rev")
    public String sendReceive(HttpServletRequest request) {
        Map<String, String> collect = requestConvert(request);
        String exchange = collect.remove("exchange");
        String key = collect.remove("key");
        String message = JSONObject.toJSONString(collect);
        Object o = rabbitTemplate.convertSendAndReceive(exchange, key, message);
        return "已发送,消息处理:" + o;
    }


    private Map<String, String> requestConvert(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        return parameterMap.entrySet().stream().collect(Collectors.toMap(v -> v.getKey(), v -> v.getValue()[0]));
    }


}
