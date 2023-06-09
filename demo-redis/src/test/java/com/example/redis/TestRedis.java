package com.example.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

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


    @Resource
    private RedisTemplate<String, Object> template;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedissonClient redissonClient;

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
    }

}
