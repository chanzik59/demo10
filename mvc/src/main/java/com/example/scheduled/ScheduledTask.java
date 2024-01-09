package com.example.scheduled;

import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

/**
 * @author czq
 * @date 2023/10/30 11:38
 * @Description:
 */
public class ScheduledTask {

    volatile public ScheduledFuture<?> scheduledFuture;


    public void cancel(){
        Optional.ofNullable(scheduledFuture).ifPresent(v->v.cancel(true));
    }
}
