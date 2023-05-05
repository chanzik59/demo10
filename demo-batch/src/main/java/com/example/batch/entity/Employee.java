package com.example.batch.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author chenzhiqin
 * @date 4/5/2023 17:11
 * @info XX
 */
@Data
@ToString
public class Employee implements Serializable {

    private Integer id;
    private String name;
    private Integer age;
    private Integer sex;
}
