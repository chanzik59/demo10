package com.example.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author chenzhiqin
 * @date 25/5/2023 10:18
 * @info user表
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    private String nam;

    private String age;

    private String address;

}
