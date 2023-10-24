package com.user.service;

import com.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author chenzhiqin
 * @date 1/9/2023 14:25
 * @info XX
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean deduction(Long id, BigDecimal amt) {
        int result = userMapper.update(id, amt);
        if (1 == result) {
            return true;
        } else {
            throw new RuntimeException("余额不足");
        }
    }
}
