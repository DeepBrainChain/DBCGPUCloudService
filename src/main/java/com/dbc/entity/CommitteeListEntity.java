package com.dbc.entity;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "getMachineCommitteeList")
public class CommitteeListEntity {

                @Id
                private String machineId;
                private int book_time;
                private JSONArray booked_committee;
                private int confirm_start_time;
                private JSONArray confirmed_committee;
                private JSONArray hashed_committee;
                private JSONArray onlined_committee;
                private String status;


}
