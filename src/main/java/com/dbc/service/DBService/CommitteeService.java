package com.dbc.service.DBService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.CommitteeListDao;
import com.dbc.dao.VerificationDao;
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
public class CommitteeService {


                @Autowired
                VerificationDao verificationDao;
                @Value("${chainUrl}")
            	private   String chainUrl;
                @Autowired
                CommitteeListDao committeeListDao;

                public String Commit() throws URISyntaxException {

                WebSocketClient client = new WebSocketClient(new URI(chainUrl),new Draft_6455()) {

                    private int count;
                    /**
                     * 建立连接调用
                     * @param serverHandshake
                     */
                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {
                        log.info("===建立连接===");
                        Verification verification = verificationDao.getVer(1);
                        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(verification));
                        JSONArray b = JSON.parseArray(data.getString("booked_machine"));
                        JSONArray bo = JSON.parseArray(b.toJSONString());

                        if(bo != null && bo.size() > 0) {
                            for (int i = 0; i < bo.size(); i++) {
                                List<String> s = Collections.singletonList(bo.get(i).toString());
//                                byte[] result = s.getBytes(StandardCharsets.UTF_8);


//                                String b = "5FHneW46xGXgs5mUiveU4sbTyGBzmstUspZC92UhjJM694ty";

                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("jsonrpc","2.0");
                                jsonObject.put("id",1);
                                jsonObject.put("method","onlineCommittee_getMachineCommitteeList");
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
                        Verification verification = verificationDao.getVer(1);
                        JSONObject datas = JSONObject.parseObject(JSONObject.toJSONString(verification));
                        JSON jsonArray = JSON.parseArray(datas.getString("booked_machine"));
                        JSONArray array = JSON.parseArray(jsonArray.toJSONString());
                        String f =null;


                            JSONObject data = new JSONObject(JSON.parseObject(s));
                            CommitteeListEntity machineCommitteeList = new CommitteeListEntity();
                            JSONObject result= new JSONObject(JSON.parseObject(data.getString("result")));



                            JSONArray b = JSON.parseArray(result.getString("bookedCommittee"));
                            JSONArray bo = JSON.parseArray(b.toJSONString());
                            if(bo != null && bo.size() > 0) {
                                for (int i = 0; i < bo.size(); i++) {
                                    bo.get(i).toString();
                                }
                            }

                            JSONArray c = JSON.parseArray(result.getString("confirmedCommittee"));
                            JSONArray con = JSON.parseArray(c.toJSONString());
                            if(con != null && con.size() > 0) {
                                for (int i = 0; i < con.size(); i++) {
                                    con.get(i).toString();
                                }
                            }

                            JSONArray h = JSON.parseArray(result.getString("hashedCommittee"));
                            JSONArray hashed = JSON.parseArray(h.toJSONString());
                            if(hashed != null && hashed.size() > 0  ) {
                                for (int i = 0; i < hashed.size(); i++) {
                                    hashed.get(i).toString();
                                }
                            }

                            JSONArray o = JSON.parseArray(result.getString("onlinedCommittee"));
                            JSONArray online = JSON.parseArray(o.toJSONString());
                            if(online != null && online.size() > 0) {
                                for (int i = 0; i < online.size(); i++) {
                                    JSONArray.parseObject(online.get(i).toString());
                                }
                            }



                                f  =   array.get(count).toString();
                                machineCommitteeList.setMachineId(f);
                                machineCommitteeList.setOnlined_committee(online);
                                machineCommitteeList.setBooked_committee(bo);
                                machineCommitteeList.setHashed_committee(hashed);
                                machineCommitteeList.setConfirmed_committee(con);
                                machineCommitteeList.setBook_time(result.getInteger("bookTime"));
                                machineCommitteeList.setConfirm_start_time(result.getInteger("confirmStartTime"));
                                machineCommitteeList.setStatus(result.getString("status"));
                                committeeListDao.SaveCommit(machineCommitteeList);

                                count+=1;






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
