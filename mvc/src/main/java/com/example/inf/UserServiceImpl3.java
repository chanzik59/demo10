package com.example.inf;

import com.example.condition.UserServiceCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * @author czq
 * @date 2023/10/13 11:40
 * @Description:
 */

//@ConditionalOnBean(value = UserServiceFa.class)
@ConditionalOnMissingBean(value = UserServiceFa.class)
@Service
public class UserServiceImpl3 extends UserServiceImpl {

    @Override
    public void test() {
        System.out.println("this is UserServiceImpl3");
    }

    public void test3() {
        System.out.println("user3  test3");
    }

}
