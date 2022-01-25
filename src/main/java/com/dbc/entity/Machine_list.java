package com.dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "Machine_list")
public class Machine_list implements Serializable {

                private int id;
                private String jsonrpc;   //json-rpc
                private String result;   // 返回结果
                private String bondingHeight;
                private String machineInfoDetail;
                private String committee_upload_info;
                private int calc_point;
                private int cpu_core_num;
                private int cpu_rate;
                private String cpu_type;
                private int cuda_core;
                private int gpu_mem;
                private int gpu_num;
                private String gpu_type;
                private int hard_disk;
                private boolean is_support;
                private String machine_id;
                private int mem_num;
                private String rand_str;
                private String staker_customize_info;
                private int download_net;
                private String images;
                private int latitude;
                private int left_change_time;
                private int longitude;
                private int upload_net;
                private String machineStash;
                private int machinePrice;
                private String machineStatus;
                private String rewardDeadline;
                private String stakeAmount;

}
