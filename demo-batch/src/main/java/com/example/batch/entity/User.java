package com.example.batch.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author chenzhiqin
 * @date 20/4/2023 11:15
 * @info XX
 */
@Data
@ToString
public class User {

    private long id;
    private String name;
    private int age;
    private String address;
}
