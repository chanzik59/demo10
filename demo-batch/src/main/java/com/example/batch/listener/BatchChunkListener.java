package com.example.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 18/4/2023 10:35
 * @info 块监听
 */
@Component
@Slf4j
public class BatchChunkListener implements ChunkListener {
    @Override
    public void beforeChunk(ChunkContext context) {
        log.info("块执行之前");

    }

    @Override
    public void afterChunk(ChunkContext context) {
        log.info("块执行之后");
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        log.info("块执行异常");
    }
}
