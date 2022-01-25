package com.dbc.service.DBService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.MachineListDao;
import com.dbc.entity.Machine_list;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Component
public class Socket {

                @Autowired
                MachineListDao machineListDao;
                @Value("${chainUrl}")
            	private   String chainUrl;
                    public String WebSocket() throws URISyntaxException {

                            //创建客户端连接对象
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
                                    Machine_list machine_list = new Machine_list();
                                    machine_list.setJsonrpc(data.getString("jsonrpc"));
                                    machine_list.setId(data.getInteger("id"));
                                    machineListDao.SaveMachine(machine_list);
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

                            //连接状态不再是0请求中，判断建立结果是不是1已建立
                            if (client.getReadyState().ordinal() == 1){

                            }

                        return null;
                    }
}
