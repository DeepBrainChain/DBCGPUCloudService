package com.dbc.impl;

import com.dbc.dao.LCMachineDao;
import com.dbc.entity.LCMachineEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LCMachineDaoImpl implements LCMachineDao {

                @Autowired
                MongoTemplate mongoTemplate;


                @Override
                public void InsertLCM(LCMachineEntity lcMachine) { mongoTemplate.save(lcMachine); }



                // 通过钱包地址获取验证的机器
                @Override
                public LCMachineEntity getMachineInfo(String wallet) {
                    Query query = Query.query(Criteria.where("wallet").is(wallet));;
                    LCMachineEntity lcMachineEntity = mongoTemplate.findOne(query,LCMachineEntity.class);

                    return lcMachineEntity;
                }

                // 查询列表
                @Override
                public List<LCMachineEntity> FindAllMachine() {

                    List<LCMachineEntity> lcMachineEntities = mongoTemplate.findAll(LCMachineEntity.class);
                    return lcMachineEntities;
                }
}
