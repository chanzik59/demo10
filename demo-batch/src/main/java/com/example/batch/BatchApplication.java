package com.example.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenzhiqin
 * @date 17/4/2023 9:58
 * @info XX
 */
@SpringBootApplication
@EnableBatchProcessing
public class BatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class,args);
    }
}
