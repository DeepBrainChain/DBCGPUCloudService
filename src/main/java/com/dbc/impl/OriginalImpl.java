package com.dbc.impl;

import com.dbc.dao.OriginalDao;
import com.dbc.entity.Original;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OriginalImpl implements OriginalDao {


                @Autowired
                private MongoTemplate mongoTemplate;


                @Override
                public Original getOriginal(String machine_id) {

                    Query query = Query.query(Criteria.where("machine_id").is(machine_id));
                    Original original = mongoTemplate.findOne(query,Original.class);

                    return original;
                }

                @Override
                public void SaveOrigin(Original original) {
                    
                    mongoTemplate.save(original);

                }
}
