package com.example.redis.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenzhiqin
 * @date 25/5/2023 14:49
 * @info XX
 */
@Slf4j
@ControllerAdvice
public class WebAdvice {


    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String handler(Exception exception) {
        log.info("Exception", exception);

        return "fail";
    }



}
