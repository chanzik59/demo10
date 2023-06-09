package com.example.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author chenzhiqin
 * @date 25/5/2023 10:06
 * @info 配置
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    @Value("${spring.redis.password}")
    private String password;

    /**
     * redisTemplate 需要配置序列号器，默认使用jdk 普通json序列化器会携带类信息，常用手动序列化 {@link org.springframework.data.redis.core.StringRedisTemplate}
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> template(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(serializer);
        return redisTemplate;
    }


    /**
     * 单机连接方式，集群或者其他模式连接方式 {@link Config#useClusterServers()}
     * 再spring boot 中使用redisson 不建议使用starter方式避免影响元redis spring boot 整合
     * <p>
     * {@link RLock#tryLock()}
     * <p>
     * 分布式所原理：
     * 可重入： 使用的是hash 键值对 记录重入次数
     * 自旋： 使用发布订阅机制，先获取锁，获取失败返回 ttl 时间，如果剩余时间大于零，进入订阅模式，进入阻塞等待唤醒，唤醒之后如果还有剩余时间，争抢锁，抢夺失败继续进入阻塞等待唤醒
     * <p>
     * 防止死锁： 如果没有传入失效时间ttl默认是30 秒，同时使用看门狗10秒续约一次，如果服务器宕机，到时ttl失效锁释放，如果因为异常没有释放锁，会成为死锁
     * 如果传入了失效时间，到时自动失效
     * <p>
     * 主从同步锁失效问题：{@link RedisConfig#multiLock()}
     *
     * @return
     */
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String address = String.format("redis://%s:%s", redisHost, redisPort);
        config.useSingleServer().setAddress(address).setPassword(password);
        return Redisson.create(config);
    }


    @Bean
    public RedissonClient redissonClient1() {
        Config config = new Config();
        String address = String.format("redis://%s:%s", redisHost, redisPort);
        config.useSingleServer().setAddress(address).setPassword(password);
        return Redisson.create(config);
    }


    @Bean
    public RedissonClient redissonClient2() {
        Config config = new Config();
        String address = String.format("redis://%s:%s", redisHost, redisPort);
        config.useSingleServer().setAddress(address).setPassword(password);
        return Redisson.create(config);
    }


    /**
     * 链锁要配置 多个独立节点 ，全部节点获取锁成功才是成功
     * 防止主从锁同步失效问题：
     */
    public void multiLock() {
        RLock lock1 = redissonClient().getLock("123");
        RLock lock2 = redissonClient1().getLock("123");
        RLock lock3 = redissonClient2().getLock("123");
        RLock multiLock = redissonClient().getMultiLock(lock1, lock2, lock3);
    }


}
