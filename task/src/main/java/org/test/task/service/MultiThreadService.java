package org.test.task.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author 刘恒活
 * @since 2020/12/17 17:01
 */
@Slf4j
@Service

public class MultiThreadService {

    public void run(String s) throws InterruptedException {
        log.info("线程{}开始跑：{}", Thread.currentThread().getId(), s);
        Thread.sleep(5*1000);
    }
}
