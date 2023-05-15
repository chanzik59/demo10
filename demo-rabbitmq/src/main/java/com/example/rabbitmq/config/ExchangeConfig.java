package com.example.rabbitmq.config;

import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenzhiqin
 * @date 9/5/2023 11:50
 * @info 交换机配置
 */
@Configuration
public class ExchangeConfig {
    /**
     * 直接交换机 用routing key 和队列绑定 ,发送消息需要指定routing key
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct", false, false);
    }


    /**
     * 主题交换机，与之相匹配队列才会收到消息
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topic", false, false);
    }

    /**
     * 扇出交换机，相当于群发
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout", false, false);
    }


    /**
     * 备份交换机，一般设置为 扇出交换机
     *
     * @return
     */
    @Bean
    public FanoutExchange backupExchange() {
        return new FanoutExchange("backup", false, false);
    }

    /**
     * 延迟交换机需要安装延迟插件
     *
     * @return
     */
    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> arguments = new HashMap<>(1);
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange("delay", "x-delayed-message", true, false, arguments);
    }
}
