package com.dbc.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.db.sql.Order;
import com.dbc.dao.DelayOrder;
import com.dbc.dao.MyVirtualDao;
import com.dbc.entity.ItemDelayed;
import com.dbc.entity.MyVirtualEntity;
import com.dbc.entity.OrderEntity;
import com.dbc.service.committee.MyVirtualService;
import com.dbc.test.DelayedTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;


@Slf4j
@Lazy(false)
@Component
public class DelayOwnOrderImpl implements DelayOrder<MyVirtualEntity> {


            @Autowired
            MyVirtualService myVirtualService;

            @Autowired
            MyVirtualDao myVirtualDao;

//            @Autowired
//            ExecutorService delayOrderExecutor;


            private final static DelayQueue<ItemDelayed<MyVirtualEntity>> DELAY_QUEUE = new DelayQueue<>();

            /**
             * 初始化时加载数据库中需处理超时的订单，系统启动后扫描数据库中未支付,未过期的的订单
             *
             */
            @PostConstruct
            public void init() {
                log.info("系统启动:扫描数据库中未支付,未过期的的订单");

                List<MyVirtualEntity> orderList = myVirtualDao.getOrder();
                for (MyVirtualEntity order : orderList) {
                    ItemDelayed<MyVirtualEntity> orderDelayed = new ItemDelayed<MyVirtualEntity>(order.getId(),order.getTime().getTime());
                    this.addToOrder(orderDelayed);
                }
                log.info("系统启动:扫描数据库中未支付的订单,总共扫描了" + orderList.size() + "个订单,推入检查队列,准备到期检查...");

                /*启动一个线程，去取延迟订单*/
                new Thread(() -> {
                    log.info("启动处理的订单线程:" + Thread.currentThread().getName());
                    ItemDelayed<MyVirtualEntity> orderDelayed;
                    while (true) {
                        try {
                            orderDelayed = DELAY_QUEUE.take();
                            //处理超时订单
//                            orderService.updateCloseOverTimeOrder(orderDelayed.getDataId());
                            MyVirtualEntity myVirtualEntity = new MyVirtualEntity();
                            myVirtualEntity.setId(orderDelayed.getDataId());
                            myVirtualEntity.setOrderStatus("订单取消");
//                        redisUtil.ins("order_"+myVirtualEntity.getMachine_id(),machine_id,30, TimeUnit.MINUTES);
                            myVirtualDao.update(myVirtualEntity);
                        } catch (Exception e) {
                            log.error("执行自营超时订单的_延迟队列_异常:" + e);
                        }
                    }
                }).start();
            }



            @Override
            public boolean addToOrder(ItemDelayed<MyVirtualEntity> itemDelayed) {
                return DELAY_QUEUE.add(itemDelayed);
            }

            @Override
            public boolean DelayQueue(MyVirtualEntity data) {
                ItemDelayed<MyVirtualEntity> orderDelayed = new ItemDelayed<>(data.getMachine_id(), data.getTime().getTime());
                return DELAY_QUEUE.add(orderDelayed);

            }

            @Override
            public void removeToOrder(MyVirtualEntity data) {
                if (data == null) {
                    return;
                }
                for (Iterator<ItemDelayed<MyVirtualEntity>> iterator = DELAY_QUEUE.iterator(); iterator.hasNext(); ) {
                    ItemDelayed<MyVirtualEntity> queue = iterator.next();
                    if (queue.getDataId().equals(data.getMachine_id())) {
                        DELAY_QUEUE.remove(queue);
                    }
                }

            }
}
