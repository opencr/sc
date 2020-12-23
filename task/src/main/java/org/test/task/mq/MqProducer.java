package org.test.task.mq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 刘恒活
 * @since 2020/12/17 16:54
 */
@Component
@Slf4j
public class MqProducer {
    @Value("${mq.direct}")
    private String name;
    @Value("${mq.route}")
    private String route;
    @Value("${mq.queue}")
    private String queue;
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String json) {
        this.sendMessage(json, null);
    }

    private void sendMessage(String json, final Long delayTimes) {
        log.info("MQ队列初始化信息：【{}】，【{}】，【{}】", name, queue, route);
        //给延迟队列发送消息
        rabbitTemplate.convertAndSend(name, route, json,
                new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        if (delayTimes != null) {
                            //给消息设置延迟毫秒值
                            message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                        }
                        return message;
                    }
                });
        log.info("send json:{}", json);
    }
}
