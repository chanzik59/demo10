package com.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenzhiqin
 * @date 21/8/2023 14:54
 * @info XX
 */
@SpringBootApplication
public class UserApp {
    public static void main(String[] args) {
        System.out.println("更新1");
        System.out.println("push 1");
        SpringApplication.run(UserApp.class, args);
    }
}
