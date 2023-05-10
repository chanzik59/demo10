package com.example.rabbitmq.listener;

import com.alibaba.fastjson.JSONObject;
import com.example.rabbitmq.constant.RabbitConstants;
import com.example.rabbitmq.entity.User;
import com.example.rabbitmq.service.UserService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author chenzhiqin
 * @date 10/5/2023 10:43
 * @info 消息消费
 */
@Slf4j
@Component
public class RabbitMqConsumer {

    @Resource
    private UserService userService;


    /**
     * 如果有返回值那么被正确接收才算消息被确认
     *
     * @param message
     * @return
     */
    @RabbitListener(queues = RabbitConstants.DIRECT_QUEUE)
    public void directConsumer(String message) {
        log.info("接收到消息{}", message);
        User user = JSONObject.parseObject(message, User.class);
        userService.addUser(user);
    }


    /**
     * 手动确认
     * 没有进行确认的消息 下载重启会自动入队进行消费
     *
     * reject 一次只能拒绝一条消息，可以重新入队
     * nack 可以开启一次拒绝多条比deliveryTag 小的消息，可以重新入队
     * ack 用于肯定 表示该消息被正确处理
     *
     * @param message
     * @param channel
     * @param deliveryTag
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "manual", durable = "false", autoDelete = "false"), exchange = @Exchange(value = "manual"), key = "manual"), ackMode = "MANUAL")
    public void manualAck(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        log.info("手动确认接收到消息{}", message);
        User user = JSONObject.parseObject(message, User.class);
        userService.addUser(user);
        channel.basicAck(deliveryTag, false);
    }


    @RabbitListener(queues = RabbitConstants.TOPIC_QUEUE_ALL)
    public void topicAll(String message) {
        log.info("接收到消息{}", message);
        User user = JSONObject.parseObject(message, User.class);
        userService.addUser(user);
        userService.addUserTmp(user);
    }

    @RabbitListener(queues = RabbitConstants.TOPIC_QUEUE_TEMP)
    public void topicTemp(String message) {
        log.info("接收到消息{}", message);
        User user = JSONObject.parseObject(message, User.class);
        userService.addUserTmp(user);
    }


    @RabbitListener(queues = "fanout1")
    public void fanout1(String message) {
        log.info("fanout1,接收到消息{}", message);
    }

    @RabbitListener(queues = "fanout2")
    public void fanout2(String message) {
        log.info("fanout2,接收到消息{}", message);
    }
}
