package com.dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@Document(collection = "ResultHashInfo")
public class ResultHashEntity {

                @Id
                private String only_key;
                private String machine_id;  // 机器ID
                private String gpu_type;    // GPU 型号
                private String gpu_num;     // 机器内核数
                private String cuda_core;
                private String gpu_mem;   // GPU 内存
                private String calc_point; //显卡的总算力点数
                private String sys_disk;  // 系统磁盘
                private String data_disk;   // 数据U盘
                private String cpu_type;    // CPU 型号
                private String cpu_core_num;  // CPU 核心数
                private String cpu_rate;    // CPU 使用率
                private String mem_num;     // 内存
                private String rand_str; // 随机数
                private int is_support;     // 是否通过


}
