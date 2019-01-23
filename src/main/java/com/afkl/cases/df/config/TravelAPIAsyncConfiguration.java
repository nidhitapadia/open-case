package com.afkl.cases.df.config;

import com.afkl.cases.df.api.exception.TravelAPIAsyncExceptionHandler;
import com.afkl.cases.df.config.data.TravelAPIAsyncData;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class TravelAPIAsyncConfiguration implements AsyncConfigurer {

    @Autowired
    private TravelAPIAsyncData travelAPIAsyncData;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(travelAPIAsyncData.getCorePoolSize());
        executor.setMaxPoolSize(travelAPIAsyncData.getMaxPoolSize());
        executor.setQueueCapacity(travelAPIAsyncData.getQueueCapacity());
        executor.setThreadNamePrefix(travelAPIAsyncData.getThreadNamePrefix());
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new TravelAPIAsyncExceptionHandler();
    }
}
