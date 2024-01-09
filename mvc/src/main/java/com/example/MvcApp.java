package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author czq
 * @date 2023/9/7 11:18
 */
//@EnableScheduling
//@EnableAsync
@MapperScan(basePackages = "com.example.mapper")
//@PropertySource(value = "file:F:/desktop/cfs/aa.properties",encoding = "UTF-8")
@ImportResource("classpath:test.xml")
@SpringBootApplication
public class MvcApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MvcApp.class);
    }
}