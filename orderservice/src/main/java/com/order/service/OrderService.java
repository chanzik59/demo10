package com.order.service;

import com.order.entity.Order;

/**
 * @author chenzhiqin
 * @date 24/8/2023 16:32
 * @info XX
 */

public interface OrderService {


    /**
     * 修改
     *
     * @param order
     */
    void updateOrder(Order order);


    /**
     * 下单
     *
     * @param order
     * @return
     */
    int placeOrder(Order order);


}
