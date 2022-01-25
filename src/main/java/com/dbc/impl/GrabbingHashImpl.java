package com.dbc.impl;

import com.dbc.dao.GrabbingHashDao;
import com.dbc.entity.GrabbingHashEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class GrabbingHashImpl implements GrabbingHashDao {

                @Autowired
                MongoTemplate mongoTemplate;

                @Override
                public void SaveGrabbing(GrabbingHashEntity grabbingHashEntity) {

                            mongoTemplate.save(grabbingHashEntity);
                }

                @Override
                public GrabbingHashEntity GetGraHash(String only_key) {

                    Query query = Query.query(Criteria.where("_id").is(only_key));
                    GrabbingHashEntity grabbingHashEntity = mongoTemplate.findOne(query,GrabbingHashEntity.class);
                    return grabbingHashEntity;
                }
}
