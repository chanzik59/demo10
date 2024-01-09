package com.example.scheduled;

import com.example.entity.TaskInfo;
import com.example.inf.UserService;
import com.example.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 动态定时任务实现步骤
 * 步骤1：定义定时任务 DownLoadTaskV3 类实现 SchedulingConfigurer 接口；
 * 步骤2：编写定时任务要执行的业务逻辑；
 * 步骤3：数据库中配置任务执行的具体时间规则，记住任务名称
 * 步骤4：根据任务名称从数据库获取 Cron 参数，设置任务触发器，触发任务执行。
 * （仅抛砖引玉，可作进一步的抽象）
 */
@Slf4j
@ConditionalOnMissingClass
@Component
public class DownLoadTaskV3 implements SchedulingConfigurer {

    @Resource
    private TaskService taskService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        Runnable task = () -> log.info("执行任务");

        // 步骤 4：根据任务名称从数据库获取 Cron 参数，设置任务触发器，触发任务执行。
        Trigger trigger = triggerContext -> {
            TaskInfo taskInfo = taskService.get("job2");
            log.info("被执行{}",taskInfo.toString());

            CronTrigger trigger1 = new CronTrigger(taskInfo.getCron());
            return trigger1.nextExecutionTime(triggerContext);
        };


        // 设置任务触发器，触发任务执行。
        taskRegistrar.addTriggerTask(task, trigger);
    }


}