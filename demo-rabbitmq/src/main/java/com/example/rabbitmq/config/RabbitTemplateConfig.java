package com.example.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author chenzhiqin
 * @date 10/5/2023 14:45
 * @info rabbit 消息确认配置
 */
@Slf4j
@Configuration
public class RabbitTemplateConfig {

    @Resource
    private ConnectionFactory connectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);
        //触发情形  1 消息找不到交换机 2 消息发送到交换机，但找不到队列  3 找不到交换机和队列 4成功匹配  （找不到交换机ack都是false 其他均为true）
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("相关数据:{},确认情况：{},原因：{}", correlationData, ack, cause);

        });
        //触发情形 1 消息发送到交换机，但找不到队列
        rabbitTemplate.setReturnsCallback(returned -> {
            log.info("消息内容：{},应答码：{},回应信息：{},交换机：{},路由键：{}", returned.getMessage(), returned.getReplyCode(), returned.getReplyText(), returned.getExchange(), returned.getRoutingKey());

        });

        return rabbitTemplate;

    }
}
