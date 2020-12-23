package org.test.task.mq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.test.task.service.MultiThreadService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author 刘恒活
 * @since 2020/12/17 16:51
 */

@Component
@Slf4j
public class MqConsumer {

    @Resource
    private MultiThreadService multiThreadService;

    /**
     * concurrency 多线程同时消费
     * @param json
     * @param message
     * @param channel
     */
    @RabbitListener(queues = "${mq.queue}", concurrency = "10")
    public void listener(String json, Message message, Channel channel) {
        log.info("{}开始处理mq消息：{}", Thread.currentThread().getName(), json);
        try {
            multiThreadService.run(json);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("{}", e);
        }
        log.info("{}结束处理mq消息：{}", Thread.currentThread().getName(), json);
    }
}
