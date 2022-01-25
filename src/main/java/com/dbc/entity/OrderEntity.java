package com.dbc.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Data
public class OrderEntity implements Delayed {

                // 订单编号
                private String orderNO;
                // 过期时间
                private Date time;
                // 订单状态
                private String orderStatus;




                // 设置延时时间
                public long getDelay (TimeUnit unit) {
                    long l = unit.convert(time.getTime() - System.currentTimeMillis(),TimeUnit.MILLISECONDS);
                    return l;
                }

                public int compareTo(Delayed o) {
                    return time.compareTo(((OrderEntity) o).getTime()); // 根据取消时间来比较，如果取消时间小的，会有限被提取出来
                }
}
