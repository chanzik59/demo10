package com.order.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
