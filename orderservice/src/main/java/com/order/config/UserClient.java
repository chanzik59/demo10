package com.order.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author chenzhiqin
 * @date 22/8/2023 10:32
 * @info XX
 */
@FeignClient("userservice")
public interface UserClient {


    /**
     * 获取用户年龄
     *
     * @return
     */
    @RequestMapping("user/name")
    String getAge();


    /**
     * 余额扣减
     *
     * @param id
     * @param amt
     * @return
     */
    @RequestMapping("user/deduct")
    boolean deduction(@RequestParam("id") Long id, @RequestParam("amt") BigDecimal amt);
}
