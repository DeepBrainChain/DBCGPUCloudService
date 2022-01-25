package com.dbc.service.committee;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.DBCWalletDao;
import com.dbc.dao.TaskIdDao;
import com.dbc.entity.TaskIdEntity;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.ec.ScaleYNegateXPointMap;

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

/**
 *     创建虚拟机
 */

@Service
@Slf4j
public class CreateVirtualMachineService {

                @Autowired
                DBCWalletDao dbcWalletDao;

                @Autowired
                TaskIdDao taskIdDao;

                @Value("${clientUrl}")
            	private   String clientUrl;
                // 请求头
                public HttpEntity<Map<String,Object>> PostJson (JSONObject jsonMap){

                    HttpHeaders  httpHeaders =new HttpHeaders();

                    MediaType type =MediaType.parseMediaType("application/json;charset=UTF-8");

                    httpHeaders.setContentType(type);

                    HttpEntity<Map<String,Object>> httpEntity =new HttpEntity<>(jsonMap,httpHeaders);

                    return httpEntity;
                }

                /**
                 *  post 请求 请求参数为json
                 *  String id 获取私钥
                 */
                public JSONObject Creat_VM (String only_key,String machine_id,String ssh_port,String gpu_count,String cpu_cores,String mem_rate,
                                            String nonce, String sign , String wallet) throws IOException {

                    List<String> param = new ArrayList<String>();
                    param.add("node");
                    param.add("src\\main\\resources\\DeepBrainChain\\verify_signature.js");
                    param.add("--msg");
                    param.add(nonce);
                    param.add("--addr");
                    param.add(wallet);
                    param.add("--sig");
                    param.add(sign);

                    ProcessBuilder Builder = new ProcessBuilder(param);
                    Builder.redirectErrorStream(true);
                    Process p = Builder.start();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line = bufferedReader.readLine();

                    if (line.startsWith("true")) {

                        try {

                            String url = clientUrl+"/api/v1/tasks/start";

                            RestTemplate restTemplate = new RestTemplate();

                            List<String> params = new ArrayList<String>();
                            params.add("node");
                            params.add("src\\main\\resources\\DeepBrainChain\\test_sign.js");
                            params.add("--key");
                            params.add(dbcWalletDao.getWif(only_key));
                            System.out.println(params);
                            ProcessBuilder processBuilder = new ProcessBuilder(params);

                            processBuilder.redirectErrorStream(true);
                            Process process = processBuilder.start();
                            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            JSONObject j = new JSONObject(br.read());


                            List<String> list = new ArrayList<>();
                            list.add(machine_id);

                            JSONObject jsonArray = new JSONObject();
                            jsonArray.put("ssh_port", ssh_port);   // 虚拟机开放端口范围
                            jsonArray.put("image_name", "ubuntu.qcow2");  // 镜像
                            jsonArray.put("gpu_count", gpu_count);   // 虚拟机GPU数
                            jsonArray.put("cpu_cores", cpu_cores);   // 虚拟机CPU数
                            jsonArray.put("mem_rate", mem_rate);

                            JSONObject jsonMap = new JSONObject();

                            jsonMap.put("peer_nodes_list", list);
                            jsonMap.put("additional", jsonArray);
                            jsonMap.put("sign", j.getString("signature"));
                            jsonMap.put("nonce", j.getString("nonce"));
                            jsonMap.put("wallet", dbcWalletDao.getWallet(only_key));

                            System.out.println(jsonMap);


                            ResponseEntity<JSONObject> response = restTemplate.postForEntity
                                    (
                                            url,
                                            PostJson(jsonMap),
                                            JSONObject.class
                                    );

                            JSONObject jsonObject = response.getBody();
//                            System.out.println(jsonObject);
//                            assert jsonObject != null;
//                            System.out.println(jsonObject.getString("result_message"));
//                            System.out.println(jsonObject.getString("task_id"));
//                            assert jsonObject != null;
//                                        if (jsonObject.getInteger("result_code") == 1) {

                            JSONObject taskId = JSON.parseObject(JSON.toJSONString(jsonObject.get("message")));
                            System.out.println(taskId);
                            TaskIdEntity taskIdEntity = new TaskIdEntity();
                            taskIdEntity.setMachine_id(machine_id);
                            taskIdEntity.setTask_id(taskId.getString("task_id"));
                            taskIdEntity.setCreate_time(taskId.getDate("create_time"));
                            taskIdEntity.setStatus(taskId.getString("status"));
                            taskIdDao.Save_TaskId(taskIdEntity);
//                                        }


                            return response.getBody();

                        } catch (Exception e) {

                            log.info("创建失败");

                            log.info(String.valueOf(e));

                            JSONObject jb = new JSONObject();
                            jb.put("状态",e);

                            return jb;
                        }
                    }else {

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("状态","验证失败");
                        return jsonObject;
                    }
                }

