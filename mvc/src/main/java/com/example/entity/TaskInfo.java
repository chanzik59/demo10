package com.example.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


@Data
@ToString
public class TaskInfo implements Serializable {

    private Integer id;

    private String cron;

    private String jobName;

    private String status;

    private Date createTime;

    private Date updateTime;

}