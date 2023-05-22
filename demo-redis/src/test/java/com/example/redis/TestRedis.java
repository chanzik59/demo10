package com.example.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author chenzhiqin
 * @date 22/5/2023 11:35
 * @info 测试
 */
@Slf4j
@SpringBootTest
public class TestRedis {

    private Jedis jedis;

    private Jedis poolJedis;

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;


    /**
     * 普通常规方式使用jedis
     */
    @BeforeEach
    public void jedis() {
        jedis = new Jedis(redisHost, redisPort);
        jedis.auth(redisPassword);
    }

    /**
     * jedis 连接池使用redis
     */
    @BeforeEach
    public void jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10);
        config.setMinIdle(0);
        poolJedis = new JedisPool(config, redisHost, redisPort, 5000, redisPassword, 0).getResource();
    }


    @Test
    public void test() {
        poolJedis.select(0);
        String name = poolJedis.get("name");
        log.info("name:{}", name);

    }
}
