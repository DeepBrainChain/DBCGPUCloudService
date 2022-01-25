package com.dbc.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Data
@Document(collection = "MachineDetailsInfo")
public class MachineDetailsEntity {

                /**
                 * 机器详情信息
                 */

                @Id
                private String machine_id; // 机器ID
                private String controller;  // 控制钱包
                private String machine_stash;   // 机器所有者
                private String machine_renter;  // 机器出租人
                private String bonding_height;  // 上线时间
                private String stake_amount;    // 持有金额
                private String machine_status;   // 机器状态
                private String total_rented_duration;  // 总租用时间
                private String total_rented_times;    // 总租用次数
                private String total_rent_fee;     //总租金
                private String total_burn_fee;
                private List<String> machine_info_detail;  //机器信息详情
                private JSONArray reward_committee;     // 奖励委员会
                private String reward_deadline;     // 奖励截至日期
                private String gpu_type;
                private Integer gpu_num;         // Gpu 个数
                private String cuda_core;
                private String gpu_mem;         // 内存
                private double calc_point;      // 算例
                private String sys_disk;        // 系统磁盘
                private String data_disk;       // 数据盘
                private String cpu_type;     // cpu 类型
                private String cpu_core_num;        // cpu 核数
                private String cpu_rate;        // cpu 使用率
                private String mem_num;
                private String rand_str;
                private String is_support;
                private String upload_net;      // 上行带宽
                private String download_net;       // 下行带宽
//                private Integer longitude;       // 经度
//                private Integer latitude;        // 维度
                private JSONArray telecom_operators;        // 电信运营商
                private JSONArray images;       // 镜像
                private String country;         //国家
                private String city;         // 城市
                private String operator;    // 算工名称
                private String server_room; // 机房编号
                private double East;    // 东经
                private double West;    // 西经
                private double South;   // 南纬
                private double North;   // 北纬

//                private Date time;      // 过期时间
                private String orderStatus;     // 订单状态


                // 设置延时时间
//                public long getDelay (TimeUnit unit) {
//                    long l = unit.convert(time.getTime() - System.currentTimeMillis(),TimeUnit.MILLISECONDS);
//                    return l;
//                }
//
//                public int compareTo(Delayed o) {
//                    return time.compareTo(((OrderEntity) o).getTime()); // 根据取消时间来比较，如果取消时间小的，会有限被提取出来
//                }


}
