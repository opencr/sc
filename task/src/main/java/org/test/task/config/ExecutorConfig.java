package org.test.task.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Async("behaviorAnalysisExecutor")
 *
 * @author 刘恒活
 * @since 2020/11/25 11:12
 */
@Configuration
public class ExecutorConfig {
    private int behaviorAnalysisMaxPoolSize = 10;
    private int behaviorAnalysisCorePoolSize = 10;
    private int behaviorAnalysisQueueCapacity =10;

    @Bean("executor")
    public Executor behaviorAnalysisExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("behavior-analysis-schedule-");
        //最大线程数5：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(behaviorAnalysisMaxPoolSize);
        //核心线程数5：线程池创建时候初始化的线程数
        executor.setCorePoolSize(behaviorAnalysisCorePoolSize);
        //缓冲队列500：用来缓冲执行任务的队列
        executor.setQueueCapacity(behaviorAnalysisQueueCapacity);
        // 当提交的任务个数大于QueueCapacity，就需要设置该参数，但spring提供的都不太满足业务场景，可以自定义一个，
        // 也可以注意不要超过QueueCapacity即可
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        executor.initialize();
        return executor;
    }
}
