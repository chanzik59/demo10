package com.example.rabbitmq.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.rabbitmq.config.RabbitTemplateConfig;
import com.example.rabbitmq.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author chenzhiqin
 * @date 10/5/2023 10:08
 * @info 调用测试
 */
@Slf4j
@Controller
public class RabbitController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RabbitTemplate txTemplate;


    /**
     * 如果消息配置了ttl 并发到了ttl队列中，按照时间小的为准，但是消息ttl只有在队列头部的时候才会判断时间，所以一般不准，
     * 如果需要一个队列中，不同的消息有不同的ttl选择消息发送中携带，如果需要准时则用ttl队列
     * <p>
     * 插件交换机要配置延迟参数才起作用
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
        String priority = collect.remove("priority");

        MessageProperties properties = new MessageProperties();
        properties.setExpiration(time);
        Optional.ofNullable(priority).map(Integer::valueOf).ifPresent(properties::setPriority);
        Optional.ofNullable(collect.remove("delay")).filter(StringUtils::hasLength).map(Integer::valueOf).ifPresent(properties::setDelay);
        String body = JSONObject.toJSONString(collect);
        log.info("发送消息:{}", body);
        Message message = new Message(body.getBytes(StandardCharsets.UTF_8), properties);
        rabbitTemplate.convertAndSend(exchange, key, message);
        return "已发送,消息处理:";
    }


    /**
     * 使用Receive 接口 要单一队列消费 ，获取到单一返回值，否则会出现异常，只有正常接口返回值才算被确认消息
     * <p>
     * rpc 调用 默认使用直接反馈模式
     * {@linkplain RabbitTemplateConfig#rpcCfg(RabbitTemplate)}
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

        MessageProperties properties = new MessageProperties();
        properties.setCorrelationId(String.valueOf(System.currentTimeMillis()));
        String body = JSONObject.toJSONString(collect);
        log.info("发送消息:{}", body);
        Message message = new Message(body.getBytes(StandardCharsets.UTF_8), properties);
        Object o = rabbitTemplate.convertSendAndReceive(exchange, key, message);
        return "已发送,消息处理:" + o;
    }


    /**
     * rabbitmq 事务消息和数据库事务有区别，只是防止消息丢失，确保消息能够发送成功，可以用发送者确认代替
     *
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @Transactional
    @RequestMapping("rabbit/tx")
    public String sendWithTransactional(HttpServletRequest request) throws Exception {
        Map<String, String> collect = requestConvert(request);
        String exchange = collect.remove("exchange");
        String key = collect.remove("key");
        String message = JSONObject.toJSONString(collect);
        txTemplate.convertAndSend(exchange, key, message);
        return "消息发送成功:" + message;
    }


    private Map<String, String> requestConvert(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        return parameterMap.entrySet().stream().collect(Collectors.toMap(v -> v.getKey(), v -> v.getValue()[0]));
    }


}
