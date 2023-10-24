package com.order.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chenzhiqin
 * @date 1/9/2023 10:57
 * @info XX
 */
@Data
public class Order {
    private Long id;

    private String orderName;

    private BigDecimal amt;

    private Long userId;
}
