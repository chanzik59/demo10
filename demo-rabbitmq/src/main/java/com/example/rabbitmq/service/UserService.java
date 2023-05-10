package com.example.rabbitmq.service;

import com.example.rabbitmq.entity.User;

/**
 * @author chenzhiqin
 * @date 10/5/2023 10:58
 * @info 用户应用层
 */
public interface UserService {

    /**
     * 数据新增
     *
     * @param user
     * @return
     */
    int addUser(User user);


    /**
     * 数据新增
     *
     * @param user
     * @return
     */
    int addUserTmp(User user);
}
