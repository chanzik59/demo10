package com.example.batch.controller;

import com.example.batch.service.EmployeeService;
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

import javax.annotation.Resource;

/**
 * @author chenzhiqin
 * @date 18/4/2023 16:23
 * @info  控制层
 */
@Controller
@Slf4j
public class BatchController {

    @Resource
    private Job job;

    @Autowired
    private Job saveDbJob;

    @Resource
    private Job employeeJob;

    @Autowired
    private JobLauncher jobLauncher;


    @Autowired
    private JobExplorer jobExplorer;

    @Resource
    private EmployeeService employeeService;


    @RequestMapping("batch")
    @ResponseBody
    public ExitStatus batch() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder(jobExplorer).getNextJobParameters(job).addString("name", "小刚").toJobParameters();
        return jobLauncher.run(job, jobParameters).getExitStatus();
    }


    @RequestMapping("dataInit")
    @ResponseBody
    public String dataInit() {
        return employeeService.dataInit();
    }


    @RequestMapping("csvToDb")
    @ResponseBody
    public ExitStatus toDb() throws Exception {
        employeeService.truncateTemp();
        JobParameters jobParameters = new JobParametersBuilder(jobExplorer).getNextJobParameters(saveDbJob).addLong("time", System.currentTimeMillis()).toJobParameters();
        return jobLauncher.run(saveDbJob, jobParameters).getExitStatus();
    }


    @RequestMapping("dbToDb")
    @ResponseBody
    public ExitStatus dbToDb() throws Exception {
        employeeService.truncate();
        JobParameters jobParameters = new JobParametersBuilder(jobExplorer).getNextJobParameters(employeeJob).addLong("time", System.currentTimeMillis()).toJobParameters();
        return jobLauncher.run(employeeJob, jobParameters).getExitStatus();
    }
}
