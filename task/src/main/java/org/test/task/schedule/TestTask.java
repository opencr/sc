package org.test.task.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.test.task.mq.MqProducer;

import javax.annotation.Resource;

/**
 * @author 刘恒活
 * @since 2020/12/17 17:07
 */
@Component
//@EnableScheduling
@Slf4j
public class TestTask {

    @Resource
    private MqProducer mqProducer;
    private static final int max = 100;
    private static int count = 0;

    //@Scheduled(fixedDelay = 5000)
    public void test(){
        if(count < max){
            int n = 0;
            boolean stop = true;
            while (stop) {
                n++;
                count++;
                mqProducer.sendMessage("我是第" + count + "条消息");
                if(n > 30){
                    stop=false;
                }
            }
        }else{
            try {
                Thread.sleep(60*60*1000);
            } catch (InterruptedException e) {
                log.error("{}", e);
            }
            log.debug("队列已经满了");
        }
    }
}
