package com.dbc.entity;

import lombok.Data;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 *  订单下单后三十分钟内未支付则自动取消封装方法
 */
@Data
public class ItemDelayed<T> implements Delayed  {

                // 延迟时间 30分钟
                private final static long DELAY = 30 * 60 * 1000L;
                private String dataId; // 数据ID
                private long startTime; // 开始时间
                private long expire;
                private Date now; // 创建时间
                private T data; // 泛型 data


                public ItemDelayed(String dataId, long startTime, long secondsDelay) {
                    super();
                    this.dataId = dataId;
                    this.startTime = startTime;
                    this.expire = startTime + (secondsDelay * 1000);
                    this.now = new Date();
                }

                public ItemDelayed(String dataId, long startTime) {
                    super();
                    this.dataId = dataId;
                    this.startTime = startTime;
                    this.expire = startTime + DELAY;
                    this.now = new Date();
                }

                @Override
                public int compareTo(Delayed o) {
                    return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
                }

                @Override
                public long getDelay(TimeUnit unit) {
                    return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                }

}
