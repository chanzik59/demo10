package com.example.redis.config;

import com.example.redis.advice.WebInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author chenzhiqin
 * @date 25/5/2023 15:33
 * @info
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(new WebInterceptor());
    }


    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(0);

        for (int i = 0; i < 4; i++) {

        new Thread(()->{

            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName()+": begin");
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+": end");
            } catch (InterruptedException e) {

                e.printStackTrace();
            }finally {
                semaphore.release();
            }


        }).start();

        }
        
    }
}
