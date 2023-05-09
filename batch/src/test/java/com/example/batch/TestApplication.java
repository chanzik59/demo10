package com.example.batch;

import com.example.batch.entity.User;
import com.example.batch.mapper.UserMapper;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenzhiqin
 * @date 9/5/2023 12:01
 * @info 测试启动类
 */
@SpringBootTest
public class TestApplication {

    @Resource
    private UserMapper userMapper;



    @Test
    public void test(){
        List<User> all = userMapper.all();
        System.out.println(all);

    }
}
