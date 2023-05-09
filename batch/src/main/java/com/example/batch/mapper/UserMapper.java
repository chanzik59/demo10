package com.example.batch.mapper;

import com.example.batch.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author chenzhiqin
 * @date 9/5/2023 17:29
 * @info 用户持久层
 */
public interface UserMapper {

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    List<User> allUser(Map<String, Object> params);

    /**
     * 新增数据
     *
     * @param user
     * @return
     */
    int save(User user);


    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    List<User> all();
}
