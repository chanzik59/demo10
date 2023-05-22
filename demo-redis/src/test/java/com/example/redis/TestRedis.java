package com.example.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

/**
 * @author chenzhiqin
 * @date 22/5/2023 11:35
 * @info 测试
 */
@Slf4j
@SpringBootTest
public class TestRedis {

    private Jedis jedis;


    /**
     * 常规方式初始化jedis
     */
    @BeforeEach
    public void jedis() {
        jedis = new Jedis("192.168.75.129", 6379);
    }


    @Test
    public void test(){
        jedis.select(0);
        String name = jedis.get("name");
        log.info("name:{}",name);

    }
}
