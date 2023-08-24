package com.sharding;

import com.sharding.entity.User;
import com.sharding.entity.mapper.UserMapper;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author chenzhiqin
 * @date 18/8/2023 11:41
 * @info XX
 */
@SpringBootTest
public class TestApp {


    @Resource
    private UserMapper userMapper;


    @Test
    @Transactional
    @Rollback(value = false)
    public void addUser(){
        User user = new User();
        user.setId(0L);
        user.setName("张铁蛋");
        user.setAge("13");
        userMapper.addUser(user);
    }
}
