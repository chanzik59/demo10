package com.example.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chenzhiqin
 * @date 19/4/2023 10:43
 * @info 暂停标识实现暂停
 */
//@Component
@Slf4j
public class BatchStopFlagJob {

    @Resource
    private JobBuilderFactory jobBuilderFactory;


    @Resource
    private StepBuilderFactory stepBuilderFactory;

    private Integer simulateCount = 50;


    @Bean
    public Step step1(StopStepListener listener) {
        return stepBuilderFactory.get("step1").startLimit(2).listener(listener).tasklet((contribution, chunkContext) -> {

            for (int i = 0; i < simulateCount; i++) {
                log.info("执行步骤1:{}", Count.readCount++);
            }
            if (!Count.taskCount.equals(Count.readCount)) {
//                chunkContext.getStepContext().getStepExecution().setTerminateOnly();
            }
            return RepeatStatus.FINISHED;

        }).build();
    }


    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2").tasklet((contribution, chunkContext) -> {

            log.info("执行步骤2");
            return RepeatStatus.FINISHED;

        }).build();
    }


    @Bean
    public Job job(Step step1, Step step2) {
        return jobBuilderFactory.get("stopJob6").start(step1).next(step2).build();
    }


}
