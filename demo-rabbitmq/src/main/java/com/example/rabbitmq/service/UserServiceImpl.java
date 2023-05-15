package com.example.rabbitmq.service;

import com.example.rabbitmq.entity.User;
import com.example.rabbitmq.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chenzhiqin
 * @date 10/5/2023 10:59
 * @info 用户应用实现
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public int addUser(User user) {
        return userMapper.save(user);
    }

    @Override
    public int addUserTmp(User user) {
        return userMapper.saveTemp(user);
    }
}
