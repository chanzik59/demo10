package com.example.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author czq
 * @date 2023/10/30 15:06
 * @Description:
 */

@ToString
@Data
public class TbTask implements Serializable {

    private String taskId;

    private String taskName;

    private String taskDesc;

    private String taskExp;

    private Integer taskStatus;

    private String taskClass;

    private Date updateTime;

    private Date createTime;

}
