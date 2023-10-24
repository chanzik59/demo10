package com.example.inf;

import com.example.entity.AA;
import com.example.entity.User;


/**
 * @author czq
 * @date 2023/10/12 14:01
 * @Description:
 */

//@Service
public class UserServiceImpl1 implements UserService {
    @Override
    public void test() {
        System.out.println("测试user1");
    }

    @Override
    public void test1() {
        System.out.println("测试user1 method1");
    }

    @Override
    public void test2(AA aa) {

    }

    @Override
    public void test4(AA bb) {

    }

    @Override
    public User findUser() {

        return null;
    }
}
