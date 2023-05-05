package com.example.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 17/4/2023 17:46
 * @info XX
 */
@Slf4j
@Component
public class AnnotationListener {

    @BeforeJob
    public void before(JobExecution jobExecution) {

        log.info("执行之前{}", jobExecution.getStatus());
    }

    @AfterJob
    public void after(JobExecution jobExecution) {

        log.info("执行之后{}", jobExecution.getStatus());
    }
}
