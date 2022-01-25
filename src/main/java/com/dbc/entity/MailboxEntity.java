package com.dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *     验证人邮箱实体
 */
@Data
@Document(collection = "CommitteeMailbox")
public class MailboxEntity {

            @Id
            private String wallet;      // 钱包地址
            private String emil;        // 邮箱
}
