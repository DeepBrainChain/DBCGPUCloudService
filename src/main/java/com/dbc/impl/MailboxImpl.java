package com.dbc.impl;

import com.dbc.dao.MailboxDao;
import com.dbc.entity.MailboxEntity;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MailboxImpl implements MailboxDao {


            @Autowired
            MongoTemplate mongoTemplate;

            @Override
            public void insert(MailboxEntity mailboxEntity) {

                mongoTemplate.save(mailboxEntity);
//                MongoConverter converter =mongoTemplate.getConverter();
//                if (converter.getTypeMapper().isTypeKey("_class")){
//                    ((MappingMongoConverter) converter).setTypeMapper(new DefaultMongoTypeMapper(null));
//                }
            }

            /**
             *  db.getCollection('CommitteeMailbox').find({"_id":"5HgjMd2iuiyzPvurozVydmwCcSbDZRgdA6UHtvtXi9KSr3A8"}, {"emil":1,"_id":0})
             *  根据钱包地址获取邮箱
             * @return
             */
            @Override
            public String SelectEmil(String wallet) {

                    List<Document> aggregateCondList = new ArrayList<Document>();
                    aggregateCondList.add(new Document("_id",wallet));
                    aggregateCondList.add(new Document("emil",1).append("_id",0));

                    MongoCursor<Document> machineDetailsInfo = mongoTemplate.getCollection("CommitteeMailbox").find().iterator();

                    Document document = machineDetailsInfo.next();
                    String emil = document.getString("emil");

                    try {
                        Query query = Query.query(Criteria.where("_id").is(wallet));
                        MailboxEntity mailboxEntity = mongoTemplate.findOne(query,MailboxEntity.class);
                        return mailboxEntity.getEmil();
                    }catch (Exception e){
                        System.out.println("未找到钱包对应的邮箱");
                    }

                    return null;



            }
}
