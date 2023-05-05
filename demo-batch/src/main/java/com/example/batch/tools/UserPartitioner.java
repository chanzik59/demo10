package com.example.batch.tools;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenzhiqin
 * @date 4/5/2023 11:07
 * @info 分区器
 */
@Component
public class UserPartitioner implements Partitioner {
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> result = new HashMap<>(gridSize);
        //文件间隔
        int range = 10;
        //开始位置
        int start = 1;
        //结束位置
        int end = 10;
        String text = "testFile/user%s-%s.txt";
        for (int i = 0; i < gridSize; i++) {
            ExecutionContext value = new ExecutionContext();
            Resource resource = new ClassPathResource(String.format(text, start, end));
            try {
                value.putString("file", resource.getURL().toExternalForm());
            } catch (Exception e) {
                e.printStackTrace();
            }
            start += range;
            end += range;
            result.put("user_partition_" + i, value);
        }
        return result;
    }
}
