package com.dbc.service.committee;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.*;
import com.dbc.entity.*;
import com.dbc.service.DBService.HttpClient;

import com.utils.HReply;
import com.utils.HReplyStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  前端接口：验证人传入wallet 获取可以验证的机器信息 原始信息和可以验证的时间段
 */
@Service
@Slf4j
public class GetInfoMachineService {

                    @Autowired
                    LCMachineDao lcMachineDao;

                    @Autowired
                    CommitteeListDao committeeListDao;

                    @Autowired
                    OriginalDao originalDao;

                    @Autowired
                    LCOpsDao lcOpsDao;

                    @Autowired
                    HttpClient client;

                    @Autowired
                    InformationDao informationDao;

//                    @Cacheable(cacheNames = {"By"},value = "DefaultKeyTest",keyGenerator = "simpleKeyGenerator")
                    public ResponseEntity<HReply> getMachinesBywallet(String wallet,String language){

//                        SendMail.send_common("heaven@dbchain.ai",
//                                "service@dbchain.ai",
//                                "DBC service",
//                                "验证机器",
//                                "当前有机器需要验证",
//                                "dbc service",
//                                "https://www.deepbrainchain.org");
                        HReply hReply = HReply.createHReply(HReplyStatusEnum.DBCTRADE_GET_DBC_COUNT_SUCCESS,language);

                        try {

                            LCMachineEntity lcMachineEntity = lcMachineDao.getMachineInfo(wallet);
                            JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(lcMachineEntity));
                            JSONArray b = JSON.parseArray(data.getString("booked_machine"));
                            JSONArray booked = JSON.parseArray(b.toJSONString());

                            JSONArray h = JSON.parseArray(data.getString("hashed_machine"));
                            JSONArray hashed = JSON.parseArray(h.toJSONString());

                            List<Object> list = new ArrayList<>();


                            if(booked != null && booked.size()>0){

                                for (Object o : booked) {

                                    Map<String,Object> map =new HashMap<>();
                                    String machine_id = o.toString();
//                                String original = client.SendPost(machine_id);
                                    InformationEntity original = informationDao.getInfo(machine_id);
                                    if (original == null) {continue;}
                                    String a = original.getMachine_information();
                                    CommitteeListEntity committeeListEntity = committeeListDao.getTime(machine_id);
                                    int start_time = committeeListEntity.getConfirm_start_time();
                                    int HashSize = committeeListEntity.getHashed_committee().size();
                                    LCOpsEntity lcOpsEntity = lcOpsDao.getOPsInfo(wallet, machine_id);
                                    map.put("original", a);
                                    map.put("booked_machine", lcOpsEntity);
                                    map.put("confirm_start_time", start_time);
                                    map.put("HashSize",HashSize);
                                    list.add(map);

                                }

                            }


                            if(hashed != null && hashed.size()>0){

                                for (Object o : hashed) {

                                    Map<String,Object> maps =new HashMap<>();
                                    String machine_id = o.toString();
//                                String original = client.SendPost(machine_id);
                                    InformationEntity original = informationDao.getInfo(machine_id);
                                    if (original == null) {
                                        continue;}
                                    String a = original.getMachine_information();
                                    CommitteeListEntity committeeListEntity = committeeListDao.getTime(machine_id);
                                    int start_time = committeeListEntity.getConfirm_start_time();
                                    int HashSize = committeeListEntity.getHashed_committee().size();
                                    LCOpsEntity lcOpsEntity = lcOpsDao.getOPsInfo(wallet, machine_id);
                                    maps.put("original", a);
                                    maps.put("hashed_machine", lcOpsEntity);
                                    maps.put("confirm_start_time", start_time);
                                    maps.put("HashSize",HashSize);
                                    list.add(maps);

                                }
                            }


                            hReply.setContent(list);
                        }catch (Exception e){

                            System.out.println("不存再钱包地址");
                            return null;

                        }


                        return ResponseEntity.ok(hReply);


                    }
}

