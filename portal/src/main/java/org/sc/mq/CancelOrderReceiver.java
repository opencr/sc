package org.sc.mq;

import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 取消订单消息的处理者
 *
 * @author liuhenghuo
 * @date 2018/9/14
 */
@Component
@Slf4j
@RabbitListener(queues = "queue.mall.order.cancel")
public class CancelOrderReceiver {

    @SneakyThrows
    @RabbitHandler
    public void handle(String json, Channel channel, Message message){

        try {
            log.info("process orderId:{}", json);
            /**
             * 确认一条消息：<br>
             * channel.basicAck(deliveryTag, false); <br>
             * deliveryTag:该消息的index <br>
             * multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息 <br>
             */

            int n = (int)(Math.random()*10);
            if(n > 5){
                log.info("return msg = " + json);
                // 消息重回消息队列
//                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false, true);
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
                Thread.sleep(5000*1);
            }else{
                log.info("ok msg = " + json);
                // 确认消费一条消息
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            //消费者处理出了问题，需要告诉队列信息消费失败
            /**
             * 拒绝确认消息:<br>
             * channel.basicNack(long deliveryTag, boolean multiple, boolean requeue) ; <br>
             * deliveryTag:该消息的index<br>
             * multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。<br>
             * requeue：被拒绝的是否重新入队列 <br>
             */
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false, true);
            log.info("return msg = " + json);
            /**
             * 拒绝一条消息：<br>
             * channel.basicReject(long deliveryTag, boolean requeue);<br>
             * deliveryTag:该消息的index<br>
             * requeue：被拒绝的是否重新入队列
             */
            //channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
