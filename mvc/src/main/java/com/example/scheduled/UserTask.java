package com.example.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author czq
 * @date 2023/10/26 15:00
 * @Description:
 */

@Slf4j
//@Component
public class UserTask {


    @Scheduled(cron = "0/30 * * * * ?")
    @Async("taskPool")
    public void task1() throws InterruptedException {
        log.info("周期查询-task1");
        TimeUnit.SECONDS.sleep(20);
    }


    @Scheduled(cron = "0/40 * * * * ?")
    @Async("taskPool")
    public void task2() throws InterruptedException {
        log.info("周期查询-task2");
        TimeUnit.SECONDS.sleep(30);
    }


}
