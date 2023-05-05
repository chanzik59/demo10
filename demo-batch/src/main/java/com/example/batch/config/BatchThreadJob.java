package com.example.batch.config;

import com.example.batch.entity.User;
import com.example.batch.tools.UserPartitioner;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * @author chenzhiqin
 * @date 4/5/2023 10:10
 * @info XX
 */
@Slf4j
public class BatchThreadJob {


    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    /**
     * 普通平面文件读取  关闭状态覆盖多线程一定关闭，多线程执行不允许重试
     *
     * @return
     */
    @Bean
    public FlatFileItemReader<User> itemReader() {
        return new FlatFileItemReaderBuilder<User>().name("thread_reader")
                .saveState(false)
                .resource(new ClassPathResource("testFile/thread.txt"))
                .delimited().delimiter("#")
                .names("id", "name", "age")
                .targetType(User.class)
                .build();

    }


    @Bean
    public ItemWriter<User> itemWriter() {
        return items -> items.forEach(v -> log.info(v.toString()));
    }


    /**
     * 开启多线程执行
     *
     * @param itemWriter
     * @param itemReader
     * @return
     */
    @Bean
    public Step step(ItemWriter itemWriter, ItemReader itemReader) {
        return stepBuilderFactory.get("thread_step")
                .chunk(1)
                .reader(itemReader)
                .writer(itemWriter)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }


    /**
     * 并行json
     *
     * @return
     */
    @Bean
    public JsonItemReader<User> jsonReader() {
        ObjectMapper mapper = new ObjectMapper();
        JacksonJsonObjectReader<User> jsonObjectReader = new JacksonJsonObjectReader<>(User.class);
        jsonObjectReader.setMapper(mapper);
        return new JsonItemReaderBuilder<User>().name("json_reader")
                .resource(new ClassPathResource("testFile/parallel.json"))
                .jsonObjectReader(jsonObjectReader)
                .build();

    }


    /**
     * 并行txt
     *
     * @return
     */
    @Bean
    public FlatFileItemReader txtReader() {
        return new FlatFileItemReaderBuilder<User>()
                .name("txt_reader")
                .resource(new ClassPathResource("testFile/parallel.txt"))
                .delimited().delimiter("#").names("id", "name", "age").targetType(User.class)
                .build();
    }


    @Bean
    public Step jsonStep(ItemReader jsonReader, ItemWriter itemWriter) {
        return stepBuilderFactory.get("json_step")
                .<User, User>chunk(1)
                .reader(jsonReader)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public Step txtStep(ItemReader txtReader, ItemWriter itemWriter) {
        return stepBuilderFactory.get("txt_step")
                .<User, User>chunk(1)
                .reader(txtReader)
                .writer(itemWriter)
                .build();
    }


    /**
     * 通用文件内读取器
     *
     * @param resource
     * @return
     */
    @Bean
    @StepScope
    public FlatFileItemReader<User> workReader(@Value("#{stepExecutionContext['file']}") Resource resource) {
        return new FlatFileItemReaderBuilder<User>()
                .name("work_reader")
                .resource(resource)
                .delimited().delimiter("#").names("id", "name", "age").targetType(User.class)
                .build();
    }


    /**
     * 从步骤
     *
     * @param workReader
     * @param itemWriter
     * @return
     */
    @Bean
    public Step workStep(ItemReader workReader, ItemWriter itemWriter) {
        return stepBuilderFactory.get("work_step")
                .chunk(1)
                .reader(workReader)
                .writer(itemWriter)
                .build();

    }

    /**
     * 主步骤
     *
     * @param workStep
     * @param partitioner
     * @param handler
     * @return
     */
    @Bean
    public Step mainStep(Step workStep, UserPartitioner partitioner, PartitionHandler handler) {
        return stepBuilderFactory.get("main_step")
                .partitioner(workStep.getName(), partitioner)
                .partitionHandler(handler)
                .build();
    }


    /**
     * 分期处理器
     *
     * @param workStep
     * @return
     */
    @Bean
    public PartitionHandler handler(Step workStep) {
        TaskExecutorPartitionHandler handler = new TaskExecutorPartitionHandler();
        handler.setGridSize(5);
        handler.setTaskExecutor(new SimpleAsyncTaskExecutor());
        handler.setStep(workStep);
        return handler;

    }

    @Bean
    public Job partitionJob(Step mainStep) {
        return jobBuilderFactory.get("part_job14")
                .start(mainStep)
                .build();
    }


    public Job job(Step jsonStep, Step txtStep) {
        Flow jsonFlow = new FlowBuilder<Flow>("json_flow").start(jsonStep).build();
        //开启并行操作
        Flow flow = new FlowBuilder<Flow>("txt_flow").start(txtStep).split(new SimpleAsyncTaskExecutor()).add(jsonFlow).build();
        return jobBuilderFactory.get("thread_job4")
                .start(flow).end().build();
    }

}
