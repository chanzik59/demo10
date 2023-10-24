package com.user.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chenzhiqin
 * @date 1/9/2023 10:56
 * @info XX
 */

@Data
public class User {

    private Long id;

    private String name;

    private BigDecimal amt;
}
