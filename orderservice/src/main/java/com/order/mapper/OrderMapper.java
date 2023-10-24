package com.order.mapper;

import com.order.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenzhiqin
 * @date 1/9/2023 11:26
 * @info XX
 */
@Mapper
public interface OrderMapper {

    /**
     * 更新
     *
     * @param order
     * @return
     */
    int update(Order order);

    /**
     * 新增
     *
     * @param order
     * @return
     */
    @Insert(" insert into t_order (order_name,amt,user_id) values ( #{orderName},#{amt},#{userId} )")
    int save(Order order);
}
