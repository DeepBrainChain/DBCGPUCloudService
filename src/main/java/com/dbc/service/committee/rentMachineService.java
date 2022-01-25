package com.dbc.service.committee;

import com.dbc.dao.DBCWalletDao;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *      提交交易：机器ID 和 租用天数
 */
@Service
@Slf4j
public class rentMachineService {

                    @Autowired
                    DBCWalletDao dbcWalletDao;

                    /**
                     *
                     * @return   Id 机器ID  data 租用天数
                     */
                    public String rentMachine(String only_key,String machine_id,String day){


                        List<String> params = new ArrayList<String>();
                        params.add("node");
                        params.add("src\\main\\resources\\DeepBrainChain\\rentMachine.js");
                        params.add("--key");
                        params.add(dbcWalletDao.getWif(only_key));
                        params.add("--id");
                        params.add(machine_id);
                        params.add("--data");
                        params.add(day);       // 天数

                        ProcessBuilder processBuilder = new ProcessBuilder(params);

                        processBuilder.redirectErrorStream(true);
                        String line = "";
                        String Message = "";
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
