package com.dbc.dao;

import com.dbc.entity.ItemDelayed;

/**
 *      通用订单接口
 */
public interface DelayOrder<T> {

            // 添加延迟对象到延迟队列
            boolean addToOrder(ItemDelayed<T> itemDelayed);
            //根据对象添加到指定延时队列
            boolean DelayQueue (T data);

            void removeToOrder (T data);
}
