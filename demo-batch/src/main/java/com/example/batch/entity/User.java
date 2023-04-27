package com.example.batch.entity;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author chenzhiqin
 * @date 20/4/2023 11:15
 * @info XX
 */
@Data
@ToString
public class User {

    private long id;
    @NotBlank(message = "用户名字不能为空")
    private String name;
    private int age;
    private String address;
}
