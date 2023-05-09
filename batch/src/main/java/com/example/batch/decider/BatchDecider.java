package com.example.batch.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author chenzhiqin
 * @date 9/5/2023 16:25
 * @info 自定义决策器
 */
@Component
public class BatchDecider implements JobExecutionDecider {
    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        JobParameters jobParameters = jobExecution.getJobParameters();
        String age = jobParameters.getString("age");
        if (Objects.nonNull(age) && Integer.valueOf(age) > 200) {
            return new FlowExecutionStatus("kid");
        }
        return FlowExecutionStatus.COMPLETED;
    }
}
