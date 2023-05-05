package com.example.batch.tools;

import com.example.batch.config.BatchDbJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenzhiqin
 * @date 5/5/2023 15:12
 * @info 员工信息分区器
 */
@Slf4j
@Component
public class EmployeePartitioner implements Partitioner {
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> contextHashMap = new HashMap<>(gridSize);
        int form = 1, to = BatchDbJob.range, range = BatchDbJob.range;

        for (int i = 0; i < gridSize; i++) {
            ExecutionContext context = new ExecutionContext();
            context.putInt("from", form);
            context.putInt("to", to);
            context.put("range", range);
            form += range;
            to += range;
            contextHashMap.put("partition_" + i, context);
        }
        return contextHashMap;
    }
}
