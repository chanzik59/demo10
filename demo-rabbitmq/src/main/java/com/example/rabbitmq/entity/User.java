package com.example.rabbitmq.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author chenzhiqin
 * @date 10/5/2023 10:49
 * @info 用户
 */
@Data
@ToString
public class User {

    private Long id;

    private String name;

    private String age;
}
