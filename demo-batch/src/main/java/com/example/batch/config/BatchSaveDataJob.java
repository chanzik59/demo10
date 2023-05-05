package com.example.batch.config;

import com.example.batch.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author chenzhiqin
 * @date 5/5/2023 11:30
 * @info 读取文件存数据库
 */
@Slf4j
@Configuration
public class BatchSaveDataJob {

    @Resource
    private JobBuilderFactory jobBuilderFactory;

    @Resource
    private StepBuilderFactory stepBuilderFactory;


    @Value("${batch.save.path}")
    private String path;


    @Bean("csvReader")
    public FlatFileItemReader<Employee> itemReader() {
        return new FlatFileItemReaderBuilder<Employee>().name("cvs_reader")
                .resource(new PathResource(new File(path, "employee.csv").getAbsolutePath()))
                .saveState(false)
                .delimited().delimiter(",").names("id", "name", "age", "sex").targetType(Employee.class)
                .build();
    }


    @Bean("dbWriter")
    public MyBatisBatchItemWriter<Employee> itemWriter(SqlSessionFactory sqlSessionFactory) {
        MyBatisBatchItemWriter<Employee> itemWriter = new MyBatisBatchItemWriter<>();
        itemWriter.setSqlSessionFactory(sqlSessionFactory);
        itemWriter.setStatementId("com.example.batch.mapper.EmployeeMapper.saveTemp");
        return itemWriter;
    }

    @Bean
    public Step csvToDbStep(ItemReader<Employee> csvReader, ItemWriter<Employee> dbWriter) {
        return stepBuilderFactory.get("save_step")
                .<Employee,Employee>chunk(10000)
                .reader(csvReader)
                .writer(dbWriter)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public Job saveDbJob(Step csvToDbStep, JobExecutionListener jobTimeListener) {
        return jobBuilderFactory.get("save_job")
                .incrementer(new RunIdIncrementer()).listener(jobTimeListener)
                .start(csvToDbStep).build();
    }

}
