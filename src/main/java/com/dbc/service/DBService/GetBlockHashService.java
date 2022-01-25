package com.dbc.service.DBService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.GetBlockHashDao;
import com.dbc.entity.GetBlockHashEntity;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Service
public class GetBlockHashService {

            @Autowired
            GetBlockHashDao getBlockHashDao;
            @Value("${chainUrl}")
        	private   String chainUrl;
            
            public String BlockHash() throws URISyntaxException {

                WebSocketClient client = new WebSocketClient(new URI(chainUrl),new Draft_6455()) {

                        @Override
                        public void onOpen(ServerHandshake serverHandshake) {

                            send("{\n" +
                                    "     \"jsonrpc\":\"2.0\",\n" +
                                    "      \"id\":1,\n" +
                                    "      \"method\":\"chain_getHeader\",\n" +
                                    "      \"params\": []\n" +
                                    "    }");
                        }

                        @Override
                        public void onMessage(String s) {
                            log.info("====收到来自服务端的消息===" + s);


                            JSONObject data = new JSONObject(JSON.parseObject(s));
                            JSONObject result= new JSONObject(JSON.parseObject(data.getString("result")));

                            String number = result.getString("number");
                            int b =Integer.parseInt(number.replaceAll("^0[x|X]", ""), 16);      //正则表达式去掉0x



                            GetBlockHashEntity getBlockHashEntity = new GetBlockHashEntity();
                            getBlockHashEntity.setId(1);
                            getBlockHashEntity.setNumber(b);
                            getBlockHashDao.insertBlockHash(getBlockHashEntity);
                        }

                        @Override
                        public void onClose(int i, String s, boolean b) {
                            log.info("关闭连接:::" + "i = " + i + ":::s = " + s +":::b = " + b);
                        }

                        @Override
                        public void onError(Exception e) {
                            log.error("====出现错误====" + e.getMessage());
                        }

                };
                client.connect();
                return null;
            }

}
