package com.example.service;

import com.example.entity.TbTask;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author czq
 * @date 2023/10/26 15:33
 * @Description:
 */
public interface TbTaskService {

    List<TbTask> getAll();

    TbTask getById(String taskId);

    int update(TbTask tbTask);

}
