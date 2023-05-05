package com.example.batch.config;

import com.example.batch.entity.Employee;
import com.example.batch.entity.User;
import com.example.batch.entity.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenzhiqin
 * @date 19/4/2023 10:43
 * @info ItemReader测试
 */
@Slf4j
@Configuration
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


    /**
     * 基于分页
     *
     * @param dataSource
     * @param userRowMapper
     * @return
     */
    @Bean
    public JdbcPagingItemReader<User> pagingReader(DataSource dataSource, RowMapper userRowMapper, PagingQueryProvider provider) {
        Map<String, Object> param = new HashMap<>();
        param.put("age", 16);
        return new JdbcPagingItemReaderBuilder<User>().name("pagingReader").dataSource(dataSource)
                .queryProvider(provider).parameterValues(param).pageSize(10).rowMapper(userRowMapper).build();
    }


    /**
     * 分页逻辑
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean
    public PagingQueryProvider pagingQueryProvider(DataSource dataSource) throws Exception {
        SqlPagingQueryProviderFactoryBean factoryBean = new SqlPagingQueryProviderFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setSelectClause("select * ");
        factoryBean.setFromClause(" from user");
        factoryBean.setWhereClause(" where age>:age");
        factoryBean.setSortKey("id");
        return factoryBean.getObject();
    }

    @Bean
    public ItemWriter<User> itemWriter() {
        return items -> items.forEach(System.out::println);
    }


    @Bean
    public ItemWriter<Employee> employeeItemWriter() {
        return items -> System.out.println(items);
    }


    @Bean
    @StepScope
    public MyBatisPagingItemReader<Employee>  mybatisReader(SqlSessionFactory sqlSessionFactory) {
        MyBatisPagingItemReader<Employee> itemReader = new MyBatisPagingItemReader<>();
        itemReader.setSqlSessionFactory(sqlSessionFactory);
        itemReader.setQueryId("com.example.batch.mapper.EmployeeMapper.getByPage");
        itemReader.setPageSize(1000);
        return itemReader;
    }



    @Bean
    public Step step(ItemWriter employeeItemWriter, ItemReader pagingReader) {
        return stepBuilderFactory.get("mybatisStep").chunk(1).reader(pagingReader).writer(employeeItemWriter).build();
    }


    @Bean
    public Step mybatisStep(ItemWriter employeeItemWriter, ItemReader mybatisReader) {
        return stepBuilderFactory.get("step6").chunk(1000).reader(mybatisReader).writer(employeeItemWriter).build();
    }




    @Bean
    public Job job(Step mybatisStep) {
        return jobBuilderFactory.get("mybatis_job").incrementer(new RunIdIncrementer()).start(mybatisStep).build();
    }


}
