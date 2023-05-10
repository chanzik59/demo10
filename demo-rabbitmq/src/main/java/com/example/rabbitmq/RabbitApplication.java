package com.example.rabbitmq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenzhiqin
 * @date 9/5/2023 11:22
 * @info XX
 */
@MapperScan("com.example.rabbitmq.mapper")
@EnableRabbit
@SpringBootApplication
public class RabbitApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitApplication.class);
    }
}
