package com.example.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 9/5/2023 15:55
 * @info 步骤监听器
 */
@Slf4j
@Component
public class BatchStepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("步骤:{} 执行之前{}", stepExecution.getId(), stepExecution.getExitStatus());

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("步骤:{} 执行之后{}", stepExecution.getId(), stepExecution.getExitStatus());
        return ExitStatus.COMPLETED;
    }
}
