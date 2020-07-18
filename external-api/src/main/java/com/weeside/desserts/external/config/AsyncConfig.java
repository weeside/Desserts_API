package com.weeside.desserts.external.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {
    public static final String EXTERNAL_API_ASYNC_THREAD_POOL_NAME = "externalAsyncThreadPool";

    @Bean(EXTERNAL_API_ASYNC_THREAD_POOL_NAME)
    public TaskExecutor externalAsyncThreadPool() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(availableProcessors);
        threadPoolTaskExecutor.setMaxPoolSize(availableProcessors * 2);

        return threadPoolTaskExecutor;
    }
}
