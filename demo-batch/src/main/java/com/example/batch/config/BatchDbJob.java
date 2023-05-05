package com.example.batch.config;

import com.example.batch.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenzhiqin
 * @date 5/5/2023 15:15
 * @info 数据库转数据库
 */
@Slf4j
@Configuration
public class BatchDbJob {

    @Resource
    private JobBuilderFactory jobBuilderFactory;

    @Resource
    private StepBuilderFactory stepBuilderFactory;


    public static int pageSize = 1000;

    public static int range = 10000;

    public static int gridSize = 50;


    @Bean
    @StepScope
    public MyBatisPagingItemReader<Employee> dbReader(@Value("#{stepExecutionContext['from']}") Integer from,
                                                      @Value("#{stepExecutionContext['to']}") Integer to,
                                                      SqlSessionFactory sqlSessionFactory) {

        MyBatisPagingItemReader<Employee> itemReader = new MyBatisPagingItemReader<>();
        itemReader.setSqlSessionFactory(sqlSessionFactory);
        itemReader.setQueryId("com.example.batch.mapper.EmployeeMapper.getTempByPage");
        itemReader.setPageSize(pageSize);
        Map<String, Object> params = new HashMap<>();
        params.put("from", from);
        params.put("to", to);
        itemReader.setParameterValues(params);

        return itemReader;
    }


    @Bean
    public MyBatisBatchItemWriter<Employee> dbWriter(SqlSessionFactory sqlSessionFactory) {
        MyBatisBatchItemWriter<Employee> itemWriter = new MyBatisBatchItemWriter<>();
        itemWriter.setSqlSessionFactory(sqlSessionFactory);
        itemWriter.setStatementId("com.example.batch.mapper.EmployeeMapper.save");
        return itemWriter;

    }


    @Bean("employeeWork")
    public Step workStep(ItemWriter dbWriter, ItemReader dbReader) {
        return stepBuilderFactory.get("work_step")
                .chunk(pageSize)
                .reader(dbReader)
                .writer(dbWriter)
                .build();
    }


    @Bean("employeeHandler")
    public PartitionHandler partitionHandler(Step employeeWork) {
        TaskExecutorPartitionHandler handler = new TaskExecutorPartitionHandler();
        handler.setGridSize(gridSize);
        handler.setTaskExecutor(new SimpleAsyncTaskExecutor());
        handler.setStep(employeeWork);
        return handler;
    }

    @Bean("employeeMain")
    public Step mainStep(Step employeeWork, PartitionHandler employeeHandler, Partitioner employeePartitioner) {

        return stepBuilderFactory.get("main_step")
                .partitioner(employeeWork.getName(), employeePartitioner)
                .partitionHandler(employeeHandler)
                .build();
    }


    @Bean
    public Job employeeJob(Step employeeMain, JobExecutionListener jobTimeListener) {
        return jobBuilderFactory.get("employee_job")
                .incrementer(new RunIdIncrementer())
                .start(employeeMain)
                .listener(jobTimeListener)
                .build();
    }


}
