package com.example.entity;

import com.example.ant.Secure;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author czq
 * @date 2023/9/27 16:00
 * @Description:
 */

@Scope(scopeName = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
@ToString
@Secure
public class AA {
    String c = "20";




}
