package com.dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "BlockHashInfo")
public class GetBlockHashEntity {

                @Id
                private int id;    // 主键
                private Integer number;      // 快高用于计算验证时间 30秒更新
}
