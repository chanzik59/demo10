package com.example.controller;

import com.example.entity.TbTask;
import com.example.scheduled.TbTaskScheduled;
import com.example.service.TbTaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author czq
 * @date 2023/10/31 17:31
 * @Description:
 */
@RequestMapping("task")
@RestController
public class TaskController {

    @Resource
    private TbTaskScheduled tbTaskScheduled;

    @Resource
    private TbTaskService taskService;


    @RequestMapping("start")
    public String start(String taskId) {
        tbTaskScheduled.start(taskId);
        return "任务启动成功:" + taskId;
    }


    @RequestMapping("stop")
    public String stop(String taskId) {
        tbTaskScheduled.stop(taskId);
        return "任务关停成功:" + taskId;
    }


    @RequestMapping("update")
    public String update(TbTask tbTask) {
        if (1 != taskService.update(tbTask)) {
            return "更改失败";
        }
        tbTaskScheduled.tasks.clear();
        tbTaskScheduled.stop(tbTask.getTaskId());
        tbTaskScheduled.start(tbTask.getTaskId());
        return "更改成功";
    }


}
