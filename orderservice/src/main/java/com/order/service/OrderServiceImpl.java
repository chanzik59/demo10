package com.order.service;

import com.order.config.UserClient;
import com.order.entity.Order;
import com.order.mapper.OrderMapper;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chenzhiqin
 * @date 1/9/2023 11:39
 * @info XX
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;


    @Resource
    private UserClient userClient;


    @Override
    public void updateOrder(Order order) {

    }

    @Override
    @GlobalTransactional
    public int placeOrder(Order order) {
        orderMapper.save(order);
        userClient.deduction(order.getUserId(), order.getAmt());
        return 1;
    }

}
