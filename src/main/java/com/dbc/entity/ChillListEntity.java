package com.dbc.entity;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "CommitteeList")
public class ChillListEntity {

                // 验证人列表
                private int id;
                private JSONArray normal;
                private JSONArray chill_list;
                private JSONArray waiting_box_pubkey;

}
