package org.test.task.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 消息队列配置
 *
 * @author 刘恒活
 * @since 2020/7/10 16:18
 */

@Configuration
@Slf4j
public class RabbitMqConfig {

    @Resource
    private RabbitAdmin rabbitAdmin;
    @Value("${mq.direct}")
    private String name;
    @Value("${mq.queue}")
    private String queue;
    @Value("${mq.route}")
    private String route;

    /**
     * 消息实际消费队列所绑定的交换机
     */
    @Bean
    DirectExchange direct() {
        return (DirectExchange) ExchangeBuilder.directExchange(name).durable(true).build();
    }

    /**
     * 考生信息更新消费队列
     */
    @Bean
    public Queue candidateUpdateQueue() {
        return new Queue(queue);
    }

    /**
     * 将生信息更新队列绑定到交换机
     */
    @Bean
    Binding candidateUpdateBinding() {
        return BindingBuilder.bind(candidateUpdateQueue()).to(direct()).with(route);
    }

    /**
     * 创建初始化RabbitAdmin对象
     * AutoStartup只有设置为 true，spring 才会加载 RabbitAdmin 这个类
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    /**
     * 创建交换机和对列
     */
    @Bean
    void createExchangeQueue() {
        log.info("MQ队列初始化信息：【{}】，【{}】，【{}】", name, queue, route);
        rabbitAdmin.declareExchange(direct());
        rabbitAdmin.declareQueue(candidateUpdateQueue());
    }

}
