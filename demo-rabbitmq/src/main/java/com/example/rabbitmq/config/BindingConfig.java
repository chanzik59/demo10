package com.example.rabbitmq.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenzhiqin
 * @date 10/5/2023 9:55
 * @info 交换器队列绑定配置
 */
@Configuration
public class BindingConfig {

    /**
     * 直接交换机与队列绑定，需要路由键与绑定完全一致
     *
     * @param directExchange
     * @param directQueue
     * @return
     */
    @Bean
    public Binding directBinding(DirectExchange directExchange, Queue directQueue) {
        return BindingBuilder.bind(directQueue).to(directExchange).with("direct");
    }


    /**
     * 主题交换机与队列绑定需要 路由键表达式  # 表示多个任意 * 表示单个任意
     *
     * @param topicExchange
     * @param topicAll
     * @return
     */
    @Bean
    public Binding topicAllBinding(TopicExchange topicExchange, Queue topicAll) {
        return BindingBuilder.bind(topicAll).to(topicExchange).with("topic.#");
    }

    @Bean
    public Binding topicABinding(TopicExchange topicExchange, Queue topicTemp) {
        return BindingBuilder.bind(topicTemp).to(topicExchange).with("topic.temp");
    }

    /**
     * 扇出交换机与队列绑定不需要路由键
     *
     * @param fanoutExchange
     * @param fanout1
     * @return
     */
    @Bean
    public Binding fanout1Biding(FanoutExchange fanoutExchange, Queue fanout1) {
        return BindingBuilder.bind(fanout1).to(fanoutExchange);
    }

    @Bean
    public Binding fanout2Biding(FanoutExchange fanoutExchange, Queue fanout2) {
        return BindingBuilder.bind(fanout2).to(fanoutExchange);
    }

    /**
     * 绑定备份队列与备份交换机
     *
     * @param backupExchange
     * @param warn
     * @return
     */
    @Bean
    public Binding warnBinding(FanoutExchange backupExchange, Queue warn) {
        return BindingBuilder.bind(warn).to(backupExchange);
    }

    @Bean
    public Binding backupBinding(FanoutExchange backupExchange, Queue backup) {
        return BindingBuilder.bind(backup).to(backupExchange);
    }

    @Bean
    public Binding delayBinding(CustomExchange delayExchange, Queue delay) {
        return BindingBuilder.bind(delay).to(delayExchange).with("delay").noargs();
    }
}
