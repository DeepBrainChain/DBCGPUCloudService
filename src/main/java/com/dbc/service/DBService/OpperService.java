package com.dbc.service.DBService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.MachineDetailsDao;
import com.dbc.dao.VerificationDao;
import com.dbc.entity.MachineDetailsEntity;
import com.dbc.entity.Machine_list;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *     更新表 ：更新机器对应的算工名称（后执行）
 */

@Service
@Slf4j
public class OpperService {
            @Autowired
            VerificationDao verificationDao;

            @Autowired
            MachineDetailsDao machineDetailsDao;
            @Value("${chainUrl}")
        	private   String chainUrl;
            public String L_Details() throws URISyntaxException {

                WebSocketClient c = new WebSocketClient(new URI(chainUrl),new Draft_6455()) {
                    private int count;
                    JSONArray list = machineDetailsDao.findInfo();

                    //                    private List<String> machine_id = new ArrayList<>();
                    //private int count;
                    /**
                     * 建立连接调用
                     * @param serverHandshake
                     */
                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {

                        for (int i=0; i<list.size(); i++) {
                            MachineDetailsEntity machineDetailsEntities = machineDetailsDao.selectOwer(list.getString(i));
        //                        Map<String, MachineDetailsEntity> mappedMovies = new HashMap<String,MachineDetailsEntity>();
        //                        for (MachineDetailsEntity m : machineDetailsEntities) {
        //                            mappedMovies.put(m.getmachine_stash(), m);
        //                        }

                            JSONObject datas = JSONObject.parseObject(JSONObject.toJSONString(machineDetailsEntities));
                            String b= datas.getString("machine_stash");



                            List<String> f = new ArrayList<>();
                            f.add(b);
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("jsonrpc", "2.0");
                            jsonObject.put("id", 1);
                            jsonObject.put("method", "onlineProfile_getStakerIdentity");
                            jsonObject.put("params",f);
                            //                                machine_id.add(id);

                            send(jsonObject.toString());
                        }


                    }

                    @SneakyThrows
                    @Override
                    public void onMessage(String s) {
                        log.info("====收到来自服务端的消息===" + s);
                        JSONObject data = new JSONObject(JSON.parseObject(s));
                        JSONArray jsonArray = machineDetailsDao.findid();
                        MachineDetailsEntity detailsEntity = new MachineDetailsEntity();

                        detailsEntity.setOperator(data.getString("result"));
                        detailsEntity.setMachine_id(jsonArray.getString(count));
                        machineDetailsDao.UpdateDetails(detailsEntity);
                        count+=1;

                    }

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
                c.connect();
                return  null;
    }
}
