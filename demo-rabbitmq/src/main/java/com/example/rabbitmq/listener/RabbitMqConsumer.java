package com.example.rabbitmq.listener;

import com.alibaba.fastjson.JSONObject;
import com.example.rabbitmq.constant.RabbitConstants;
import com.example.rabbitmq.entity.User;
import com.example.rabbitmq.service.UserService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
     * <p>
     * reject 一次只能拒绝一条消息，可以重新入队
     * nack 可以开启一次拒绝多条比deliveryTag 小的消息，可以重新入队
     * ack 用于肯定 表示该消息被正确处理
     * <p>
     * 备份交换机 是正常交换机路由队列失败-转发到备份交换机，备份交换机一般采用fanout
     *
     * @param message
     * @param channel
     * @param deliveryTag
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "manual", durable = "false", autoDelete = "false", arguments = {@Argument(name = "x-dead-letter-exchange", value = "dead"), @Argument(name = "x-dead-letter-routing-key", value = "dead"), @Argument(name = "x-message-ttl", value = "10000", type = "java.lang.Integer"), @Argument(name = "x-max-length", value = "6", type = "java.lang.Integer")}), exchange = @Exchange(value = "manual", arguments = @Argument(name = "alternate-exchange", value = "backup")), key = "manual"), ackMode = "MANUAL")
    public void manualAck(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws Exception {
        log.info("手动确认接收到消息{}", message);
        User user = JSONObject.parseObject(message, User.class);
        if (Integer.valueOf(user.getAge()) <= 100) {
            userService.addUser(user);
            channel.basicAck(deliveryTag, false);
        } else if (Integer.valueOf(user.getAge()) > 200) {
            channel.basicReject(deliveryTag, false);
        } else {
            channel.basicNack(deliveryTag, false, false);
        }

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

    @RabbitListener(queues = "backup")
    public void backup(String message) {
        log.info("backup 收到消息{}", message);
    }

    @RabbitListener(queues = "warn")
    public void warn(String message) {
        log.info("warn,接收到消息{}", message);
    }

    @RabbitListener(queues = "delay")
    public void delay(String message) {
        log.info("delay,接收到消息{}", message);
    }


    /**
     * 死信队列声明和消费
     * <p>
     * 进入私信队列的三种情况  1 ttl 超时  2  非正常处理并且没有重新入队  （nack,reject） 3 达到队列最大消息数
     *
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "dead", durable = "true", autoDelete = "false"), exchange = @Exchange(value = "dead"), key = "dead"))
    public void deadQueue(String message) {
        log.info("死信队列接收到消息:{}", message);
        User user = JSONObject.parseObject(message, User.class);
        userService.addUserTmp(user);
    }

    /**
     * rabbitmq 实现rpc调用，使用两个队列发送
     *
     * @param message
     * @return
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "request"), exchange = @Exchange(value = "request"), key = "rpc"))
    public String request(String message) {
        log.info("测试rpc:{}", message);
        return "aa";
    }
}
