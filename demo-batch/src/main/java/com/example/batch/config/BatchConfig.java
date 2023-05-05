package com.example.batch.config;

import com.example.batch.listener.AnnotationListener;
import com.example.batch.listener.BatchChunkListener;
import com.example.batch.listener.BatchStepListener;
import com.example.batch.listener.BathJobListener;
import com.example.batch.tools.BatchJobDecider;
import com.example.batch.tools.BatchValidate;
import com.example.batch.tools.DateTimeIncrementer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobListenerFactoryBean;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * @author chenzhiqin
 * @date 17/4/2023 9:59
 * @info XX
 */
@Slf4j
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;


    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Autowired
    private JobLauncher jobLauncher;


    private Integer times = 10;

    /**
     * 默认参数校验
     *
     * @return
     */
    @Bean
    public DefaultJobParametersValidator defaultValidator() {
        DefaultJobParametersValidator validator = new DefaultJobParametersValidator();
        //必填参数
        validator.setRequiredKeys(new String[]{"name", "daily"});
        //可选参数
        validator.setOptionalKeys(new String[]{"age", "run.id"});

        return validator;
    }


    /**
     * 参数组合校验器
     *
     * @param validate
     * @param defaultValidator
     * @return
     */
    @Bean
    public CompositeJobParametersValidator compositeValidator(BatchValidate validate, DefaultJobParametersValidator defaultValidator) {
        CompositeJobParametersValidator compositeValidator = new CompositeJobParametersValidator();
        compositeValidator.setValidators(Arrays.asList(validate, defaultValidator));
        return compositeValidator;

    }


    //    @Bean
    public Job job(AnnotationListener listener, Step stepChunk, @Qualifier("compositeValidator") JobParametersValidator validator, BathJobListener jobListener) {
        return jobBuilderFactory.get("jobIncrement")
                //作业增量器
                .incrementer(new RunIdIncrementer())
                .incrementer(new DateTimeIncrementer())
                .validator(validator)
                .listener(jobListener)
                .listener(JobListenerFactoryBean.getListener(listener))
                .start(stepChunk).build();
    }


    /**
     * 流程化job
     *
     * @param startStep
     * @param successStep
     * @param failStep
     * @return
     */
