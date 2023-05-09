package com.example.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenzhiqin
 * @date 9/5/2023 13:59
 * @info 普通任务配置类
 */
@Slf4j
@Configuration
public class BatchTaskletConfig {


    /**
     * 延迟获取作业参数
     *
     * @param name
     * @return
     */
    @StepScope
    @Bean
    public Tasklet annotationJobParam(@Value("#{jobParameters['name']}") String name) {
        return (contribution, chunkContext) -> {
            log.info("通过注解延迟获取作业参数:{}", name);
            return RepeatStatus.FINISHED;
        };
    }


}
