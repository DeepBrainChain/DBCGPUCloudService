package com.dbc.impl;

import com.dbc.dao.GetBlockHashDao;
import com.dbc.entity.GetBlockHashEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;


@Component
public class GetBlockHashImpl implements GetBlockHashDao {

                @Autowired
                MongoTemplate mongoTemplate;

                /**
                 *
                 * @param getBlockHashEntity 获取当前快高
                 */
                @Override
                public void insertBlockHash(GetBlockHashEntity getBlockHashEntity) {
                    mongoTemplate.save(getBlockHashEntity);
                }


                @Override
                public Integer FindHash(int id) {

                    Query query = Query.query(Criteria.where("_id").is(id));

                    GetBlockHashEntity getBlockHashEntity = mongoTemplate.findOne(query,GetBlockHashEntity.class);

                    return getBlockHashEntity.getNumber();
                }
}
