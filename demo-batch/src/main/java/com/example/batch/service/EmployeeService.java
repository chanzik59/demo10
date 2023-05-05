package com.example.batch.service;

/**
 * @author chenzhiqin
 * @date 4/5/2023 16:37
 * @info XX
 */
public interface EmployeeService {

    /**
     * 初始化文件数据
     *
     * @return
     */
    String dataInit();


    /**
     * 清空临时表
     */
    void truncateTemp();
}
