package com.example.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 5/5/2023 11:19
 * @info 作业执行耗时
 */
@Component
@Slf4j
public class JobTimeListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        jobExecution.getExecutionContext().putLong("begin", System.currentTimeMillis());

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String name = jobExecution.getJobInstance().getJobName();
        long time = System.currentTimeMillis() - jobExecution.getExecutionContext().getLong("begin");

        log.info("作业：{}，耗时{}毫秒", name, time);

    }
}
