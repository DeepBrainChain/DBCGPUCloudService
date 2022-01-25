package com.dbc.impl;

import com.alibaba.fastjson.JSONArray;
import com.dbc.dao.CommitteeListDao;
import com.dbc.entity.CommitteeListEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class CommitteeListDaoImpl implements CommitteeListDao {


                @Autowired
                MongoTemplate mongoTemplate;

                @Override
                public void SaveCommit(CommitteeListEntity machineCommitteeList) {

                     mongoTemplate.save(machineCommitteeList,"getMachineCommitteeList");

                }


                @Override
                public JSONArray getComm(String machineId) {

                        Query query = Query.query(Criteria.where("machineId").is(machineId));
                        CommitteeListEntity machineCommitteeLists = mongoTemplate.findOne(query, CommitteeListEntity.class);

                        return  machineCommitteeLists.getBooked_committee();
                    }

                @Override
                public CommitteeListEntity getTime(String machine_id) {

                    Query query =Query.query(Criteria.where("machineId").is(machine_id));
                    CommitteeListEntity committeeListEntity;
                    committeeListEntity = mongoTemplate.findOne(query,CommitteeListEntity.class);

                    return committeeListEntity;
                }
}
