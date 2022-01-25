package com.dbc.service.committee;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.DBCWalletDao;
import com.dbc.dao.TaskIdDao;
import com.dbc.entity.TaskIdEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class VerifierCreateVMService {

                    @Autowired
                    DBCWalletDao dbcWalletDao;

                    @Autowired
                    TaskIdDao taskIdDao;

                    @Value("${clientUrl}")
                	private   String clientUrl;
                    // 请求头
                    public HttpEntity<Map<String,Object>> PostJson (JSONObject jsonMap){

                        HttpHeaders httpHeaders =new HttpHeaders();

                        MediaType type =MediaType.parseMediaType("application/json;charset=UTF-8");

                        httpHeaders.setContentType(type);

                        HttpEntity<Map<String,Object>> httpEntity =new HttpEntity<>(jsonMap,httpHeaders);

                        return httpEntity;
                    }


                    public JSONObject Verifier_VM (String machine_id,String nonce,String sign,String wallet) throws IOException {

                            try {

                                    String url = clientUrl+"/api/v1/tasks/start";

                                    RestTemplate restTemplate = new RestTemplate();

                                    List<String> list = new ArrayList<>();
                                    list.add(machine_id);

                                    JSONObject jsonArray = new JSONObject();
                                    jsonArray.put("ssh_port","5682");   // 虚拟机开放端口范围
                                    jsonArray.put("image_name", "ubuntu.qcow2");  // 镜像
                                    jsonArray.put("gpu_count", "0");   // 虚拟机GPU数
                                    jsonArray.put("cpu_cores", "40");   // 虚拟机CPU数
                                    jsonArray.put("mem_size", "32");
                                    jsonArray.put("disk_size", "10");
                                    jsonArray.put("vnc_port", "5901");
                                    jsonArray.put("vm_xml", "");
                                    jsonArray.put("vm_xml_url", "");

                                    JSONObject jsonMap = new JSONObject();

                                    jsonMap.put("peer_nodes_list", list);
                                    jsonMap.put("additional", jsonArray);
                                    jsonMap.put("sign", sign);
                                    jsonMap.put("nonce", nonce);
                                    jsonMap.put("wallet", wallet);
                                    jsonMap.put("session_id","");
                                    jsonMap.put("session_id_sign","");

                                    System.out.println(jsonMap);


                                    ResponseEntity<JSONObject> response = restTemplate.postForEntity
                                            (
                                                    url,
                                                    PostJson(jsonMap),
                                                    JSONObject.class
                                            );

                                    JSONObject jsonObject = response.getBody();

                                    assert jsonObject != null;
                                    JSONObject taskId = JSON.parseObject(JSON.toJSONString(jsonObject.get("message")));

                                    TaskIdEntity taskIdEntity = new TaskIdEntity();
                                    taskIdEntity.setMachine_id(machine_id);
                                    taskIdEntity.setTask_id(taskId.getString("task_id"));
                                    taskIdEntity.setCreate_time(taskId.getDate("create_time"));
                                    taskIdEntity.setStatus(taskId.getString("status"));
                                    taskIdDao.Save_TaskId(taskIdEntity);


                                    return response.getBody();

                            } catch (Exception e) {

                                log.info("创建失败");

                                log.info(String.valueOf(e));

                                JSONObject jb = new JSONObject();
                                jb.put("状态",e);

                                return jb;
                            }
                    }


                    /**
                     * 获取虚拟机详情
                     */
                    public JSONObject Tasks (String machine_id,String nonce,String sign,String wallet) {

                        try {

                            String task_id = taskIdDao.Query_id(machine_id);

                            String url = clientUrl+"/api/v1/tasks" + task_id;

                            RestTemplate restTemplate = new RestTemplate();

                            List<String> list = new ArrayList<>();
                            list.add(machine_id);

                            JSONObject jsonArray = new JSONObject();

                            JSONObject jsonMap = new JSONObject();

                            jsonMap.put("peer_nodes_list", list);
                            jsonMap.put("additional", jsonArray);
                            jsonMap.put("sign", sign);
                            jsonMap.put("nonce", nonce);
                            jsonMap.put("wallet", wallet);
                            jsonMap.put("session_id","");
                            jsonMap.put("session_id_sign","");

                            System.out.println(jsonMap);


                            ResponseEntity<JSONObject> response = restTemplate.postForEntity
                                    (
                                            url,
                                            PostJson(jsonMap),
                                            JSONObject.class
                                    );

                            return response.getBody();


                        } catch (Exception e) {

                            log.info("创建失败");

                            log.info(String.valueOf(e));

                            JSONObject jb = new JSONObject();
                            jb.put("状态",e);

                            return jb;
                        }

                    }


                    /**
                     * 重启 虚拟机
                     * @param machine_id
                     * @param nonce
                     * @param sign
                     * @param wallet
                     * @return
                     */
                    public JSONObject Restart_VM (String machine_id,String nonce,String sign,String wallet,String task_id) {

                        try {

                            String url = clientUrl+"/api/v1/tasks/restart/" + task_id;
                            log.info(url);

                            RestTemplate restTemplate = new RestTemplate();

                            List<String> list = new ArrayList<>();
                            list.add(machine_id);

                            JSONObject jsonArray = new JSONObject();

                            JSONObject jsonMap = new JSONObject();

                            jsonMap.put("peer_nodes_list", list);
                            jsonMap.put("additional", jsonArray);
                            jsonMap.put("sign", sign);
                            jsonMap.put("nonce", nonce);
                            jsonMap.put("wallet", wallet);
                            jsonMap.put("session_id","");
                            jsonMap.put("session_id_sign","");

                            System.out.println(jsonMap);


                            ResponseEntity<JSONObject> response = restTemplate.postForEntity
                                    (
                                            url,
                                            PostJson(jsonMap),
                                            JSONObject.class
                                    );

                            return response.getBody();

                        } catch (Exception e) {

                            log.info("创建失败");

                            log.info(String.valueOf(e));

                            JSONObject jb = new JSONObject();
                            jb.put("状态",e);

                            return jb;
                        }
                    }





}
