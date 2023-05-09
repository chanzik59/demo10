package com.example.batch.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author chenzhiqin
 * @date 9/5/2023 11:57
 * @info 访问入口
 */
@Slf4j
@Controller
public class BatchController {


    @Resource
    public JobOperator jobOperator;


    @ResponseBody
    @RequestMapping("batch/{batchName}")
    public String batch(@PathVariable("batchName") String batchName, HttpServletRequest request) throws Exception {

        Map<String, String[]> parameterMap = request.getParameterMap();
        String runId="run.id="+System.currentTimeMillis();
        String params = parameterMap.entrySet().stream().map(v -> v.getKey() + "=" + v.getValue()[0]).reduce(runId, (a, b) -> a +","+ b);
        Long jobId = jobOperator.start(batchName, params);
        String message = String.format("作业%d已被执行!", jobId);
        log.info(message);
        return message;

    }


}
