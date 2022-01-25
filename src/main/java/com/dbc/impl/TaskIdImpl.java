package com.dbc.impl;

import com.dbc.dao.TaskIdDao;
import com.dbc.entity.TaskIdEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class TaskIdImpl implements TaskIdDao {

                @Autowired
                MongoTemplate mongoTemplate;

                @Override
                public void Save_TaskId(TaskIdEntity taskIdEntity) {

                     mongoTemplate.save(taskIdEntity);

                }

                @Override
                public String Query_id(String machine_id) {

                    Query query = Query.query(Criteria.where("_id").is(machine_id));
                    TaskIdEntity taskIdEntity = mongoTemplate.findOne(query,TaskIdEntity.class);
                    return taskIdEntity.getTask_id();
                }
}
