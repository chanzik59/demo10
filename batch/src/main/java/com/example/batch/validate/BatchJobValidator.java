package com.example.batch.validate;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author chenzhiqin
 * @date 9/5/2023 14:56
 * @info 自定义作业参数校验
 */
@Configuration
public class BatchJobValidator implements JobParametersValidator {
    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        String name = parameters.getString("name");
        if ("小芳".equals(name)) {
            throw new JobParametersInvalidException("不能叫小芳");
        }

    }

    /**
     * 默认作业参数校验
     *
     * @return
     */
    @Bean
    public DefaultJobParametersValidator parametersValidator() {
        DefaultJobParametersValidator validator = new DefaultJobParametersValidator();
        validator.setRequiredKeys(new String[]{"name", "age", "run.id"});
        validator.setOptionalKeys(new String[]{"address"});
        return validator;
    }


    /**
     * 组合参数校验器
     *
     * @return
     */
    @Bean
    public CompositeJobParametersValidator compositeValidator() {
        CompositeJobParametersValidator validator = new CompositeJobParametersValidator();
        validator.setValidators(Arrays.asList(this, parametersValidator()));
        return validator;
    }
}
