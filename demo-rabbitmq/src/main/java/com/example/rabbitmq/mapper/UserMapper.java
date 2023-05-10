package com.example.rabbitmq.mapper;

import com.example.rabbitmq.entity.User;

/**
 * @author chenzhiqin
 * @date 10/5/2023 10:50
 * @info 持久层
 */
public interface UserMapper {

    /**
     * 数据新增
     *
     * @param user
     * @return
     */
    int save(User user);

    /**
     * 临时表数据新增
     *
     * @param user
     * @return
     */
    int saveTemp(User user);
}
