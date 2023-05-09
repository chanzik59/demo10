package com.example.batch.config;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author chenzhiqin
 * @date 9/5/2023 14:33
 * @info 批量配置类
 */
@Configuration
public class BatchConfig implements ApplicationContextAware {


    @Resource
    private JobLauncher jobLauncher;

    @Resource
    private JobExplorer jobExplorer;

    @Resource
    private JobRegistry jobRegistry;

    @Resource
    private JobRepository jobRepository;

    private ApplicationContext applicationContext;

    /**
     * job注册
     *
     * @return
     */
    @Bean
    public JobRegistryBeanPostProcessor processor() {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
        postProcessor.setJobRegistry(jobRegistry);
        postProcessor.setBeanFactory(applicationContext.getAutowireCapableBeanFactory());
        return postProcessor;
    }


    @Bean
    public JobOperator jobOperator() {
        SimpleJobOperator simpleJobOperator = new SimpleJobOperator();
        simpleJobOperator.setJobLauncher(jobLauncher);
        simpleJobOperator.setJobParametersConverter(new DefaultJobParametersConverter());
        simpleJobOperator.setJobRegistry(jobRegistry);
        simpleJobOperator.setJobExplorer(jobExplorer);
        simpleJobOperator.setJobRepository(jobRepository);
        return simpleJobOperator;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
