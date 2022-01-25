package com.dbc.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class DemoDaoImpl implements DemoDao {

            @Autowired
            private MongoTemplate mongoTemplate;

            @Override
            public void saveDemo(DemoEntity demoEntity) {

                mongoTemplate.save(demoEntity);

            }

            @Override
            public DemoEntity findDemo(Long id) {
                Query query =new Query(Criteria.where("id").is(id));
                DemoEntity demoEntity =mongoTemplate.findOne(query,DemoEntity.class);
                return demoEntity;
            }
}
