package com.dbc.impl;

import com.dbc.dao.GpuinfoDao;
import com.dbc.entity.Gpuinfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GpuinfoImpl implements GpuinfoDao {


                @Autowired
                MongoTemplate mongoTemplate;

                @Override
                public List<Gpuinfor> getGpu() {

                    List<Gpuinfor> gpuinforList = mongoTemplate.findAll(Gpuinfor.class,"GPU_information");
                    return gpuinforList;
                }
}
