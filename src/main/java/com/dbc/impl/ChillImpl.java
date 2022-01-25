package com.dbc.impl;

import com.dbc.dao.ChillDao;
import com.dbc.entity.ChillListEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class ChillImpl implements ChillDao {

                @Autowired
                MongoTemplate mongoTemplate;

                @Override
                public void insertChill(ChillListEntity chillListEntity) { mongoTemplate.save(chillListEntity); }

                @Override
                public ChillListEntity getChilInfo(int id) {

                    Query query = Query.query(Criteria.where("id").is(id));
                    ChillListEntity chillListEntity = mongoTemplate.findOne(query,ChillListEntity.class);

                    return chillListEntity;
                }
}
