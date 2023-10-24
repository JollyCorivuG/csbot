package com.jhc.csbot.config;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 异步线程池配置
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/12
 */
@Configuration
@Slf4j
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
  
    @Override
    public Executor getAsyncExecutor() {
        MdcThreadPoolTaskExecutor executor = new MdcThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(200);
        executor.setKeepAliveSeconds(5 * 60);
        executor.setQueueCapacity(1000);
        // 自定义实现拒绝策略
        executor.setRejectedExecutionHandler((Runnable runnable, ThreadPoolExecutor exe) -> log.error("当前任务线程池队列已满."));
        // 或者选择已经定义好的其中一种拒绝策略
        // 丢弃任务并抛出RejectedExecutionException异常
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // 丢弃任务，但是不抛出异常
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        // 丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 由调用线程处理该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        // 线程名称前缀
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }
  
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> log.error("线程池执行任务发生未知异常.", ex);
    }
  
    /**
     * 增加日志MDC
     */
    public static class MdcThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {
  
        /**
         * Gets context for task *
         *
         * @return context for task
         */
        private Map<String, String> getContextForTask() {
            return MDC.getCopyOfContextMap();
        }
  
        /**
         * All executions will have MDC injected. {@code ThreadPoolExecutor}'s submission methods ({@code execute()} etc.)
         * all delegate to this.
         */
        @Override
        public void execute(@NonNull Runnable command) {
            super.execute(wrap(command, getContextForTask()));
        }
  
        /**
         * All executions will have MDC injected. {@code ThreadPoolExecutor}'s submission methods ({@code submit()} etc.)
         * all delegate to this.
         */
        @NonNull
        @Override
        public Future<?> submit(@NonNull Runnable task) {
            return super.submit(wrap(task, getContextForTask()));
        }
  
        /**
         * All executions will have MDC injected. {@code ThreadPoolExecutor}'s submission methods ({@code submit()} etc.)
         * all delegate to this.
         */
        @NonNull
        @Override
        public <T> Future<T> submit(@NonNull Callable<T> task) {
            return super.submit(wrap(task, getContextForTask()));
        }
  
        /**
         * Wrap callable
         * @param <T>     parameter
         * @param task    task
         * @param context context
         * @return the callable
         */
        private <T> Callable<T> wrap(final Callable<T> task, final Map<String, String> context) {
            return () -> {
                Map<String, String> previous = MDC.getCopyOfContextMap();
                if (context == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(context);
                }
                try {
                    return task.call();
                } finally {
                    if (previous == null) {
                        MDC.clear();
                    } else {
                        MDC.setContextMap(previous);
                    }
                }
            };
        }

        /**
         * Wrap runnable
         * @param runnable
         * @param context
         * @return
         */
        private Runnable wrap(final Runnable runnable, final Map<String, String> context) {
            return () -> {
                Map<String, String> previous = MDC.getCopyOfContextMap();
                if (context == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(context);
                }
                try {
                    runnable.run();
                } finally {
                    if (previous == null) {
                        MDC.clear();
                    } else {
                        MDC.setContextMap(previous);
                    }
                }
            };
        }
    }
}