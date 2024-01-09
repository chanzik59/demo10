package com.example.mapper;

import com.example.entity.TbTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author czq
 * @date 2023/10/26 15:37
 * @Description:
 */


public interface TbTaskMapper {

    List<TbTask> selectAll();


    TbTask selectById(String taskId);


    int update(TbTask tbTask);


}
