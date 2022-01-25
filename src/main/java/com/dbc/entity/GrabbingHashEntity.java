package com.dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "GrabbingHashInfo")
public class GrabbingHashEntity {

                @Id
                private String only_key;
                private String report_id;       // 订单号
                private String machine_id;      //机器ID
                private String reporter_rand_str;       // 报告人随机数
                private String committee_rand_str;      // 验证人自己的随机号
                private String err_reason;             // 报告内容
                private int support_report;         // 机器是否有问题
}
