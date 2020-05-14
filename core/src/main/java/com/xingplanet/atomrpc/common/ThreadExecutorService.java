package com.xingplanet.atomrpc.common;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 * @author wangjin
 * @date 2019-02-01
 */
@Configuration
public class ThreadExecutorService implements DisposableBean {

    // todo 线程超时监控


    private ExecutorService executorService;
    @Bean("executorService")
    public ExecutorService createExecutorServiceBean() {
        ThreadFactory threadFactory =new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").build();
        executorService = new ThreadPoolExecutor(10,20,1, TimeUnit.SECONDS,new ArrayBlockingQueue<>(100), threadFactory);
        return executorService;

    }

    @Override
    public void destroy() throws Exception {
        executorService.shutdownNow();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }
}
