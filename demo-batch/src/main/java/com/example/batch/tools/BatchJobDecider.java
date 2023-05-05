package com.example.batch.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author chenzhiqin
 * @date 18/4/2023 14:25
 * @info 自定义决策器
 */
@Component
@Slf4j
public class BatchJobDecider implements JobExecutionDecider {
    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        int anInt = new Random().nextInt(3);
        if (1 == anInt) {
            return new FlowExecutionStatus("A");
        } else if (2 == anInt) {
            return new FlowExecutionStatus("B");
        } else {
            return new FlowExecutionStatus("C");
        }
    }
}
