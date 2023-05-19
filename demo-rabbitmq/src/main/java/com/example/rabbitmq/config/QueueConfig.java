package com.example.rabbitmq.config;

import com.example.rabbitmq.constant.RabbitConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenzhiqin
 * @date 9/5/2023 11:50
 * @info 队列配置
 */
@Configuration
public class QueueConfig {

    /**
     * 队列设置信息优先级,一共0-255优先级越大，优先级越高，发送消息中携带
     * 只有消息再队列中阻塞，消息优先级才有作用
     *
     * @return
     */
    @Bean
    public Queue directQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-max-priority", 10);
        return new Queue(RabbitConstants.DIRECT_QUEUE, false, false, false, arguments);
    }

    @Bean
    public Queue topicAll() {
        return new Queue(RabbitConstants.TOPIC_QUEUE_ALL, false, false, false);
    }

    @Bean
    public Queue topicTemp() {
        return new Queue(RabbitConstants.TOPIC_QUEUE_TEMP, false, false, false);
    }


    @Bean
    public Queue fanout1() {
        return new Queue("fanout1", false, false, false);
    }

    @Bean
    public Queue fanout2() {
        return new Queue("fanout2", false, false, false);
    }


    @Bean
    public Queue warn() {
        return new Queue("warn", false, false, false);
    }

    @Bean
    public Queue backup() {
        return new Queue("backup", false, false, false);
    }

    @Bean
    public Queue delay() {
        return new Queue("delay", false, false, false);
    }

    @Bean
    public Queue rpc() {
        return new Queue("rpc", false, false, false);
    }
}
