package com.dbc.task;

import com.dbc.impl.DelayOwnOrderImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *     订单三十分钟取消任务
 */

@Component
@Configuration
@EnableScheduling
@Slf4j
public class OrderTimeTask {

                @Autowired
                DelayOwnOrderImpl delayOwnOrder;

                @Scheduled(fixedRate=1000*60*35)    // 三十五分钟执行一次线程
                public void delay (){
                    delayOwnOrder.init();
                }
}
