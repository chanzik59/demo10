package com.example.rabbitmq;

import com.example.rabbitmq.config.RabbitTemplateConfig;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

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
        publish();
        consumer();
    }


    /**
     * 事务开启直到commit才会发消息到队列否则可以回滚不发送
     *
     * @throws Exception
     * @see RabbitTemplateConfig#txTemplate()
     */
    public static void publish() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.75.131");
        factory.setPort(5672);
        factory.setUsername("rabbit");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.txSelect();
        try {
            channel.exchangeDeclare("direct", BuiltinExchangeType.DIRECT);
            channel.queueDeclare("direct", false, false, false, null);
            channel.queueBind("direct", "direct", "direct");
            String message = "hello";
            channel.basicPublish("direct", "direct", null, message.getBytes(StandardCharsets.UTF_8));
            channel.txCommit();
        } catch (Exception e) {
            e.printStackTrace();
            channel.txRollback();
        }

    }


    public static void consumer() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.75.130");
        factory.setPort(5672);
        factory.setUsername("rabbit");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume("direct", true, (a, b) -> {
            System.out.println("收到消息" + new String(b.getBody()));
        }, (a, b) -> {
        });
    }
}
