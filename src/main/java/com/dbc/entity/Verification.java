package com.dbc.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "Machine_list")
public class Verification {

            private int id;
            private String result;
            private List<String> bonding_machine;
            private List<String> booked_machine;
            private List<String> fulfilling_machine;
            private List<String> confirmed_machine;
            private List<String> refused_machine;
            private List<String> online_machine;
            private List<String> rented_machine;  // 被租用机器
}
