package com.dbc.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.URI;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class WebSocket implements Serializable {


                public static void main(String[] args) {

                    try {
                        //创建客户端连接对象
                        WebSocketClient client = new WebSocketClient(new URI("wss://infotest.dbcwallet.io"),new Draft_6455()) {
                            /**
                             * 建立连接调用
                             * @param serverHandshake
                             */
                            @Override
                            public void onOpen(ServerHandshake serverHandshake) {
                                log.info("===建立连接===");
                                JSONArray list =new JSONArray();
                                String b = "5FHneW46xGXgs5mUiveU4sbTyGBzmstUspZC92UhjJM694ty";
                                list.add(b);
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("jsonrpc","2.0");
                                jsonObject.put("id",1);
                                jsonObject.put("params",list);
                                System.out.println("jsonObject1：" + jsonObject.toString());

//
//                                send(jsonObject.toString());
//
//
//
//
//                                send("{\n" +
//                                        "     \"jsonrpc\":\"2.0\",\n" +
//                                        "      \"id\":1,\n" +
//                                        "      \"method\":\"chain_getHeader\",\n" +
//                                        "      \"params\": []\n" +
//                                        "    }");
//
//                                send("{\n" +
//                                        "     \"jsonrpc\":\"2.0\",\n" +
//                                        "      \"id\":1,\n" +
//                                        "      \"method\":\"onlineCommittee_getCommitteeOps\",\n" +
//                                        "      \"params\": [\"5DdA3eHdWKuHLjqEquKQzyvhumNBEN32RxRWkuuaFvda474S\n\",\"aa2f6c0bdb394fe6cc6a0165cde1d1c5089e20c3fcf815841d8ae1dd087eab22\"]\n" +
//                                        "}");
//
//
//                                send("{\n" +
//                                        "     \"jsonrpc\":\"2.0\",\n" +
//                                        "      \"id\":1,\n" +
//                                        "      \"method\":\"onilneCommittee_getCommitteeMachineList\",\n" +
//                                        "      \"params\": [\"5Cyb1Tx992KdpAdAwMfg4JJNMmCpidPybcSZniyRaPnp5q8z\"]\n" +
//                                        "    }");
//
                                send("{\n" +
                                        "     \"jsonrpc\":\"2.0\",\n" +
                                        "      \"id\":1,\n" +
                                        "      \"method\":\"onlineCommittee_getMachineCommitteeList\",\n" +
                                        "      \"params\": [\"dec5a3d0bb8c93c2779abaee44665f84f30d7abab279cf240ad818905449f07f\"]\n" +
                                        "}");
//
//                                send("{\n" +
//                                        "     \"jsonrpc\":\"2.0\",\n" +
//                                        "      \"id\":1,\n" +
//                                        "      \"method\":\"onlineProfile_getMachineInfo\",\n" +
//                                        "      \"params\": [\"166aead3997957ce0e76b9e5fa5b12e2b7bd04a964f267171a2d458300ae7021\"]\n" +
//                                        "}");
//
//                                send("{\n" +
//                                        "     \"jsonrpc\":\"2.0\",\n" +
//                                        "      \"id\":1,\n" +
//                                        "      \"method\":\"onlineProfile_getStakerIdentity\",\n" +
//                                        "      \"params\": [\"5CoEB32UJESDaEQnGcsL26dA3ww4HJhbyNTpvBTFpVQz2y98\"]\n" +
//                                        "    }");

                                send("{\n" +
                                        "        \"jsonrpc\":\"2.0\",\n" +
                                        "            \"id\":1,\n" +
                                        "            \"method\":\"rentMachine_getRentOrder\",\n" +
                                        "            \"params\": [\"5CJBjzZ9crAQ83iFu3XtDm6HnaKbfRqyryKKMuWAzGRQgSrr\", \"d6151ed30f69806f2b4ea4a76be2e092f0d5a0b196a525413203426cf42e8d2c\"]\n" +
                                        "    }");

                            }
                            /**
                             * 收到服务端消息调用
                             * @param s
                             */
                            @Override
                            public void onMessage(String s) {
                                log.info("====收到来自服务端的消息===" + s);
                            }
                            /**
                             * 断开连接调用
                             * @param i
                             * @param s
                             * @param b
                             */
                            @Override
                            public void onClose(int i, String s, boolean b) {
                                log.info("关闭连接:::" + "i = " + i + ":::s = " + s +":::b = " +   b);
                            }
                            /**     dbchaininfo.congtu.cloud  identifier.congtu.cloud
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

                        if (client.getReadyState().ordinal() == 1){

                        }
                    }catch (URISyntaxException e){
                        log.error(e.getMessage());
                    }
                }




}
