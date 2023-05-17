package com.example.rabbitmq;

import com.example.rabbitmq.config.RabbitTemplateConfig;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author chenzhiqin
 * @date 9/5/2023 11:33
 * @info XX
 */
@SpringBootTest
public class TestApplication {

    @Test
    public void test() throws Exception {


    }

    public static void main(String[] args) throws Exception {
//        publish();
//        consumer();
        pullOne();
    }


    /**
     * 事务开启直到commit才会发消息到队列否则可以回滚不发送
     *
     * @throws Exception
     * @see RabbitTemplateConfig#txTemplate()
     */
    public static void publish() throws Exception {
        Channel channel = createChannel();
        channel.txSelect();
        try {
            channel.exchangeDeclare("direct", BuiltinExchangeType.DIRECT);
            channel.queueDeclare("direct", false, false, false, null);
            channel.queueBind("direct", "direct", "direct");
            String message = "hello" + ":" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm"));
            channel.basicPublish("direct", "direct", null, message.getBytes(StandardCharsets.UTF_8));
            channel.txCommit();
        } catch (Exception e) {
            e.printStackTrace();
            channel.txRollback();
        }

    }


    public static Channel createChannel() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.75.129");
        factory.setPort(5672);
        factory.setUsername("rabbit");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }


    /**
     * 订阅消费
     *
     * @throws Exception
     */
    public static void consumer() throws Exception {
        Channel channel = createChannel();
        channel.basicConsume("direct", true, (a, b) -> {
            System.out.println("收到消息" + new String(b.getBody()));
        }, (a, b) -> {
        });
    }


    /**
     * 每次只拉取一条消息
     *
     * @throws Exception
     */
    public static void pullOne() throws Exception {
        Channel channel = createChannel();
        GetResponse response = channel.basicGet("direct", true);
        System.out.println("收到消息:" + new String(response.getBody()));

    }
}
