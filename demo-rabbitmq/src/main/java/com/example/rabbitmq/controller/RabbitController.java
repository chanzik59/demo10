package com.example.rabbitmq.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
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


    /**
     * 如果消息配置了ttl 并发到了ttl队列中，按照时间小的为准，但是消息ttl只有在队列头部的时候才会判断时间，所以一般不准，
     * 如果需要一个队列中，不同的消息有不同的ttl选择消息发送中携带，如果需要准时则用ttl队列
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("rabbit")
    public String send(HttpServletRequest request) {
        Map<String, String> collect = requestConvert(request);
        String exchange = collect.remove("exchange");
        String key = collect.remove("key");
        String time = collect.remove("ttl");

        MessageProperties properties = new MessageProperties();
        properties.setExpiration(time);
        String body = JSONObject.toJSONString(collect);
        Message message = new Message(body.getBytes(StandardCharsets.UTF_8), properties);
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
