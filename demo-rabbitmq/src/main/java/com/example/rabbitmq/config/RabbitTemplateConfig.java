package com.example.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Optional;

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


    /**
     * template 方式配置事务消息  需要配合{@link org.springframework.transaction.annotation.Transactional} 使用
     *
     * @return
     */
    public RabbitTemplate txTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //事务和发送者确认不能同时存在
        rabbitTemplate.setChannelTransacted(true);
        return rabbitTemplate;
    }


    /**
     * 固定队列需要设置监听器不然没法接收到消息
     *
     * @param rabbitTemplate
     * @return
     */
    public SimpleMessageListenerContainer container(RabbitTemplate rabbitTemplate) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("rpc");
        container.setMessageListener(rabbitTemplate);
        container.setConcurrentConsumers(100);
        container.setConcurrentConsumers(100);
        container.setPrefetchCount(250);
        return container;
    }


    /**
     * rpc 1发送的三种种配置 一个是是用固定队列，2使用默认，直接发送可以不用配置，使用template 3 使用临时队列
     *
     * @param rabbitTemplate
     */
    private void rpcCfg(RabbitTemplate rabbitTemplate) {
        //设置固定的Reply 地址  需要设置队列监听器
        rabbitTemplate.setUseTemporaryReplyQueues(false);
        rabbitTemplate.setReplyAddress("rpc");
        rabbitTemplate.expectedQueueNames();
        rabbitTemplate.setUserCorrelationId(true);
        //使用直接发送
        rabbitTemplate.setUseTemporaryReplyQueues(false);
        rabbitTemplate.setReplyAddress("amq.rabbitmq.reply-to");
        rabbitTemplate.setUserCorrelationId(true);
        //临时队列 不推荐 性能最差
        rabbitTemplate.setUseTemporaryReplyQueues(true);
        rabbitTemplate.setUserCorrelationId(true);
        rabbitTemplate.setReplyTimeout(10000);
    }


    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();
        HashSet<String> set1 = new HashSet<>();
        set.add("11");
        set.add("13");
        set1.add("11");
        set1.add("12");
        set.retainAll(set1);
        System.out.println(set);
        Optional.ofNullable(set).map(v->v.retainAll(set1)).ifPresent(System.out::println);
    }

}
