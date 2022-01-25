package com.dbc.service.committee;

import com.dbc.dao.DBCWalletDao;
import com.dbc.dao.MyVirtualDao;
import com.dbc.entity.MachineDetailsEntity;
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
 *      机器续费
 */
@Service
@Slf4j
public class RenewalService {

                @Autowired
                MyVirtualDao myVirtualDao;

                @Autowired
                DBCWalletDao dbcWalletDao;


                public String Renew (String only_key, String machine_id, int add_day , double dbc) {

                            List<String> params = new ArrayList<String>();
                            params.add("node");
                            params.add("src\\main\\resources\\DeepBrainChain\\reletMachine.js");
                            params.add("--key");
                            params.add(dbcWalletDao.getWif(only_key));
                            params.add("--id");
                            params.add(machine_id);
                            params.add("--data");
                            params.add(String.valueOf(add_day));       // 天数

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
//                            MachineDetailsEntity machineDetailsEntity = machineDetailsDao.FindOne(machine_id);
                            MyVirtualEntity virtualEntity = myVirtualDao.Query(only_key);
                            MyVirtualEntity myVirtualEntity = new MyVirtualEntity();
                            myVirtualEntity.setId(only_key);
                            myVirtualEntity.setDay(virtualEntity.getDay() + add_day);
                            myVirtualEntity.setDbc(virtualEntity.getDbc() + dbc);
                            myVirtualDao.renew(myVirtualEntity);

                            return "Success";
                }
}
