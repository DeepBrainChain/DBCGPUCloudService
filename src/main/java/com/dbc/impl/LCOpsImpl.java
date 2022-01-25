package com.dbc.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.LCOpsDao;
import com.dbc.entity.LCOpsEntity;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LCOpsImpl implements LCOpsDao {


                @Autowired
                MongoTemplate mongoTemplate;

                @Override
                public void AddLCO(LCOpsEntity lcOps) {

                    mongoTemplate.save(lcOps);
                }

                @Override
                public LCOpsEntity getOPsInfo(String mid,String booked_committee) {
                    Query query = new Query();
                    query.addCriteria(Criteria.where("mid").is(mid));
                    query.addCriteria(Criteria.where("booked_committee").is(booked_committee));
                    LCOpsEntity lcOpsEntity = mongoTemplate.findOne(query,LCOpsEntity.class);

                    return lcOpsEntity;
                }

//                @Override
//                public void DeleteLCO(LCOpsEntity lcOpsEntity) {
//
//                    mongoTemplate.dropCollection(LCOpsEntity.class);
//
//                }

                @Override
                public JSONArray FindAll() {

                    JSONArray jsonObject = new JSONArray();
                    List<LCOpsEntity> lcOpsEntities =  mongoTemplate.findAll(LCOpsEntity.class);
                    jsonObject.add(lcOpsEntities);

                    return jsonObject;
                }

//                @Override
//                public JSONObject mid() {
//
//
//                    JSONObject result = new JSONObject();
//
//                    List<Document> aggregateCondList = new ArrayList<Document>();
//                    aggregateCondList.add(new Document());
//                    aggregateCondList.add(new Document("mid",1).append("_id",0));
//
//                    //遍历结果集
//                    MongoCursor<Document> cursor = mongoTemplate.getCollection("LCCommitteeOps").find().iterator();
//                    while (cursor.hasNext()) {
//                        Document doc = cursor.next();
//
//            //                            String treatVal = doc.getString("machine_stash");
//                        String id = doc.getString("mid");
//
//            //                            result.add(treatVal);
//                        result.put("mid",id);
//                    }
//
//                    return result;
//                }

                @Override
                public JSONArray booked_committee() {


                    JSONArray result = new JSONArray();

                    List<Document> aggregateCondList = new ArrayList<Document>();
                    aggregateCondList.add(new Document());
                    aggregateCondList.add(new Document("booked_committee",1).append("_id",1));

                    //遍历结果集
                    MongoCursor<Document> cursor = mongoTemplate.getCollection("LCCommitteeOps").find().iterator();
                    while (cursor.hasNext()) {
                        Document doc = cursor.next();
                        String book = doc.getString("booked_committee");
                        String mid = doc.getString("_id");

                        result.add(mid);

                    }

                    return result;
                }

                @Override
                public LCOpsEntity getIdInfo(String mid) {
                    Query query = Query.query(Criteria.where("_id").is(mid));
                    LCOpsEntity lcOpsEntity = mongoTemplate.findOne(query,LCOpsEntity.class);

                    return lcOpsEntity;
                }

                @Override
                public void updateInfo(LCOpsEntity lcOpsEntity) {

                    Query query = Query.query(Criteria.where("_id").is(lcOpsEntity.getId()));
                    Update update = Update.update("emil_state",lcOpsEntity.getEmil_state());
                    mongoTemplate.updateFirst(query,update,LCOpsEntity.class);

                }
}