//    @Bean
    public Job logicJob(Step startStep, Step successStep, Step failStep) {
        return jobBuilderFactory.get("logicJob")
                .incrementer(new RunIdIncrementer())
                .start(startStep).on("FAILED").stopAndRestart(successStep)
                .from(startStep).on("*").to(successStep)
                .end()
                .build();
    }


    @Bean
    public Step step1(Tasklet tasklet1) {
        return stepBuilderFactory.get("stepName1").tasklet(tasklet1).build();
    }


    @Bean
    public Step step(Tasklet tasklet) {
        return stepBuilderFactory.get("stepName").tasklet(tasklet).build();
    }


    @Bean
    public Step startStep() {
        return stepBuilderFactory.get("startStep").tasklet((contribution, chunkContext) -> {
            log.info("流程开始");
            throw new RuntimeException("模拟失败");
//            return RepeatStatus.FINISHED;
        }).build();
    }


    @Bean
    public Step successStep() {
        return stepBuilderFactory.get("successStep").tasklet((contribution, chunkContext) -> {
            log.info("执行成功");
            return RepeatStatus.FINISHED;
        }).build();
    }


    @Bean
    public Step failStep() {
        return stepBuilderFactory.get("failStep").tasklet((contribution, chunkContext) -> {
            log.info("执行失败");
            return RepeatStatus.FINISHED;
        }).build();
    }


    @Bean
    public Step firstStep() {
        return stepBuilderFactory.get("first").tasklet((contribution, chunkContext) -> {
            log.info("first");
            return RepeatStatus.FINISHED;
        }).build();
    }


    /**
     * 自定义决策器
     *
     * @param firstStep
     * @param stepA
     * @param stepB
     * @param stepC
     * @param decider
     * @return
     */
    @Bean
    public Job customJob(Step firstStep, Step stepA, Step stepB, Step stepC, BatchJobDecider decider) {
        return jobBuilderFactory.get("customJob")
                .incrementer(new DateTimeIncrementer())
                .start(firstStep).next(decider)
                .from(decider).on("A").to(stepA)
                .from(decider).on("B").to(stepB)
                .from(decider).on("*").to(stepC)
                .end()
                .build();
    }


    /**
     * 流步骤
     *
     * @param stepA
     * @param stepB
     * @param stepC
     * @return
     */
    @Bean
    public Job flowJob(Step stepA, Step stepB, Step stepC) {
        return jobBuilderFactory.get("customJob")
                .incrementer(new DateTimeIncrementer())
                .start(stepA).next(stepB).next(stepC)
                .build();
    }


    @Bean
    public Step stepA() {
        return stepBuilderFactory.get("stepA").tasklet((contribution, chunkContext) -> {
            log.info("步骤stepA");
            Object name = chunkContext.getStepContext().getJobParameters().get("name");
            log.info("拿到参数{}", name);
            return RepeatStatus.FINISHED;
        }).build();
    }


    @Bean
    public Step stepB(Flow flowB) {
        log.info("步骤stepB");
        return stepBuilderFactory.get("stepB").flow(flowB).build();
    }


    @Bean
    public Step stepB1() {
        return stepBuilderFactory.get("stepB1").tasklet((contribution, chunkContext) -> {
            log.info("步骤stepB1");
            return RepeatStatus.FINISHED;
        }).build();
    }


    @Bean
    public Step stepB2() {
        return stepBuilderFactory.get("stepB2").tasklet((contribution, chunkContext) -> {
            log.info("步骤stepB2");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step stepB3() {
        return stepBuilderFactory.get("stepB3").tasklet((contribution, chunkContext) -> {
            log.info("步骤stepB3");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Flow flowB(Step stepB1, Step stepB2, Step stepB3) {
        return new FlowBuilder<Flow>("flowB").start(stepB1).next(stepB2).next(stepB3).build();

    }


    @Bean
    public Step stepC() {
        return stepBuilderFactory.get("stepC").tasklet((contribution, chunkContext) -> {
            log.info("步骤stepC");
            return RepeatStatus.FINISHED;
        }).build();
    }


    @Bean
    public Step stepChunk(BatchChunkListener chunkListener, BatchStepListener batchStepListener, ItemWriter<String> itemWriter, ItemProcessor<String, String> itemProcessor, ItemReader<String> itemReader) {
        return stepBuilderFactory.get("stepChunk")
                //chunkSize 每次提交多少数据到writer
                .<String, String>chunk(3)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .listener(chunkListener)
                .listener(batchStepListener)

                .build();
    }

    @Bean
    public Step step2(Tasklet tasklet2) {
        return stepBuilderFactory.get("stepName2").tasklet(tasklet2).build();
    }


    /**
     * job Execution  数据job 下step 共享   step Execution  是step 下的数据共享
     *
     * @param name
     * @return
     */
    @Bean
    @StepScope
    public Tasklet tasklet1(@Value("#{jobParameters['name']}") String name) {
        return (stepContribution, chunkContext) -> {


            ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();
            executionContext.put("value-step", "666");
            ExecutionContext executionContext1 = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
            executionContext1.put("value-job", "777");

            log.info("参数{}", chunkContext.getStepContext().getJobParameters());
            log.info("测试batch");
            log.info("参数{}", name);
            return RepeatStatus.FINISHED;
        };
    }


    @Bean
    @StepScope
    public Tasklet tasklet2(@Value("#{jobParameters['name']}") String name) {
        return (stepContribution, chunkContext) -> {
            ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();
            ExecutionContext executionContext1 = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
            log.info("value-step{}", executionContext.get("value-step"));
            log.info("value-job{}", executionContext1.get("value-job"));
            return RepeatStatus.FINISHED;
        };
    }


    @Bean
    public Tasklet tasklet() {

        return (contribution, chunkContext) -> {

            System.out.println("111");

            //continuable 会一直重复执行
            return RepeatStatus.CONTINUABLE;


        };


    }


    @Bean
    public ItemReader<String> itemReader() {
        return () -> {
            String result = "itemReader:" + times--;
            log.info(result);
            if (times == 0) {
                //一直执行直到返回null
                return null;
            } else {
                return result;
            }
        };
    }


    @Bean
    public ItemProcessor<String, String> itemProcessor() {
        return s -> {
            log.info("itemProcessor:" + s);
            return "itemProcessor:" + s;
        };
    }


    @Bean
    public ItemWriter<String> itemWriter() {
        return list -> {
            log.info("itemWriter:" + list);

        };
    }
}