                /**
                 *     查看虚拟机详情信息  details
                 */
                public String  VM_details (String machine_id,String only_key) throws IOException {

                    try {

                                String taskId = taskIdDao.Query_id(machine_id);
                                if (taskId != null) {
                                    String url = clientUrl+"/api/v1/tasks/" + taskId;


                                    RestTemplate restTemplate = new RestTemplate();

                                    List<String> params = new ArrayList<String>();
                                    params.add("node");
                                    params.add("src\\main\\resources\\DeepBrainChain\\test_sign.js");
                                    params.add("--key");
                                    params.add(dbcWalletDao.getWif(only_key));
                                    System.out.println(params);
                                    ProcessBuilder processBuilder = new ProcessBuilder(params);

                                    processBuilder.redirectErrorStream(true);
                                    Process process = processBuilder.start();
                                    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                                    JSONObject j = new JSONObject(br.read());


                                    List<String> list = new ArrayList<>();
                                    list.add(machine_id);
                                    JSONObject jsonArray = new JSONObject();

                                    JSONObject jsonMap = new JSONObject();

                                    jsonMap.put("peer_nodes_list", list);
                                    jsonMap.put("additional", jsonArray);
                                    jsonMap.put("sign", j.getString("signature"));
                                    jsonMap.put("nonce", j.getString("nonce"));
                                    jsonMap.put("wallet", dbcWalletDao.getWallet(only_key));

                                    System.out.println(jsonMap);


                                    ResponseEntity<String> response = restTemplate.postForEntity
                                            (
                                                    url,
                                                    PostJson(jsonMap),
                                                    String.class
                                            );

//                                            if (response.getBody().equals(null)){
//
//                                                return response.getBody();
//                                            }else {
//
//                                                return "Creating";
//
//
//                                            }

                                    return response.getBody();


                                }
                                    return "失败";

                                }catch (Exception e){

                                    log.info("未找到 task_id");

                                    log.info(String.valueOf(e));

                                    return "Error";
                                }


                }

                /**
                 *  重启虚拟机
                 */
                public String VM_restart (String machine_id ,String only_key )  {

                        try {

                                String taskId = taskIdDao.Query_id(machine_id);

                                String url = clientUrl+"api/v1/tasks/" + taskId + "/restart";  // url

                                RestTemplate restTemplate = new RestTemplate();

                                List<String> params = new ArrayList<String>();
                                params.add("node");
                                params.add("src\\main\\resources\\DeepBrainChain\\test_sign.js");
                                params.add("--key");
                                params.add(dbcWalletDao.getWif(only_key));
                                System.out.println(params);
                                ProcessBuilder processBuilder = new ProcessBuilder(params);

                                processBuilder.redirectErrorStream(true);
                                Process process = processBuilder.start();
                                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                                JSONObject j = new JSONObject(br.read());


                                List<String> list = new ArrayList<>();
                                list.add(machine_id);
                                JSONObject jsonArray = new JSONObject();

                                JSONObject jsonMap = new JSONObject();

                                jsonMap.put("peer_nodes_list", list);
                                jsonMap.put("additional", jsonArray);
                                jsonMap.put("sign", j.getString("signature"));
                                jsonMap.put("nonce", j.getString("nonce"));
                                jsonMap.put("wallet", dbcWalletDao.getWallet(only_key));

                                System.out.println(jsonMap);


                                ResponseEntity<String> response = restTemplate.postForEntity
                                        (
                                                url,
                                                PostJson(jsonMap),
                                                String.class
                                        );

                                return response.getBody();

                        }catch (Exception e){
                            return "ERROR 重启失败";


                        }


                }

                // 重置密码

                    // 修改虚拟机参数

}
