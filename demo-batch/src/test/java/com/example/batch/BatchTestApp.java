package com.example.batch;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author chenzhiqin
 * @date 18/4/2023 16:07
 * @info XX
 */
@SpringBootTest(classes = BatchApplication.class)
@Slf4j
public class BatchTestApp {

    @Autowired
    private JobLauncher jobLauncher;


    @Autowired
    private JobBuilderFactory jobBuilderFactory;


    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    public Tasklet tasklet() {
        return (contribution, chunkContext) -> {
            log.info("作业参数" + chunkContext.getStepContext().getJobParameters());
            return RepeatStatus.FINISHED;
        };
    }

    public Step step1() {
        TaskletStep step1 = stepBuilderFactory.get("step1")
                .tasklet(tasklet())
                .build();
        return step1;
    }

    public Job job() {
        Job job = jobBuilderFactory.get("start-test-job1")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
        return job;
    }


    @Test
    public void test() throws Exception {
        jobLauncher.run(job(), new JobParameters());
    }


}
