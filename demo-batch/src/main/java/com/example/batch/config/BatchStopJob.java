package com.example.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * @author chenzhiqin
 * @date 19/4/2023 10:43
 * @info XX
 */
//@Component
@Slf4j
public class BatchStopJob {

    @Resource
    private JobBuilderFactory jobBuilderFactory;


    @Resource
    private StepBuilderFactory stepBuilderFactory;

    private Integer simulateCount = 100;


    @Bean
    public Step step1(StopStepListener listener) {
        return stepBuilderFactory.get("step1").listener(listener).tasklet((contribution, chunkContext) -> {

            for (int i = 0; i < simulateCount; i++) {
                log.info("执行步骤1:{}", Count.readCount++);
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
        return jobBuilderFactory.get("stopJob").start(step1).on("STOPPED").stopAndRestart(step1)
                .from(step1).on("*").to(step2).end().build();
    }







}
