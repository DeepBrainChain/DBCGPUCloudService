package com.dbc.service.DBService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.*;
import com.dbc.entity.ChillListEntity;
import com.dbc.entity.LCOpsEntity;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *  验证人可以验证的时间
 */

@Service
@Slf4j
public class LCOpsService {


                @Autowired
                LCOpsDao lcOpsDao;

                @Autowired
                CommitteeListDao committeeListDao;

                @Autowired
                VerificationDao verificationDao;

                @Autowired
                ChillDao chillDao;

                @Autowired
                GetBlockHashDao getBlockHashDao;

                @Value("${chainUrl}")
            	private   String chainUrl;
                public String LCO() throws URISyntaxException {

                WebSocketClient client = new WebSocketClient(new URI(chainUrl),new Draft_6455()) {
                    private List<String>  comm = new ArrayList<>();
                    private List<String> machine_id = new ArrayList<>();
                    private int count;

                    /**
                     * 建立连接调用
                     *
                     * @param serverHandshake
                     */
                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {

//                        Verification verification = verificationDao.getVer(1);
//                        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(verification));
//                        JSONArray b = JSON.parseArray(data.getString("booked_machine"));
//                        JSONArray mach = JSON.parseArray(b.toJSONString());
//
//                        if(mach != null && mach.size() > 0) {
//                            for (int i = 0; i < mach.size(); i++) {
//                                String id = mach.get(i).toString();
//                                CommitteeListEntity machineCommitteeLists= committeeListDao.getComm(id);
//                                JSONObject datas = JSONObject.parseObject(JSONObject.toJSONString(machineCommitteeLists));
//
//                                JSONArray comm = JSON.parseArray(datas.getString ("booked_committee"));
//                                JSONArray bo = JSON.parseArray(comm.toJSONString());
//                                if(bo != null && bo.size() > 0) {
//                                    for (int j = 0; j < bo.size(); j++) {
//                                        bo.get(j).toString();
//                                    }
//                                }
//
//
//                                if(bo != null && bo.size() > 0) {
//                                    for (int f = 0; f < bo.size(); f++) {
//                                        List<String> list = new ArrayList<>();
//                                        String jsonArray = datas.getString("machineId");
//                                        String s = bo.get(f).toString();
//                                        list.add(s);
//                                        list.add(jsonArray);
//                                        JSONObject jsonObject = new JSONObject();
//                                        jsonObject.put("jsonrpc","2.0");
//                                        jsonObject.put("id",1);
//                                        jsonObject.put("method","leaseCommittee_getCommitteeOps");//                                        jsonObject.put("params",list);
//                                        System.out.println("jsonObject1：" + jsonObject.toString());
//
//                                        send(jsonObject.toString());
//
//
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

                        Verification verification = verificationDao.getVer(1);
                        JSONObject jsonObjects = JSONObject.parseObject(JSONObject.toJSONString(verification));
                        JSONArray jsonArray = JSON.parseArray(jsonObjects.getString("booked_machine"));
                        JSONArray book = JSON.parseArray(jsonArray.toJSONString());


                        if(bo != null && bo.size() > 0) {
                            for (int i = 0; i < bo.size(); i++) {
                                String mid = bo.get(i).toString();

                                for (int f = 0; f < book.size(); f++)   {
                                    String book_machine = book.get(f).toString();
                                    List<String> list = new ArrayList<>();
                                    list.add(mid);
                                    list.add(book_machine);

                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("jsonrpc", "2.0");
                                    jsonObject.put("id", 1);
                                    jsonObject.put("method", "onlineCommittee_getCommitteeOps");
                                    jsonObject.put("params", list);
                                    System.out.println("jsonObject1：" + jsonObject.toString());
                                    comm.add(mid);
                                    machine_id.add(book_machine);

                                    send(jsonObject.toString());
                                }
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
                        JSONObject data = new JSONObject(JSON.parseObject(s));
                        LCOpsEntity lcOps = new LCOpsEntity();
                        JSONObject result= new JSONObject(JSON.parseObject(data.getString("result")));

//                        JSONObject machine_info = new JSONObject(JSON.parseObject(result.getString("machineInfo")));

                        String res;
                        String rs;
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



                        JSONArray jsonArray = JSON.parseArray(result.getString("verifyTime"));
                        JSONArray verify = JSON.parseArray(jsonArray.toJSONString());
                        JSONArray jsonArray1 = new JSONArray();
                        if(verify != null && verify.size() > 0) {
                            for (int i = 0; i < verify.size(); i++) {

                                long is = (verify.getInteger(i)-getBlockHashDao.FindHash(1))*30000 + System.currentTimeMillis(); // 开始时间
                                long it = (verify.getInteger(i)-getBlockHashDao.FindHash(1))*30000 + System.currentTimeMillis() + 14400000; // 结束时间

                                res = df.format(is);
                                rs = df.format(it);
                                System.out.println(res);

//                                res =df.format(is);
//                                System.out.println(res);

//                                verify.get(i).toString();
                                jsonArray1.add(res+"—"+rs);
                            }
                        }

                        JSONArray b = JSON.parseArray(result.getString("verifyTime"));
                        JSONArray bo = JSON.parseArray(b.toJSONString());
                        if(bo != null && bo.size() > 0) {
                            for (int i = 0; i < bo.size(); i++) {
                                bo.get(i).toString();
                            }
                        }


                        lcOps.setMid(comm.get(count));
                        lcOps.setBooked_committee(machine_id.get(count));
                        lcOps.setId(comm.get(count)+machine_id.get(count));
                        lcOps.setVerify_time_high(bo);
//                        lcOps.setCalc_point(machine_info.getString("calc_point"));
//                        lcOps.setCpu_core_num(machine_info.getString("cpu_core_num"));
//                        lcOps.setCpu_rate(machine_info.getString("cpu_rate"));
//                        lcOps.setCpu_type(machine_info.getString("cpu_type"));
//                        lcOps.setCuda_core(machine_info.getString("cuda_core"));
//                        lcOps.setData_disk(machine_info.getString("data_disk"));
//                        lcOps.setGpu_mem(machine_info.getString("gpu_mem"));
//                        lcOps.setGpu_num(machine_info.getString("gpu_num"));
//                        lcOps.setGpu_type(machine_info.getString("gpu_type"));
//                        lcOps.setIs_support(machine_info.getString("is_support"));
//                        lcOps.setMachine_id(machine_info.getString("machine_id"));
//                        lcOps.setMem_num(machine_info.getString("mem_num"));
//                        lcOps.setRand_str(machine_info.getString("rand_str"));
//                        lcOps.setSys_disk(machine_info.getString("sys_disk"));
//                        lcOps.setConfirm_time(result.getString("confirmTime"));
//                        lcOps.setHash_time(result.getString("hashTime"));
//                        lcOps.setMachine_status(result.getString("machineStatus"));
                        lcOps.setVerify_time(jsonArray1);
                        lcOpsDao.AddLCO(lcOps);


                        this.count+=1;

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
