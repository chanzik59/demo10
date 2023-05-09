package com.example.batch.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author chenzhiqin
 * @date 9/5/2023 13:56
 * @info 用户实体类
 */
@Data
@ToString
public class User {

    private Long id;

    private String name;

    private String age;

    private String address;
}
