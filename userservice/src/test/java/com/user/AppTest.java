package com.user;

import com.user.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author chenzhiqin
 * @date 1/9/2023 14:16
 * @info XX
 */
@SpringBootTest
public class AppTest {

    @Resource
    private UserMapper userMapper;

    @Test
    void test(){

        int update = userMapper.update(1L, BigDecimal.valueOf(10L));


    }
}
