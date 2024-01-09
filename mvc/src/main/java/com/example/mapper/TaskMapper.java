package com.example.mapper;

import com.example.entity.TaskInfo;
import org.apache.ibatis.annotations.Select;

/**
 * @author czq
 * @date 2023/10/26 15:37
 * @Description:
 */

public interface TaskMapper {

    @Select("select * from sc_task_info where  job_name=#{jobName}")
    TaskInfo get(String jobName);


}
