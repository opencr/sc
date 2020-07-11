package org.sc.smp.mq;

import lombok.extern.slf4j.Slf4j;
import org.sc.smp.config.QueueEnum;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author liuhenghuo
 */
@Component
@Slf4j
public class CancelOrderSender {
    @Resource
    private AmqpTemplate amqpTemplate;

    public void sendMessage(String json, final Long delayTimes) {
        //给延迟队列发送消息
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_ORDER_CANCEL.getExchange()
                , QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey(), json
                , new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                if(delayTimes != null){
                    //给消息设置延迟毫秒值
                    message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                }
                return message;
            }
        });
        log.info("send orderId:{}", json);
    }

}
