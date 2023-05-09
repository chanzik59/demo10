package com.example.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 9/5/2023 15:36
 * @info 作业监听器
 */
@Slf4j
@Component
public class BatchJobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {

        log.info("作业:{} 执行之前{}", jobExecution.getId(), jobExecution.getStatus());

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("作业:{} 执行之后{}", jobExecution.getId(), jobExecution.getStatus());
    }
}
