package com.example.batch.config;

import com.example.batch.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author chenzhiqin
 * @date 27/4/2023 15:04
 * @info XX
 */
@Slf4j
public class BatchItemWriter {

    @Resource
    public StepBuilderFactory stepBuilderFactory;

    @Resource
    public JobBuilderFactory jobBuilderFactory;

    @Bean
    public FlatFileItemReader<User> reader() {

        return new FlatFileItemReaderBuilder<User>().name("reader").resource(new ClassPathResource("testFile/processor.txt"))
                .delimited().delimiter("#").names("id", "name", "age").targetType(User.class).build();

    }


    /**
     * 输出到平面文件
     *
     * @return
     */
    @Bean
    public FlatFileItemWriter<User> writer() {
        return new FlatFileItemWriterBuilder<User>().name("writer").resource(new PathResource("C:\\Users\\phone\\Desktop\\TestFile\\test\\text.txt"))
                .formatted().format("id: %s,姓名：%s,年龄：%s").names("id", "name", "age").build();
    }


    @Bean
    public JacksonJsonObjectMarshaller<User> marshaller() {
        return new JacksonJsonObjectMarshaller<>();
    }


    /**
     * 输出到json
     *
     * @param marshaller
     * @return
     */
    @Bean
    public JsonFileItemWriter<User> jsonWriter(JacksonJsonObjectMarshaller marshaller) {
        return new JsonFileItemWriterBuilder<User>().name("jsonWriter").resource(new PathResource("C:\\Users\\phone\\Desktop\\TestFile\\test\\text.json"))
                .jsonObjectMarshaller(marshaller).build();
    }


    /**
     * 输出到数据库
     *
     * @param dataSource
     * @param setter
     * @return
     */
    @Bean
    public JdbcBatchItemWriter<User> jdbcWriter(DataSource dataSource, ItemPreparedStatementSetter setter) {
        return new JdbcBatchItemWriterBuilder<>().dataSource(dataSource)
                .sql("insert into user(id,name,age) values(?,?,?)").itemPreparedStatementSetter(setter).build();


    }


    /**
     * 组合输出
     *
     * @param writer
     * @param jsonWriter
     * @param jdbcWriter
     * @return
     */
    @Bean
    public CompositeItemWriter<User> compositeItemWriter(ItemWriter writer, ItemWriter jsonWriter, ItemWriter jdbcWriter) {
        return new CompositeItemWriterBuilder<User>().delegates(Arrays.asList(writer, jdbcWriter, jsonWriter)).build();
    }


    @Bean
    public Step step(ItemWriter compositeItemWriter, ItemReader itemReader) {

        return stepBuilderFactory.get("writer5").chunk(1).reader(itemReader).writer(compositeItemWriter).build();
    }


    @Bean
    public Job job(Step step) {
        return jobBuilderFactory.get("writerJob").start(step).build();
    }

}
