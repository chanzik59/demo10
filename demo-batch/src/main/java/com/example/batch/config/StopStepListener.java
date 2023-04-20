package com.example.batch.config;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 19/4/2023 10:44
 * @info 步骤监听器中断作业
 */
@Component
public class StopStepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        if(!Count.taskCount.equals(Count.readCount)){
            return  ExitStatus.STOPPED;
        }
        return stepExecution.getExitStatus();
    }
}
