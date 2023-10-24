package com.example.entity;

import com.example.inf.UserService;


import java.lang.reflect.Proxy;

/**
 * @author czq
 * @date 2023/9/27 16:02
 * @Description:
 */
public class Test implements UserService {

    public static void main(String[] args) {
        Test test = new Test();
        UserService userService =(UserService) Proxy.newProxyInstance(Test.class.getClassLoader(), new Class[]{UserService.class}, (proxy, method, args1) -> {
            System.out.println("前置增强");
            return method.invoke(test);

        });

        userService.test();
    }

    @Override
    public void test() {
        System.out.println("测试类测试");
    }

    @Override
    public void test1() {
        System.out.println("测hi");
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
