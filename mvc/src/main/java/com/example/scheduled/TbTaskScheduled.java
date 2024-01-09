package com.example.scheduled;

import com.example.entity.TbTask;
import com.example.service.TbTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * @author czq
 * @date 2023/10/30 15:12
 * @Description:
 */
@Slf4j
@Component
public class TbTaskScheduled {

    /**
     * 数据库任务
     */
    public ConcurrentHashMap<String, TbTask> tasks = new ConcurrentHashMap<>();


    /**
     * 正在运行的任务
     */
    public ConcurrentHashMap<String, ScheduledFuture> runTasks = new ConcurrentHashMap<>();


    private ThreadPoolTaskScheduler pool = new ThreadPoolTaskScheduler();


    @Resource
    private TbTaskService taskService;


    private void putAllTask() {
        tasks.clear();
        taskService.getAll().forEach(v -> tasks.put(v.getTaskId(), v));
    }


    public void start(String taskId) {
        try {
            if (tasks.isEmpty()) {
                putAllTask();
            }
            TbTask tbTask = tasks.get(taskId);
            Class<?> clazz = Class.forName(tbTask.getTaskClass());
            Runnable task = (Runnable) clazz.newInstance();
            CronTrigger cronTrigger = new CronTrigger(tbTask.getTaskExp());
            runTasks.put(taskId, pool.schedule(task, cronTrigger));
            update(taskId, 1);
            log.info("{}任务启动!", taskId);
        } catch (Exception e) {
            log.error("启动失败", e);
        }

    }


    public void stop(String taskId) {
        runTasks.get(taskId).cancel(true);
        runTasks.remove(taskId);
        update(taskId, 0);
    }


    public void update(String taskId, int status) {
        TbTask tbTask = taskService.getById(taskId);
        tbTask.setTaskStatus(status);
        tbTask.setUpdateTime(new Date());
        taskService.update(tbTask);
    }

}
