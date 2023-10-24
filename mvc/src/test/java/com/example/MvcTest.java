package com.example;

import com.example.entity.AA;
import com.example.entity.Student;
import com.example.entity.UserProperties;
import com.example.inf.AnimalService;
import com.example.inf.UserServiceFa;
import com.example.inf.UserServiceImpl;
import com.example.inf.UserServiceImpl3;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author czq
 * @date 2023/10/12 14:02
 * @Description:
 */

@SpringBootTest
public class MvcTest {

    @Autowired
    private UserServiceImpl userService;

//    @Autowired
    private UserServiceFa userServiceFa;


//    @Autowired
//    private UserServiceImpl3 userService3;


    @Autowired
    private AnimalService animalService;


    @Autowired
    private UserProperties userProperties;


    @Value("aa.bb")
    private String aa;


    @Test
    public void test() {
//        userService.findUser();
//        userService.testFa();
//           userService.test();

        System.out.println(userProperties);
//        userService.test2(new Student());
//
//        userService3.test3();


    }
}
