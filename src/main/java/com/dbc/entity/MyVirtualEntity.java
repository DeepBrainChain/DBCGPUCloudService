package com.dbc.entity;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 *      我的GPU虚拟机
 */

@Data
@Document(collection = "MyVirtualInfo")
public class MyVirtualEntity {

                    @Id
                    private String id;
                    private String machine_id; // 机器ID
                    private String gpu_type;        // GPU类型
                    private Integer gpu_num;         // Gpu 个数
                    private String gpu_mem;         // gpu 显存
                    private String sys_disk;        // 系统磁盘
                    private String data_disk;       // 数据盘
                    private String cpu_type;     // cpu 类型
                    private String cpu_core_num;        // cpu 核数
                    private String cpu_rate;        // cpu 使用率
                    private String mem_num;     // 内存书
                    private String upload_net;      // 上行带宽
                    private String download_net;       // 下行带宽
                    private JSONArray telecom_operators;        // 电信运营商
                    private String country;         //国家
                    private String city;         // 城市
                    private String dollar;      // 美元/日
                    private int day;        // 租用天数
                    private String count;       //总计美元
                    private double dbc;         // 等值dbc数量
                    private String wallet;      // 用户钱包地址
                    private double calc_point;  // 算力值
                    private String gpu_price;  // 租用时GPU价格
                    private double dbc_price; // 支付时dbc价格

                    //                private Date time;      // 过期时间
                    private String orderStatus;     // 订单状态  正在使用中|未支付|订单结束|订单中断|订单取消

                    // 过期时间
                    private Date time;




                    // 设置延时时间
                    public long getDelay (TimeUnit unit) {
                        long l = unit.convert(time.getTime() - System.currentTimeMillis(),TimeUnit.MILLISECONDS);
                        return l;
                    }

                    public int compareTo(Delayed o) {
                        return time.compareTo(((OrderEntity) o).getTime()); // 根据取消时间来比较，如果取消时间小的，会有限被提取出来
                    }
}
