package com.dbc.entity;

import com.alibaba.fastjson.JSONArray;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "LCCommitteeMachineList")
public class LCMachineEntity {

            //            @Id
            @Id
            private String wallet; //验证委员会钱包
            private JSONArray booked_machine;
            private JSONArray hashed_machine;
            private JSONArray confirmed_machine;
            private JSONArray online_machine;

//            SendMail.send_common(email,"service@dbchain.ai", "DBC service", "验证机器","当前有机器需要验证","dbc service","https://www.deepbrainchain.org");
    
}
