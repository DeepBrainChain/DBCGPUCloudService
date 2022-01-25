package com.dbc.impl;

import com.alibaba.fastjson.JSONArray;
import com.dbc.dao.MyVirtualDao;
import com.dbc.entity.MyVirtualEntity;
import com.mongodb.client.MongoCursor;
import com.utils.HReply;
import com.utils.HReplyStatusEnum;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyVirtualImpl implements MyVirtualDao {

            @Autowired
            MongoTemplate mongoTemplate;

            /**
             *  添加订单信息
             * @param myVirtualEntity
             */
            @Override
            public void addInfo(MyVirtualEntity myVirtualEntity) {
                mongoTemplate.save(myVirtualEntity);
            }


            @Override
            public ResponseEntity<HReply> getInfo(String wallet,String language) {

                HReply hReply = HReply.createHReply(HReplyStatusEnum.DBCTRADE_GET_DBC_V_SUCCESS,language);

                Query query = Query.query(Criteria.where("wallet").is(wallet));
                List<MyVirtualEntity> myVirtualEntity = mongoTemplate.find(query,MyVirtualEntity.class);

                hReply.setContent(myVirtualEntity);

                return ResponseEntity.ok(hReply);
            }

            @Override
            public void update(MyVirtualEntity myVirtualEntity) {

                Query query = Query.query(Criteria.where("_id").is(myVirtualEntity.getId()));
                Update update = Update.update("orderStatus",myVirtualEntity.getOrderStatus());
                mongoTemplate.updateFirst(query,update,MyVirtualEntity.class);
            }

            @Override
            public List<MyVirtualEntity> getOrder() {

                Query query = Query.query(Criteria.where("orderStatus").is("待确认支付"));
                List<MyVirtualEntity> myVirtualEntities = mongoTemplate.find(query,MyVirtualEntity.class);

                return myVirtualEntities;
            }

            // ---------续费
            @Override
            public void renew(MyVirtualEntity virtualEntity) {

                Query query = Query.query(Criteria.where("_id").is(virtualEntity.getId()));
                Update update = new Update();
                update.set("day",virtualEntity.getDay());
                update.set("dbc",virtualEntity.getDbc());
                mongoTemplate.updateFirst(query,update,MyVirtualEntity.class);

            }

            @Override
            public MyVirtualEntity Query(String id) {

                Query query = Query.query(Criteria.where("_id").is(id));
                MyVirtualEntity myVirtualEntity = mongoTemplate.findOne(query,MyVirtualEntity.class);

                return myVirtualEntity;
            }



            // 查询所有机器ID
            @Override
            public JSONArray MachineIdS() {

                    JSONArray jsonArray = new JSONArray();

                    List<Document> aggregateCondList = new ArrayList<Document>();
                    aggregateCondList.add(new Document());
                    aggregateCondList.add(new Document("machine_id",1).append("_id",1));

                    MongoCursor<Document> cursor = mongoTemplate.getCollection("MyVirtualInfo").find().iterator();
                    while (cursor.hasNext()){
                        Document document = cursor.next();

                        String id = document.getString("machine_id");
                        jsonArray.add(id);
                    }

                    return jsonArray;
            }

            // 主键查询临时钱包地址
            @Override
            public List<MyVirtualEntity> onlyKey() {


                List<MyVirtualEntity> myVirtualEntities = mongoTemplate.findAll(MyVirtualEntity.class);

                return myVirtualEntities;
            }

            @Override
            public String getWallet(String only_key) {

                Query query  = Query.query(Criteria.where("_id").is(only_key));

                MyVirtualEntity myVirtualEntity = mongoTemplate.findOne(query,MyVirtualEntity.class);
                assert myVirtualEntity != null;

                return myVirtualEntity.getWallet();
            }


}
