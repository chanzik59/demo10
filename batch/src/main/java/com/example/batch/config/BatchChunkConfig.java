package com.example.batch.config;

import com.example.batch.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author chenzhiqin
 * @date 9/5/2023 13:58
 * @info 块配置类
 */
@Configuration
public class BatchChunkConfig {


    @Value("${batch.save.path}")
    private String path;

    @Resource
    private SqlSessionFactory sqlSessionFactory;


    /**
     * 平面文件读取
     *
     * @param file
     * @return
     */
    @StepScope
    @Bean
    public FlatFileItemReader<User> userReader(@Value("#{jobParameters['file']}") String file) {
        return new FlatFileItemReaderBuilder<User>().name("user-reader")
                .resource(new ClassPathResource("files/" + file))
                .delimited().delimiter("#").names("id", "name", "age").targetType(User.class)
                .build();
    }

    /**
     * 平面文件写入
     *
     * @param file
     * @return
     */
    @StepScope
    @Bean
    public FlatFileItemWriter<User> userWriter(@Value("#{jobParameters['file']}") String file) {
        return new FlatFileItemWriterBuilder<User>().name("user-writer")
                .resource(new PathResource(path + "/" + file))
                .delimited().delimiter("$").names("id", "name", "age")
                .build();
    }


    /**
     * json 文件读取
     *
     * @param file
     * @return
     */
    @StepScope
    @Bean
    public JsonItemReader<User> jsonReader(@Value("#{jobParameters['file']}") String file) {
        JacksonJsonObjectReader<User> jsonObjectReader = new JacksonJsonObjectReader<>(User.class);
        jsonObjectReader.setMapper(new ObjectMapper());
        return new JsonItemReaderBuilder<User>().jsonObjectReader(jsonObjectReader)
                .resource(new ClassPathResource("files/" + file))
                .name("json-reader")
                .build();
    }


    /**
     * json 文件写入
     *
     * @param file
     * @return
     */
    @StepScope
    @Bean
    public JsonFileItemWriter<User> jsonWriter(@Value("#{jobParameters['file']}") String file) {
        return new JsonFileItemWriterBuilder<User>()
                .name("json-writer")
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(new PathResource(path + "/" + file))
                .build();
    }

    @Bean
    public MyBatisPagingItemReader<User> myBatisReader() {
        MyBatisPagingItemReader<User> reader = new MyBatisPagingItemReader<>();
        reader.setSqlSessionFactory(sqlSessionFactory);
        reader.setPageSize(100);
        reader.setQueryId("com.example.batch.mapper.UserMapper.allUser");
        return reader;
    }

    @Bean
    public BeanValidatingItemProcessor<User> validateProcessor() {
        BeanValidatingItemProcessor<User> itemProcessor = new BeanValidatingItemProcessor<>();
        itemProcessor.setFilter(true);
        return itemProcessor;
    }


    @Bean
    public CompositeItemProcessor<User, User> compositeProcessor(ItemProcessor<User, User> validateProcessor, ItemProcessor<User, User> itemAgeFilter) {
        CompositeItemProcessor<User, User> processor = new CompositeItemProcessor<>();
        processor.setDelegates(Arrays.asList(validateProcessor, itemAgeFilter));
        return processor;
    }


    @Bean
    public MyBatisBatchItemWriter<User> myBatisWriter() {
        MyBatisBatchItemWriter<User> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(sqlSessionFactory);
        writer.setStatementId("com.example.batch.mapper.UserMapper.save");
        return writer;
    }


}
