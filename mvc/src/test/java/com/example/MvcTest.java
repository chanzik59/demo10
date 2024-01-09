package com.example;

import com.example.entity.*;
import com.example.inf.AnimalService;
import com.example.inf.UserServiceFa;
import com.example.inf.UserServiceImpl;
import com.example.inf.UserServiceImpl3;
import com.example.mapper.TbTaskMapper;
import com.example.service.TaskService;
import com.example.service.TbTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author czq
 * @date 2023/10/12 14:02
 * @Description:
 */

@SpringBootTest
public class MvcTest {


    @Resource
    TbTaskMapper tbTaskMapper;


    @Test
    public void test() {
        TbTask tbTask = tbTaskMapper.selectById("1");
        System.out.println(tbTask);


    }
}
