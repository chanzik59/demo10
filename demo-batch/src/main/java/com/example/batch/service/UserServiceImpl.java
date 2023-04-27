package com.example.batch.service;

import com.example.batch.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author chenzhiqin
 * @date 27/4/2023 14:44
 * @info XX
 */
@Service
public class UserServiceImpl {

    public User toUpperCase(User user) {
        user.setName(user.getName().toUpperCase());
        return user;
    }
}
