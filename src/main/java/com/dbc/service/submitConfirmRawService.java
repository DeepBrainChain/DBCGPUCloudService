package com.dbc.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class submitConfirmRawService {

                    public List<String> submitRaw(){

                        List<String> params = new ArrayList<String>();
                        params.add("node");
                        params.add("src\\main\\resources\\DeepBrainChain\\sign_txs.js");
                        params.add("--port");
                        params.add("wss://infotest.dbcwallet.io");
                        params.add("--module");
                        params.add("onlineProfile");
                        params.add("--func");
                        params.add("stakerChangeMachineInfo");
                        params.add("--js");
                        params.add("src\\main\\resources\\DeepBrainChain\\types.json");
                        params.add("--key");
                        params.add("0xe5be9a5092b81bca64be81d212e7f2f9eba183bb7a90954f7b76361f6edb5c0a");
                        params.add("0xMdgdrgdg45dgdvdvffv");       //machine_id
                        params.add("0");                            //GPU型号
                        params.add("0");                           //GPU数量
                        params.add("0");                            // CUDA core数量
                        params.add("0");                            // GPU显存
                        params.add("0");                            // 算力值
                        params.add("0");                            // 硬盘
                        params.add("0");                             // CPU型号
                        params.add("0");                             // CPU内核数
                        params.add("0");                              // CPU频率
                        params.add("0");                              // 内存数
                        params.add("0");                              // 委员会自己的随机字符串
                        params.add("不");                              // 委员会是否支持该机器上线

                        ProcessBuilder processBuilder = new ProcessBuilder(params);

                        processBuilder.redirectErrorStream(true);
                        try {
                            Process process = processBuilder.start();
                            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String line;
                            String Message = "";

                            List<String> list = new ArrayList<String>();
                            String s = null;
                            String e = null;
                            while ((line = br.readLine()) != null) {
                                System.out.println(line);
                                Message += line;

                                if(line.startsWith("{")){
                                    JSONObject object = new JSONObject(line);
                                    //                                        System.out.println(object);
                                    if(object.has("Tx_status")){
                                        s = object.getString("Tx_status");
                                        list.add(s);
                                    }
                                    if(object.has("Event")){
                                        e = object.getString("func");
                                        list.add(e);

                                    }
                                }

                            }
                            int exitCode = process.waitFor();

                            System.out.println("exitCode = "+exitCode);

                            System.out.println(list);

                            return list;



                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return params;
                    }
}
