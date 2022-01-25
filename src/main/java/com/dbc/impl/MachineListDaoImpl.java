package com.dbc.impl;

import com.dbc.dao.MachineListDao;
import com.dbc.entity.Machine_list;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class MachineListDaoImpl implements MachineListDao {


            @Autowired
            MongoTemplate mongoTemplate;

            @Override
            public List<String> SaveMachine(Machine_list machine_list) {

                mongoTemplate.save(machine_list);

                return null;
            }

            @Override
            public List<Machine_list> getMachine() {
                List<Machine_list> lists = mongoTemplate.findAll(Machine_list.class);
                return lists;
            }
}
