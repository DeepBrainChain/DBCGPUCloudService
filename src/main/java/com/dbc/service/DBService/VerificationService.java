package com.dbc.service.DBService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.VerificationDao;
import com.dbc.entity.Verification;

import com.utils.HReply;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 *    链上所有机器状态（machine_id 机器ID）
 */
@Service
@Slf4j
public class VerificationService {

            @Autowired
            VerificationDao verificationDao;
            @Value("${chainUrl}")
        	private   String chainUrl;
            public ResponseEntity<HReply> Verification() throws URISyntaxException {

                WebSocketClient client = new WebSocketClient(new URI(chainUrl),new Draft_6455()) {
                    /**
                     * 建立连接调用
                     * @param serverHandshake
                     */
                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {
                        log.info("===建立连接===");
                        send("{\n" +
                                "     \"jsonrpc\":\"2.0\",\n" +
                                "      \"id\":1,\n" +
                                "      \"method\":\"onlineProfile_getMachineList\",\n" +
                                "      \"params\": []\n" +
                                "    }");
                    }
                    /**
                     * 收到服务端消息调用
                     * @param s
                     */
                    @Override
                    public void onMessage(String s) {
                        log.info("====收到来自服务端的消息===" + s);
                        JSONObject data = new JSONObject(JSON.parseObject(s));
                        Verification verification = new Verification();
                       // verification.setResult(data.getString("result"));
                        JSONObject result= new JSONObject(JSON.parseObject(data.getString("result")));


                        JSONArray b = JSON.parseArray(result.getString("bondingMachine"));
                        JSONArray bo = JSON.parseArray(b.toJSONString());
                        List<String> list = new ArrayList<>();
                        if(bo != null && bo.size() > 0) {
                            for (int i = 0; i < bo.size(); i++) {
                                byte[] bon = JSONArray.parseObject(bo.get(i).toString(), byte[].class);
                                String bonding =new String(bon);
                                list.add(bonding);
                            }
                        }

                        //String bonding =new String(bon);

                        JSONArray book = JSON.parseArray(result.getString("bookedMachine"));
                        JSONArray booke = JSON.parseArray(book.toJSONString());
                        List<String> listbook = new ArrayList<>();
                        if(booke != null && booke.size() > 0) {
                            for (int i = 0; i < booke.size(); i++) {
                                byte[] mach= JSONArray.parseObject(booke.get(i).toString(),byte[].class);
                                String booked =new String(mach);
                                listbook.add(booked);
                            }
                        }


                        JSONArray ful = JSON.parseArray(result.getString("fulfillingMachine"));
                        JSONArray fulfill = JSON.parseArray(ful.toJSONString());
                        List<String> listful = new ArrayList<>();
                        if(fulfill != null && fulfill.size() > 0) {
                            for (int i = 0; i < fulfill.size(); i++) {
                                byte[] fulfillin= JSONArray.parseObject(fulfill.get(i).toString(),byte[].class);
                                String fulfilling =new String(fulfillin);
                                listful.add(fulfilling);
                            }
                        }


                        JSONArray m = JSON.parseArray(result.getString("confirmedMachine"));
                        JSONArray ma = JSON.parseArray(m.toJSONString());
                        List<String> listmachine = new ArrayList<>();
                        if(ma != null && ma.size() > 0) {
                            for (int i = 0; i < ma.size(); i++) {
                                byte[] maf= JSONArray.parseObject(ma.get(i).toString(),byte[].class);
                                String machine =new String(maf);
                                listmachine.add(machine);
                            }
                        }

                        JSONArray r = JSON.parseArray(result.getString("refusedMachine"));
                        JSONArray re = JSON.parseArray(r.toJSONString());
                        List<String> refuse = new ArrayList<>();
                        if(re != null && re.size() > 0) {
                                for (int i = 0; i < re.size(); i++) {
                                    byte[] fuse= JSONArray.parseObject(re.get(i).toString(),byte[].class);
                                    String s1 =new String(fuse);
                                    refuse.add(s1);
                                }
                        }



                        JSONArray arr = JSON.parseArray(result.getString("onlineMachine"));
                       // String b = resul.getString("booked_machine"); //  [[u8], [u8]]
                        JSONArray a = JSON.parseArray(arr.toJSONString());
                        List<String> on = new ArrayList<>();
                        if(a != null && a.size() > 0) {
                            for (int i = 0; i < a.size(); i++) {
                                byte[] c = JSONArray.parseObject(a.get(i).toString(), byte[].class);
                                String online =new String(c);
                                on.add(online);
                            }
                        }

                        JSONArray objects = JSON.parseArray(result.getString("rentedMachine"));
                        // String b = resul.getString("booked_machine"); //  [[u8], [u8]]
                        JSONArray jsonArray = JSON.parseArray(objects.toJSONString());
                        List<String> list1 = new ArrayList<>();
                        if(jsonArray != null && jsonArray.size() > 0) {
                            for (int i = 0; i < jsonArray.size(); i++) {
                                byte[] t = JSONArray.parseObject(jsonArray.get(i).toString(), byte[].class);
                                String s1 =new String(t);
                                list1.add(s1);
                            }
                        }

                        verification.setBonding_machine(list);
                        verification.setBooked_machine(listbook);
                        verification.setFulfilling_machine(listful);
                        verification.setConfirmed_machine(listmachine);
                        verification.setOnline_machine(on);
                        verification.setRefused_machine(refuse);
                        verification.setId(data.getInteger("id"));
                        verification.setRented_machine(list1);
                        verificationDao.addVerification(verification);


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
