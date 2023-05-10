package com.example.batch.config;

import com.example.batch.entity.User;
import com.example.batch.listener.BatchStepAnoListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author chenzhiqin
 * @date 9/5/2023 13:58
 * @info 步骤配置类
 */
@Slf4j
@Configuration
public class BatchStepConfig {
    @Resource
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Step jobParamStep(StepExecutionListener stepAnoListener) {
        return stepBuilderFactory.get("job_param").listener(stepAnoListener).tasklet((contribution, chunkContext) -> {
            JobParameters jobParameters = contribution.getStepExecution().getJobParameters();
            String name = jobParameters.getString("name");
            log.info("传递进来得参数:{}", name);
            ExecutionContext stepContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();
            stepContext.put("step", "stepContext");
            ExecutionContext jobContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
            jobContext.put("job", "jobContext");

            return RepeatStatus.FINISHED;
        }).build();
    }


    @Bean
    public Step contextStep(BatchStepAnoListener stepAnoListener) {
        return stepBuilderFactory.get("context_step").listener(stepAnoListener).tasklet((contribution, chunkContext) -> {
            ExecutionContext stepContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();
            log.info("step参数{}", stepContext.get("step"));
            ExecutionContext jobContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
            log.info("job参数{}", jobContext.get("job"));
            return RepeatStatus.FINISHED;
        }).build();
    }


    @Bean
    public Step annotationParamStep(Tasklet annotationJobParam) {
        return stepBuilderFactory.get("job_param1").tasklet(annotationJobParam).build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").tasklet((contribution, chunkContext) -> {
            JobParameters jobParameters = contribution.getStepExecution().getJobParameters();
            String age = jobParameters.getString("age");
            if (Objects.nonNull(age) && Integer.valueOf(age) >= 18) {
                return RepeatStatus.FINISHED;
            } else {
                throw new RuntimeException("未成年");
            }
        }).build();
    }


    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2").tasklet((contribution, chunkContext) -> {
            log.info("步骤1执行成功");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3").tasklet((contribution, chunkContext) -> {
            log.info("步骤1执行失败");
            return RepeatStatus.FINISHED;
        }).build();
    }


    @Bean
    public Step flatStep(ItemReader<User> userReader, ItemWriter userWriter) {
        return stepBuilderFactory.get("flat_step")
                .<User, User>chunk(10)
                .reader(userReader)
                .writer(userWriter)
                .build();
    }


    @Bean
    public Step jsonStep(ItemReader<User> jsonReader, ItemWriter jsonWriter) {
        return stepBuilderFactory.get("json_step")
                .<User, User>chunk(10)
                .reader(jsonReader)
                .writer(jsonWriter)
                .build();
    }


    @Bean
    public Step myBatisStep(ItemReader<User> myBatisReader, ItemWriter myBatisWriter, ItemProcessor compositeProcessor) {
        return stepBuilderFactory.get("mybatis_step")
                .<User, User>chunk(10)
                .reader(myBatisReader)
                .processor(compositeProcessor)
                .writer(myBatisWriter)
                .build();
    }



}
