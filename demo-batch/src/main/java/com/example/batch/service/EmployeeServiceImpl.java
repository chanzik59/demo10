package com.example.batch.service;

import com.example.batch.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;

/**
 * @author chenzhiqin
 * @date 4/5/2023 16:38
 * @info XX
 */
@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Value("${batch.save.path}")
    private String savePath;

    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public String dataInit() {
        File file = new File(savePath, "employee.csv");
        if (file.exists()) {
            log.info("文件已存在，删除文件{}", file.getAbsolutePath());
            file.delete();
        }
        Random random = new Random();
        long beginTime = System.currentTimeMillis();
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            for (int i = 0; i < 500000; i++) {
                String row = String.format("%d,小张%d,%d,%d", i, i, random.nextInt(100), random.nextBoolean() ? 1 : 0);
                writer.write(row);
                writer.newLine();
            }
        } catch (Exception e) {
            log.info("Exception", e);
            return "fail";

        }
        log.info("总耗时：{}毫秒", System.currentTimeMillis() - beginTime);
        return "success";
    }

    @Override
    public void truncateTemp() {
        employeeMapper.truncateTempAll();
    }
}
