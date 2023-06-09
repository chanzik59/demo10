package com.example.redis.controller;

import com.example.redis.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author chenzhiqin123
 * @date 23/5/2023 15:18
 * @info 入口
 */
@Slf4j
@Controller
public class RedisController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedissonClient redissonClient;


    @ResponseBody
    @PostMapping("sendCode")
    public User sendCode(String phone) {
        if (!StringUtils.hasText(phone)) {
//            return "请输入手机号码";
        }
        Random random = new Random();

        StopWatch watch = new StopWatch();

        String code = String.valueOf(random.nextInt(10000000));
        log.info("验证码：" + code);
        stringRedisTemplate.opsForValue().set("code:" + phone, code, 1, TimeUnit.MINUTES);
        User user = new User(10L,"102","526","789");
        return user;
    }


    @RequestMapping("redisson")
    @ResponseBody
    public String lock() throws InterruptedException {
//        RLock lock = redissonClient.getLock("456");
//        boolean b = lock.tryLock(10,20,TimeUnit.SECONDS);
//        if(b){
//            TimeUnit.SECONDS.sleep(20);
//            System.out.println("到达二十秒");
//            TimeUnit.SECONDS.sleep(20);
//        }

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    RLock lock = redissonClient.getLock("456");
                    lock.lock();


                        System.out.println("获取到锁："+Thread.currentThread().getName());
                        TimeUnit.SECONDS.sleep(2);
                        System.out.println("锁释放："+Thread.currentThread().getName());
                        lock.unlock();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();

        }

        return "获取锁";
    }

}
