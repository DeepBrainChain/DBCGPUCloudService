package com.dbc.entity;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Information")
public class InformationEntity {

            @Id
            private String machine_id;
            private String machine_information;

}
