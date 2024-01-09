package com.example.task;

import lombok.extern.slf4j.Slf4j;

/**
 * @author czq
 * @date 2023/10/31 17:30
 * @Description:
 */
@Slf4j
public class MyTask implements Runnable {
    @Override
    public void run() {
        log.info("任务被执行");
    }
}
