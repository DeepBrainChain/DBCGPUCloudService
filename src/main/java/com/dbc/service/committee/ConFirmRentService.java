package com.dbc.service.committee;

import com.dbc.dao.DBCWalletDao;
import com.dbc.dao.MyVirtualDao;
import com.dbc.entity.MyVirtualEntity;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *      确认租用机器
 */
@Service
@Slf4j
public class ConFirmRentService {


                        @Autowired
                        DBCWalletDao dbcWalletDao;       // only_key 暂时订单Id

                        @Autowired
                        MyVirtualDao myVirtualDao;


                        public String conFirmRent (String only_key,String machine_id){


                                List<String> params = new ArrayList<String>();
                                params.add("node");
                                params.add("src\\main\\resources\\DeepBrainChain\\confirmRent.js");
                                params.add("--key");
                                params.add(dbcWalletDao.getWif(only_key));      // 获取私钥签名
                                params.add("--id");
                                params.add(machine_id);

                                ProcessBuilder processBuilder = new ProcessBuilder(params);

                                processBuilder.redirectErrorStream(true);
                                String line = "";
                                String Message = "";

                                MyVirtualEntity myVirtualEntity = new MyVirtualEntity();
                                myVirtualEntity.setId(only_key);
                                myVirtualEntity.setOrderStatus("正在使用中");
                                myVirtualDao.update(myVirtualEntity);

                                try {
                                    Process process = processBuilder.start();
                                    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                                    while ((line = br.readLine()) != null) {
                                        Message += line;
                                        log.info(line);
                                    }
                        //                            System.out.println(Message);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return line;
                        }
}
