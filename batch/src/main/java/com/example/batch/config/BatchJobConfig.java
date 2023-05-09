package com.example.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author chenzhiqin
 * @date 9/5/2023 13:57
 * @info job配置类
 */
@Configuration
public class BatchJobConfig {

    @Resource
    private JobBuilderFactory jobBuilderFactory;

    /**
     * 获取到job参数
     *
     * @param jobParamStep
     * @return
     */
    @Bean
    public Job paramJob(Step jobParamStep, Step contextStep) {
        return jobBuilderFactory.get("param_job").incrementer(new RunIdIncrementer()).start(jobParamStep).next(contextStep).build();
    }

    /**
     * 获取到job参数
     *
     * @param annotationParamStep
     * @return
     */
    @Bean
    public Job anoParamJob(Step annotationParamStep, JobParametersValidator compositeValidator, JobExecutionListener batchJobListener) {
        return jobBuilderFactory.get("anoParamJob").listener(batchJobListener).validator(compositeValidator).start(annotationParamStep).build();
    }


    @Bean
    public Job conditionJob(Step step1, Step step2, Step step3, JobExecutionDecider decider) {
        return jobBuilderFactory.get("condition").start(step1).on("FAILED").to(step3)
                .from(step1).on("*").to(decider).on("kid").to(step3)
                .from(decider).on("*").to(step2).end().build();
    }

    @Bean
    public Job flatJob(Step flatStep) {
        return jobBuilderFactory.get("flatJob").start(flatStep).build();
    }


    @Bean
    public Job jsonJob(Step jsonStep) {
        return jobBuilderFactory.get("jsonJob").start(jsonStep).build();
    }



    @Bean
    public Job myBatisJob(Step myBatisStep) {
        return jobBuilderFactory.get("myBatisJob").start(myBatisStep).build();
    }
}
