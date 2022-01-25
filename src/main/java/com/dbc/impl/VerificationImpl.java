package com.dbc.impl;

import com.dbc.dao.VerificationDao;
import com.dbc.entity.Original;
import com.dbc.entity.Verification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VerificationImpl implements VerificationDao {

                @Autowired
                MongoTemplate mongoTemplate;


                @Override
                public void addVerification(Verification verification) {

                       new DefaultMongoTypeMapper(null);

                       mongoTemplate.save(verification);

                }

                @Override
                public Verification getVer(int id) {
                    Query query = Query.query(Criteria.where("id").is(id));
                    Verification verification = mongoTemplate.findOne(query,Verification.class);

                    return verification;
                }
}
