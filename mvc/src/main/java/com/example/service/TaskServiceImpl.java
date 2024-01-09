package com.example.service;

import com.example.entity.TaskInfo;
import com.example.mapper.TaskMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author czq
 * @date 2023/10/26 16:00
 * @Description:
 */

@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;

    @Override
    public TaskInfo get(String jobName) {
        return taskMapper.get(jobName);
    }
}
