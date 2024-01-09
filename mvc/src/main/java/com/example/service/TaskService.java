package com.example.service;

import com.example.entity.TaskInfo;

/**
 * @author czq
 * @date 2023/10/26 15:33
 * @Description:
 */
public interface TaskService {

    TaskInfo get(String jobName);
}
