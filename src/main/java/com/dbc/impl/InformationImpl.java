package com.dbc.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.InformationDao;
import com.dbc.dao.VerificationDao;
import com.dbc.entity.InformationEntity;
import com.dbc.entity.LCMachineEntity;
import com.dbc.entity.Verification;
import com.dbc.service.DBService.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InformationImpl implements InformationDao {


            @Autowired
            MongoTemplate mongoTemplate;

            @Autowired
            VerificationDao verificationDao;

            @Autowired
            HttpClient client;

            @Override
            public void InsertInformation() {

                Verification verification = verificationDao.getVer(1);
                JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(verification));
                if (data!=null) {
                	JSONArray b = JSON.parseArray(data.getString("booked_machine"));
                    JSONArray bo = JSON.parseArray(b.toJSONString());

                    InformationEntity entity = new InformationEntity();
                    if(bo != null && bo.size() > 0) {
                        for (int i = 0; i < bo.size(); i++) {
                            String machine_id = bo.get(i).toString();
                            String original = client.SendPost(machine_id);
                            entity.setMachine_id(machine_id);
                            entity.setMachine_information(original);
                            mongoTemplate.save(entity);
                        }

                    }
				}
                
            }

            @Override
            public InformationEntity getInfo(String machine_id) {

                Query query = Query.query(Criteria.where("machine_id").is(machine_id));;
                InformationEntity lcMachineEntity = mongoTemplate.findOne(query,InformationEntity.class);

                return lcMachineEntity;
            }
}
