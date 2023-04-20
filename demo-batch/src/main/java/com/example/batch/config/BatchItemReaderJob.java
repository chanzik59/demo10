package com.example.batch.config;

import com.example.batch.entity.User;
import com.example.batch.entity.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author chenzhiqin
 * @date 19/4/2023 10:43
 * @info ItemReader测试
 */
@Component
@Slf4j
public class BatchItemReaderJob {

    @Resource
    private JobBuilderFactory jobBuilderFactory;


    @Resource
    private StepBuilderFactory stepBuilderFactory;

    /**
     * 普通文件映射
     *
     * @return
     */
    @Bean
    public FlatFileItemReader<User> itemReader() {
        return new FlatFileItemReaderBuilder<User>().name("flatReader").resource(new ClassPathResource("testFile/flat.txt"))
                .delimited().delimiter("#").names("id", "name", "age").targetType(User.class).build();
    }

    /**
     * 不规则文件映射
     *
     * @param userMapper
     * @return
     */
    @Bean
    public FlatFileItemReader<User> mapperReader(UserMapper userMapper) {
        return new FlatFileItemReaderBuilder<User>().name("flatReader").resource(new ClassPathResource("testFile/fieldMapper.txt"))
                .delimited().delimiter("#").names("id", "name", "age", "province", "city", "street").fieldSetMapper(userMapper).build();
    }


    /**
     * json 文件映射
     *
     * @return
     */
    @Bean
    public JsonItemReader<User> jsonReader() {
        JacksonJsonObjectReader<User> jsonObjectReader = new JacksonJsonObjectReader<>(User.class);
        jsonObjectReader.setMapper(new ObjectMapper());
        return new JsonItemReaderBuilder<User>().name("jsonReader").jsonObjectReader(jsonObjectReader).resource(new ClassPathResource("testFile/fileJson.json")).build();
    }


    /**
     * 基于游标读取表数据
     *
     * @param dataSource
     * @param userRowMapper
     * @return
     */
    @Bean
    public JdbcCursorItemReader<User> cursorItemReader(DataSource dataSource, RowMapper userRowMapper) {
        return new JdbcCursorItemReaderBuilder<User>().name("cursorReader").dataSource(dataSource).sql("select * from user where age>?").preparedStatementSetter(new ArgumentPreparedStatementSetter(new Object[]{"17"})).rowMapper(userRowMapper).build();
    }

    @Bean
    public ItemWriter<User> itemWriter() {
        return items -> items.forEach(System.out::println);
    }


    @Bean
    public Step step(ItemWriter itemWriter, ItemReader cursorItemReader) {
        return stepBuilderFactory.get("step5").chunk(1).reader(cursorItemReader).writer(itemWriter).build();
    }


    @Bean
    public Job job(Step step) {
        return jobBuilderFactory.get("stopJobItemReader1").start(step).build();
    }


}
