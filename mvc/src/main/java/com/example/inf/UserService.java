package com.example.inf;

import com.example.ant.Secure;
import com.example.entity.AA;
import com.example.entity.User;


/**
 * @author czq
 * @date 2023/10/10 17:05
 * @Description:
 */

public interface UserService {

    void test();


    void test1();


    @Secure
    void test2(AA aa);

    void test4(AA bb);


    User findUser();
}
