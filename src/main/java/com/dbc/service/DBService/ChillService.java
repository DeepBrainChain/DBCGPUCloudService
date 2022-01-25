package com.dbc.service.DBService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.ChillDao;
import com.dbc.entity.ChillListEntity;
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

/**
 *
 *      验证人地址
 */

@Service
@Slf4j
public class ChillService {

                @Autowired
                ChillDao chillDao;
                @Value("${chainUrl}")
            	private   String chainUrl;
                public ResponseEntity<HReply> Chill() throws URISyntaxException {

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
                                "      \"method\":\"committee_getCommitteeList\",\n" +
                                "      \"params\": []\n" +
                                "}");
                    }
                    /**
                     * 收到服务端消息调用
                     * @param s
                     */
                    @Override
                    public void onMessage(String s) {
                        log.info("====收到来自服务端的消息===" + s);
                        JSONObject data = new JSONObject(JSON.parseObject(s));
                        ChillListEntity chillListEntity = new ChillListEntity();
                        JSONObject resul= new JSONObject(JSON.parseObject(data.getString("result")));

                        JSONArray book = JSON.parseArray(resul.getString("chillList"));
                        JSONArray booke = JSON.parseArray(book.toJSONString());
                        if(booke != null && booke.size() > 0) {
                            for (int i = 0; i < booke.size(); i++) {
                                booke.get(i).toString();
                            }
                        }


                        JSONArray ful = JSON.parseArray(resul.getString("normal"));
                        JSONArray fulfill = JSON.parseArray(ful.toJSONString());
                        if(fulfill != null && fulfill.size() > 0) {
                            for (int i = 0; i < fulfill.size(); i++) {
                                fulfill.get(i).toString();
                            }
                        }


                        JSONArray m = JSON.parseArray(resul.getString("waitingBoxPubkey"));
                        JSONArray ma = JSON.parseArray(m.toJSONString());
                        if(ma != null && ma.size() > 0) {
                            for (int i = 0; i < ma.size(); i++) {
                                ma.get(i).toString();
                            }
                        }
                        chillListEntity.setId(data.getInteger("id"));
                        chillListEntity.setChill_list(booke);
                        chillListEntity.setNormal(fulfill);
                        chillListEntity.setWaiting_box_pubkey(ma);
                        chillDao.insertChill(chillListEntity);


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
