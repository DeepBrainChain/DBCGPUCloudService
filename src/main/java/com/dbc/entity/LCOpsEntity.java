package com.dbc.entity;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import org.springframework.core.serializer.Serializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 *     验证人验证的时间， 邮箱通知
 *     16点25分
 */
@Data
@Document(collection = "LCCommitteeOps")
public class LCOpsEntity implements Serializable {

            @Id
            private String id;         // 主键 钱包和机器的和 唯一的键
            private String mid;     // 地址
            private String booked_committee;     // 绑定机器ID
            private JSONArray verify_time;      // 验证时间
            private JSONArray verify_time_high;     // 快高
//            private String hash_time;
//            private String confirm_time;
//            private String machine_status;      // 机器状态
//            //  机器信息
//            private String machine_id;      // 机器ID
//            private String gpu_type;        //GPU 类型
//            private String gpu_num;
//            private String cuda_core;
//            private String gpu_mem;
//            private String calc_point;
//            private String sys_disk;
//            private String data_disk;
//            private String cpu_type;
//            private String cpu_core_num;
//            private String cpu_rate;
//            private String mem_num;
//            private String rand_str;
//            private String is_support;
            private int emil_state;      // 邮件发送状态   未发送  已发送

}
