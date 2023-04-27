package com.example.batch.config;

import com.example.batch.entity.User;
import com.example.batch.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemProcessorAdapter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author chenzhiqin
 * @date 27/4/2023 14:26
 * @info XX
 */
//@Component
@Slf4j
public class BatchItemProcessor {


    @Resource
    private JobBuilderFactory jobBuilderFactory;


    @Resource
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public FlatFileItemReader<User> itemReader() {
        return new FlatFileItemReaderBuilder<User>().name("Reader").resource(new ClassPathResource("testFile/processor.txt"))
                .delimited().delimiter("#").names("id", "name", "age").targetType(User.class).build();
    }


    @Bean
    public ItemWriter<User> itemWriter() {

        return items -> {
            items.forEach(System.out::println);
        };
    }


    @Bean
    public BeanValidatingItemProcessor<User> itemProcessor() {
        BeanValidatingItemProcessor<User> processor = new BeanValidatingItemProcessor<>();
        processor.setFilter(true);
        return processor;
    }

    @Bean
    public ItemProcessorAdapter<User, User> adapter(UserServiceImpl service) {
        ItemProcessorAdapter<User, User> adapter = new ItemProcessorAdapter<>();
        adapter.setTargetObject(service);
        adapter.setTargetMethod("toUpperCase");
        return adapter;
    }


    /**
     * 组合处理器
     *
     * @param itemProcessor
     * @param adapter
     * @return
     */
    @Bean
    public CompositeItemProcessor<User, User> compositeItemProcessor(ItemProcessor<User, User> itemProcessor, ItemProcessor<User, User> adapter) {
        CompositeItemProcessor<User, User> processor = new CompositeItemProcessor<>();
        processor.setDelegates(Arrays.asList(itemProcessor, adapter));
        return processor;
    }


    @Bean
    public Step step(ItemWriter itemWriter, ItemReader reader, ItemProcessor compositeItemProcessor) {
        return stepBuilderFactory.get("processor3").chunk(1).reader(reader).processor(compositeItemProcessor).writer(itemWriter).build();
    }


    @Bean
    public Job job(Step step) {
        return jobBuilderFactory.get("processorJob3").start(step).build();
    }

}
