package com.example.inf;

import com.example.ant.Secure;
import org.springframework.stereotype.Service;

/**
 * @author czq
 * @date 2023/10/12 17:18
 * @Description:
 */
@Secure
@Service
public class AnimalServiceImpl implements  AnimalService{
    @Override
    public void test() {
        System.out.println("测试animal");
    }
}
