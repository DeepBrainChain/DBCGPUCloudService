package com.dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

//  创建虚拟机Id
@Data
@Document(collection = "Task_Id")
public class TaskIdEntity {

                @Id
                private String machine_id;
                private String task_id;     // 虚拟机ID
                private Date create_time;   // 创建时间
                private String status;      // 状态
}

