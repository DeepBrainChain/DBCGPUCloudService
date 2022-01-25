package com.dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@Document(collection = "Original")
public class Original {

//                @Id
//                private String machine_id;  // 机器ID
//                private String gpu_type;    // GPU 型号
//                private String gpu_num;     // 机器内核数
//                private String cuda_core;
//                private String gpu_mem;   // GPU 内存
//                private String calc_point; //显卡的总算力点数
//                private String sys_disk;  // 系统磁盘
//                private String data_disk;   // 数据U盘
//                private String cpu_type;    // CPU 型号
//                private String cpu_core_num;  // CPU 核心数
//                private String cpu_rate;    // CPU 使用率
//                private String mem_num;     // 内存

    private ArrayList cpu;     // 机器内核数
    private String cpu_usage;
    private ArrayList disk;   // GPU 内存
    private String ip; //显卡的总算力点数
    private String mem;  // 系统磁盘
    private String mem_usage;   // 数据U盘
    private String os;    // CPU 型号
    private String state;  // CPU 核心数
    private String version;    // CPU 使用率
    private ArrayList wallet;     // 内存

}
