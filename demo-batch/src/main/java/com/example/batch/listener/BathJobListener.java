package com.example.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * 作业监听器
 *
 * @author chenzhiqin
 * @date 17/4/2023 17:06
 * @info XX
 */
@Component
@Slf4j
public class BathJobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("执行之前{}",jobExecution.getStatus());

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("执行之后{}",jobExecution.getStatus());
    }
}
