package com.example.rabbitmq.config;

import com.example.rabbitmq.constant.RabbitConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenzhiqin
 * @date 9/5/2023 11:50
 * @info 队列配置
 */
@Configuration
public class QueueConfig {

    @Bean
    public Queue directQueue() {
        return new Queue(RabbitConstants.DIRECT_QUEUE, false, false, false);
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
}
