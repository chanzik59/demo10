package com.example.batch.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenzhiqin
 * @date 18/4/2023 16:23
 * @info XX
 */
//@Controller
@Slf4j
public class BatchController {
    @Autowired
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;


    @Autowired
    private JobExplorer jobExplorer;

    @RequestMapping("batch")
    @ResponseBody
    public ExitStatus batch() throws Exception {
        log.info("被访问。。。");
        JobParameters jobParameters = new JobParametersBuilder(jobExplorer).getNextJobParameters(job).addString("name", "小刚").toJobParameters();
        return jobLauncher.run(job, jobParameters).getExitStatus();
    }
}
