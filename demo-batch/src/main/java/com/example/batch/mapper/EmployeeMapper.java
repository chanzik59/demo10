package com.example.batch.mapper;

import com.example.batch.entity.Employee;

import java.util.List;
import java.util.Map;

/**
 * @author chenzhiqin
 * @date 4/5/2023 17:10
 * @info XX
 */
public interface EmployeeMapper {
    /**
     * 新增
     *
     * @param employee
     * @return
     */
    int save(Employee employee);

    /**
     * 新增临时
     *
     * @param employee
     * @return
     */
    int saveTemp(Employee employee);


    /**
     * 清空表
     */
    void truncateAll();


    /**
     * 清空表
     */
    void truncateTempAll();


    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    List<Employee> getTempByPage(Map<String, Object> params);


    /**
     * 测试
     *
     * @return
     */
    List<Employee> getByPage();
}
