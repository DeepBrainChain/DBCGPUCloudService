package com.dbc.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.DBCWalletDao;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.dbc.entity.DBCWalletEntity;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/***
 * @Author feng
 * @Date 2019年8月13日 下午6:20:19
 * @Email:feng@deepbrain.ai
 * @Description:
 */

@Slf4j
@Component("DBCWalletDao")
public class DBCWalletDaoImpl implements DBCWalletDao {

			@Autowired
			private MongoTemplate mongoTemplate;

			@Override
			public void save(String only_key,String machine_id,String wallet,String wif,String random_number) {
				// TODO Auto-generated method stub
				try {
					DBCWalletEntity entity=new DBCWalletEntity();
					entity.setOnly_key(only_key);
					entity.setMachine_id(machine_id);
					entity.setWallet(wallet);
					entity.setWif(wif);
					entity.setRandom_number(random_number);
					//entity.setUser_name_platform(user_name_platform);
					mongoTemplate.insert(entity, "DBCWalletInfo");
				}catch (Exception e){
					log.info("机器已存在钱包");
				}

			}

			@Override
			public String getWif(String only_key) {
				// TODO Auto-generated method stub
				Query query=new Query();
				query.addCriteria(Criteria.where("_id").is(only_key));
				//query.addCriteria(Criteria.where("user_name_platform").is(user_name_platform));
				DBCWalletEntity entity=mongoTemplate.findOne(query, DBCWalletEntity.class);
				if (entity!=null) {
					return entity.getWif();

				}else {
					return "";
				}
			}

			@Override
			public JSONObject getInfo(String only_key) {

				JSONObject jsonObject = new JSONObject();
				Query query = Query.query(Criteria.where("_id").is(only_key));
				DBCWalletEntity dbcWalletEntity = mongoTemplate.findOne(query,DBCWalletEntity.class);
				jsonObject.put("wallet",dbcWalletEntity.getWallet());
				jsonObject.put("random_number",dbcWalletEntity.getRandom_number());
				return jsonObject;
			}

			@Override
			public String getWallet(String only_key) {
				// TODO Auto-generated method stub
				Query query=new Query();
				query.addCriteria(Criteria.where("_id").is(only_key));
				//query.addCriteria(Criteria.where("user_name_platform").is(user_name_platform));
				DBCWalletEntity entity=mongoTemplate.findOne(query, DBCWalletEntity.class);
				if (entity!=null) {
					return entity.getWallet();

				}else {
					return "";
				}
			}

			@Override
			public JSONArray Wallets() {

				JSONArray jsonArray = new JSONArray();

				List<Document> aggregateCondList = new ArrayList<Document>();
				aggregateCondList.add(new Document());
				aggregateCondList.add(new Document("machine_id",1).append("_id",1));

				MongoCursor<Document> cursor = mongoTemplate.getCollection("DBCWalletInfo").find().iterator();
				while (cursor.hasNext()) {

						Document doc = cursor.next();

						String id = doc.getString("_id");
						String wallet = doc.getString("wallet");

						jsonArray.add(id);
//						jsonArray.add(wallet);
				}

				return jsonArray;
			}

			@Override
			public DBCWalletEntity QueryInfo(String only_key) {

				Query query = Query.query(Criteria.where("_id").is(only_key));

				DBCWalletEntity dbcWalletEntity = mongoTemplate.findOne(query,DBCWalletEntity.class);
				return dbcWalletEntity;
			}

//			@Override
//			public List<DBCWalletEntity> Wallets(String only_key) {
//
//				Query query = Query.query(Criteria.where("_id").is(only_key));
//
//				List<DBCWalletEntity> dbcWalletEntities = mongoTemplate.findAll(query,DBCWalletEntity.class);
//				return null;
//			}

}
