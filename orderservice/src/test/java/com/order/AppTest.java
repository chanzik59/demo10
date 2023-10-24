package com.order;

import com.order.entity.Order;
import com.order.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author chenzhiqin
 * @date 1/9/2023 11:48
 * @info XX
 */
@SpringBootTest
public class AppTest {

    @Resource
    private OrderMapper orderMapper;


    @Test
    void test() {
        Order order = new Order();
        order.setOrderName("苹果");
        order.setAmt(BigDecimal.valueOf(20));
        order.setUserId(1L);
        orderMapper.save(order);
    }
}
