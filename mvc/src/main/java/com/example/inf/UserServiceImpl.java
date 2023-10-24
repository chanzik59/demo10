package com.example.inf;

import com.example.ant.Secure;
import com.example.entity.AA;
import com.example.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.stereotype.Service;

/**
 * @author czq
 * @date 2023/10/12 14:01
 * @Description:
 */

@Slf4j
//@Service
@Secure
public class UserServiceImpl extends UserServiceFa implements UserService {


    @Secure
    @Override
    public void test() {
        log.info("测试方法正在执行");
//        throw new RuntimeException("抛个小异常");
    }

    @Override
    public void test1() {
        System.out.println("测试user method1");
    }


    @Secure
    @Override
    public void test2(AA aa) {
        System.out.println("有参数aa");
    }

    @Override
    public void test4(AA bb) {
        System.out.println("有参数bb");
    }

    @Override
    public User findUser() {
        log.info("测试方法findUser");
        return null;
    }
}
