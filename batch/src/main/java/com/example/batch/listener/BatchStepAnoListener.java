package com.example.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 9/5/2023 15:59
 * @info 步骤注解监听器
 */
@Slf4j
@Component
public class BatchStepAnoListener {

    @BeforeStep
    public void before() {
        log.info("执行之前");
    }

    @AfterStep
    public void after() {
        log.info("执行之后");
    }
}
