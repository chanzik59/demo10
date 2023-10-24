package com.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author chenzhiqin
 * @date 21/8/2023 15:09
 * @info XX
 */
@EnableFeignClients
@SpringBootApplication
//@MapperScan("com.order.mapper")
@EnableAspectJAutoProxy
public class OrderApp {
    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class, args);
    }
}
