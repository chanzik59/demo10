package com.example.batch.validate;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 17/4/2023 15:17
 * @info XX
 */
@Component
public class BatchValidate implements JobParametersValidator {
    @Override
    public void validate(JobParameters jobParameters) throws JobParametersInvalidException {
        if ("小刚".equals(jobParameters.getString("name"))) {
            throw new JobParametersInvalidException("名字不能叫小刚");
        }


    }
}
