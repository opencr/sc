package org.sc.smp.config;

import lombok.Getter;

/**
 * @author liuhenghuo
 */

@Getter
public enum QueueEnum {

    /**
            * 消息通知队列
     */
    QUEUE_ORDER_CANCEL("mall.order.direct", "queue.mall.order.cancel", "route.mall.order.cancel");
    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 路由键
     */
    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }
}
