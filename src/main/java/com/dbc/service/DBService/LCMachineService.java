package com.dbc.service.DBService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.ChillDao;
import com.dbc.dao.CommitteeListDao;
import com.dbc.dao.LCMachineDao;
import com.dbc.dao.VerificationDao;
import com.dbc.entity.ChillListEntity;
import com.dbc.entity.LCMachineEntity;
import com.dbc.entity.CommitteeListEntity;
import com.dbc.entity.Verification;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class LCMachineService {


            @Autowired
            LCMachineDao lcMachineDao;

            @Autowired
            CommitteeListDao committeeListDao;

            @Autowired
            VerificationDao verificationDao;

            @Autowired
            ChillDao chillDao;

            @Value("${chainUrl}")
        	private   String chainUrl;
            public String LCMachine() throws URISyntaxException {

                WebSocketClient client = new WebSocketClient(new URI(chainUrl),new Draft_6455()) {
                    private int count;
                    /**
                     * 建立连接调用
                     * @param serverHandshake
                     */
                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {

//                        Verification verification = verificationDao.getVer(1);
//                        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(verification));
//                        JSONArray b = JSON.parseArray(data.getString("online_machine"));
//                        JSONArray mach = JSON.parseArray(b.toJSONString());
//
//                        if(mach != null && mach.size() > 0) {
//                            for (int i = 0; i < mach.size(); i++) {
//                                String id = mach.get(i).toString();
//                                CommitteeListEntity machineCommitteeLists= committeeListDao.getComm(id);
//                                JSONObject datas = JSONObject.parseObject(JSONObject.toJSONString(machineCommitteeLists));
//
//                                JSONArray comm = JSON.parseArray(datas.getString("booked_committee"));
//                                JSONArray bo = JSON.parseArray(comm.toJSONString());
//
//
//                                if(bo != null && bo.size() > 0) {
//                                    for (int f = 0; f < bo.size(); f++) {
//                                        JSONArray jsonArray = new JSONArray();
//                                        String s = bo.get(f).toString();
//                                        jsonArray.add(s);
//                                        JSONObject jsonObject = new JSONObject();
//                                        jsonObject.put("jsonrpc","2.0");
//                                        jsonObject.put("id",1);
//                                        jsonObject.put("method","leaseCommittee_getCommitteeMachineList");
//                                        jsonObject.put("params",jsonArray);
//                                        System.out.println("jsonObject1：" + jsonObject.toString());
//
//                                        send(jsonObject.toString());
//
//                                    }
//                                }
//
//
//                            }
//                        }


                        ChillListEntity chillListEntity = chillDao.getChilInfo(1);
                        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(chillListEntity));
                        JSONArray b = JSON.parseArray(data.getString("normal"));
                        JSONArray bo = JSON.parseArray(b.toJSONString());
//                        JSONArray c = JSON.parseArray(data.getString("chill_list"));
//                        JSONArray chill = JSON.parseArray(c.toJSONString());

                        if(bo != null && bo.size() > 0) {
                            for (int i = 0; i < bo.size(); i++) {
                                List<String> s = Collections.singletonList(bo.get(i).toString());

                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("jsonrpc","2.0");
                                jsonObject.put("id",1);
                                jsonObject.put("method","onilneCommittee_getCommitteeMachineList");
                                jsonObject.put("params",s);
                                System.out.println("jsonObject1：" + jsonObject.toString());

                                send(jsonObject.toString());



                            }
                        }


                    }
                    /**
                     * 收到服务端消息调用
                     * @param s
                     */
                    @Override
                    public void onMessage(String s) {

                        log.info("====收到来自服务端的消息===" + s);
                        ChillListEntity chillListEntity = chillDao.getChilInfo(1);
                        JSONObject datas = JSONObject.parseObject(JSONObject.toJSONString(chillListEntity));
                        JSONArray b = JSON.parseArray(datas.getString("normal"));
                        JSONArray bo = JSON.parseArray(b.toJSONString());


                        JSONObject data = new JSONObject(JSON.parseObject(s));
                        LCMachineEntity lcMachine = new LCMachineEntity();
                        JSONObject result= new JSONObject(JSON.parseObject(data.getString("result")));

                        JSONArray jsonArray = JSON.parseArray(result.getString("bookedMachine"));
                        JSONArray verify = JSON.parseArray(jsonArray.toJSONString());
                        JSONArray j = new JSONArray();
                        if(verify != null && verify.size() > 0) {
                            for (int i = 0; i < verify.size(); i++) {
                                byte [] bytes = JSONArray.parseObject(verify.get(i).toString(),byte[].class);
                                String machines = new String(bytes);
                                j.add(machines);
                            }
                        }

                        JSONArray con = JSON.parseArray(result.getString("confirmedMachine"));
                        JSONArray confirmed = JSON.parseArray(con.toJSONString());
                        JSONArray objects = new JSONArray();
                        if(confirmed != null && confirmed.size() > 0) {
                            for (int i = 0; i < confirmed.size(); i++) {
                                byte [] bytes = JSONArray.parseObject(confirmed.get(i).toString(),byte[].class);
                                String s1 = new String(bytes);
                                objects.add(s1);
                            }
                        }

                        JSONArray h = JSON.parseArray(result.getString("hashedMachine"));
                        JSONArray hash = JSON.parseArray(h.toJSONString());
                        JSONArray jsonArray1 = new JSONArray();
                        if(hash != null && hash.size() > 0) {
                            for (int i = 0; i < hash.size(); i++) {
                                byte [] bytes = JSONArray.parseObject(hash.get(i).toString(),byte[].class);
                                String s2 = new String(bytes);
                                jsonArray1.add(s2);
                            }
                        }

                        JSONArray o = JSON.parseArray(result.getString("onlineMachine"));
                        JSONArray online = JSON.parseArray(o.toJSONString());
                        JSONArray js = new JSONArray();
                        if(online != null && online.size() > 0) {
                            for (int i = 0; i < online.size(); i++) {
                                byte[] fuse= JSONArray.parseObject(online.get(i).toString(),byte[].class);
                                String on =new String(fuse);
                                js.add(on);
                            }
                        }




                            lcMachine.setWallet(bo.get(count).toString());
                            lcMachine.setBooked_machine(j);
                            lcMachine.setConfirmed_machine(objects);
                            lcMachine.setHashed_machine(jsonArray1);
                            lcMachine.setOnline_machine(js);
                            lcMachineDao.InsertLCM(lcMachine);


                            count +=1;


                    }
                    /**
                     * 断开连接调用
                     * @param i
                     * @param s
                     * @param b
                     */
                    @Override
                    public void onClose(int i, String s, boolean b) {
                        log.info("关闭连接:::" + "i = " + i + ":::s = " + s +":::b = " + b);
                    }
                    /**
                     * 连接报错调用
                     * @param e
                     */
                    @Override
                    public void onError(Exception e) {
                        log.error("====出现错误====" + e.getMessage());
                    }

                };
                //请求与服务端建立连接
                client.connect();

                return null;
            }
}
