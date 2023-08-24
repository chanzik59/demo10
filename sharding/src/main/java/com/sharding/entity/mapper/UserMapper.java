package com.sharding.entity.mapper;

import com.sharding.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenzhiqin
 * @date 18/8/2023 11:37
 * @info XX
 */
@Mapper
public interface UserMapper {

    /**
     * 新增
     *
     * @param user
     * @return
     */
    @Insert("insert into t_user(id,age,name) values( #{id},#{age},#{name})")
    int addUser(User user);
}
