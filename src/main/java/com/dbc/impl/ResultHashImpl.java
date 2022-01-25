package com.dbc.impl;

import com.dbc.dao.ResultHashDao;
import com.dbc.entity.ResultHashEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class ResultHashImpl implements ResultHashDao {

                @Autowired
                MongoTemplate mongoTemplate;

                @Override
                public void Saveresult(ResultHashEntity resultHashEntity,String wallet,String signature,String machine_id) {

                    mongoTemplate.save(resultHashEntity);

                }

                @Override
                public ResultHashEntity GetRHash(String only_key) {

                    Query query = Query.query(Criteria.where("only_key").is(only_key));
                    ResultHashEntity resultHashEntity = mongoTemplate.findOne(query,ResultHashEntity.class);
                    return resultHashEntity;
                }
}
