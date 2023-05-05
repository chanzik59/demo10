package com.example.batch.tools;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 17/4/2023 16:59
 * @info XX
 */
@Component
public class DateTimeIncrementer implements JobParametersIncrementer {

    /**
     * 自定义时间增长
     *
     * @param parameters
     * @return
     */
    @Override
    public JobParameters getNext(JobParameters parameters) {
        return new JobParametersBuilder(parameters).addLong("daily", System.currentTimeMillis()).toJobParameters();
    }
}
