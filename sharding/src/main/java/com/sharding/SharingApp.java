package com.sharding;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenzhiqin
 * @date 18/8/2023 11:20
 * @info XX
 */
@SpringBootApplication
//@MapperScan(value = "com.sharding.mapper")
public class SharingApp {

    public static void main(String[] args) {
        SpringApplication.run(SharingApp.class);
    }


}
