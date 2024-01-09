package com.example.service;

import com.example.entity.TbTask;
import com.example.mapper.TbTaskMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author czq
 * @date 2023/10/26 15:33
 * @Description:
 */

@Service
public class TbTaskServiceImpl implements TbTaskService {

    @Resource
    private TbTaskMapper tbTaskMapper;

    @Override
    public List<TbTask> getAll() {
        return tbTaskMapper.selectAll();
    }

    @Override
    public TbTask getById(String taskId) {
        return tbTaskMapper.selectById(taskId);
    }

    @Override
    public int update(TbTask tbTask) {
        return tbTaskMapper.update(tbTask);
    }
}
