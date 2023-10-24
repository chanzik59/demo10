package com.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * @author chenzhiqin
 * @date 1/9/2023 14:12
 * @info XX
 */

@Mapper
public interface UserMapper {

    /**
     * 更新
     *
     * @param id
     * @param amt
     * @return
     */
    @Update("update t_user set amt=amt-#{amt} where id=#{id} and amt >= #{amt}")
    int update(Long id, BigDecimal amt);
}
