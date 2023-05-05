package com.example.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 18/4/2023 10:05
 * @info 步骤监听器
 */
@Component
@Slf4j
public class BatchStepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {

        log.info("步骤执行之前：{}", stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("步骤执行之后：{}", stepExecution.getStepName());
        return stepExecution.getExitStatus();
    }
}
