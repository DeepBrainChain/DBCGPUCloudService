package com.dbc.service.committee;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.*;
import com.dbc.entity.CommitteeListEntity;
import com.dbc.entity.InformationEntity;
import com.dbc.entity.LCMachineEntity;
import com.dbc.entity.LCOpsEntity;
import com.dbc.service.DBService.HttpClient;
import com.utils.HReply;
import com.utils.HReplyStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class VerifierMachineService {


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

                    public ResponseEntity<HReply> Verifier_machine(String wallet, String language){


                            HReply hReply = HReply.createHReply(HReplyStatusEnum.DBCTRADE_GET_DBC_COUNT_SUCCESS,language);

                            try {

                                LCMachineEntity lcMachineEntity = lcMachineDao.getMachineInfo(wallet);
                                JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(lcMachineEntity));
                                JSONArray b = JSON.parseArray(data.getString("booked_machine"));
                                JSONArray booked = JSON.parseArray(b.toJSONString());

                                JSONArray h = JSON.parseArray(data.getString("hashed_machine"));
                                JSONArray hashed = JSON.parseArray(h.toJSONString());

                                JSONArray online = JSON.parseArray(data.getString("online_machine"));
                                JSONArray online_machine = JSON.parseArray(online.toJSONString());

                                List<Object> list = new ArrayList<>();


                                if(booked != null && booked.size()>0){

                                    for (Object o : booked) {

                                        Map<String,Object> map =new HashMap<>();
                                        String machine_id = o.toString();
                                        InformationEntity original = informationDao.getInfo(machine_id);
                                        if (original == null) {continue;}
                                        String a = original.getMachine_information();
                                        CommitteeListEntity committeeListEntity = committeeListDao.getTime(machine_id);
                                        int start_time = committeeListEntity.getConfirm_start_time();
                                        LCOpsEntity lcOpsEntity = lcOpsDao.getOPsInfo(wallet, machine_id);
                                        map.put("original", a);
                                        map.put("booked_machine", lcOpsEntity);
                                        map.put("confirm_start_time", start_time);
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
                                        LCOpsEntity lcOpsEntity = lcOpsDao.getOPsInfo(wallet, machine_id);
                                        maps.put("original", a);
                                        maps.put("hashed_machine", lcOpsEntity);
                                        maps.put("confirm_start_time", start_time);
                                        list.add(maps);

                                    }
                                }


                                if(online_machine != null && online_machine.size()>0){

                                    for (Object o : online_machine) {

                                        Map<String,Object> online_map =new HashMap<>();
                                        String machine_id = o.toString();
                                        //                                String original = client.SendPost(machine_id);
                                        InformationEntity original = informationDao.getInfo(machine_id);
                                        if (original == null) {
                                            continue;}
                                        String a = original.getMachine_information();
                                        CommitteeListEntity committeeListEntity = committeeListDao.getTime(machine_id);
                                        int start_time = committeeListEntity.getConfirm_start_time();
                                        LCOpsEntity lcOpsEntity = lcOpsDao.getOPsInfo(wallet, machine_id);
                                        online_map.put("original", a);
                                        online_map.put("hashed_machine", lcOpsEntity);
                                        online_map.put("confirm_start_time", start_time);
                                        list.add(online_map);

                                    }
                                }


                                hReply.setContent(list);
                            }catch (Exception e){

                                System.out.println("获取列表失败");
                                return null;

                            }


                            return ResponseEntity.ok(hReply);


                    }
}
