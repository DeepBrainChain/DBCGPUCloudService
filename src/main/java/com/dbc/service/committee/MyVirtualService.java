package com.dbc.service.committee;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.DBCWalletDao;
import com.dbc.dao.MachineDetailsDao;
import com.dbc.dao.MyVirtualDao;
import com.dbc.entity.MachineDetailsEntity;
import com.dbc.entity.MyVirtualEntity;
import com.dbc.service.SUBDBC.SUBDBCWallet;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MyVirtualService {

                @Autowired
                MachineDetailsDao machineDetailsDao;

                @Autowired
                MyVirtualDao myVirtualDao;

                @Autowired
                DBCWalletDao dbcWalletDao;

                @Autowired
                SUBDBCWallet subdbcWallet;

                // 订单添加

                public boolean ADDMyVirtual(String only_key, String machine_id, String dollar, int day, String count, double dbc, String wallet)  {

//                        List<String> params = new ArrayList<String>();
//                        params.add("node");
//                        params.add("src\\main\\resources\\DeepBrainChain\\rentMachine.js");
//                        params.add("--key");
//                        params.add(dbcWalletDao.getWif(only_key));
//                        params.add("--id");
//                        params.add(machine_id);
//                        params.add("--data");
//                        params.add(String.valueOf(day));
//
//                        ProcessBuilder processBuilder = new ProcessBuilder(params);
//
//                        processBuilder.redirectErrorStream(true);
//                        String line = "";
//                        String Message = "";
//                        try {
//                            Process process = processBuilder.start();
//                            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                            while ((line = br.readLine()) != null) {
//                                Message += line;
//                                log.info(line);
//                            }
//                            //                            System.out.println(Message);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }


                        String url = "https://dbchaininfo.congtu.cloud/query/dbc_info";

                        String json = loadJson(url);
                        JSONObject j =JSONObject.parseObject(json);
                        JSONObject jsonObject = new JSONObject(JSON.parseObject(j.getString("content")));


                        MachineDetailsEntity machineDetailsEntity = machineDetailsDao.FindOne(machine_id);
                        MyVirtualEntity myVirtualEntity = new MyVirtualEntity();
                        myVirtualEntity.setId(machineDetailsEntity.getMachine_id() + wallet);
                        myVirtualEntity.setMachine_id(machineDetailsEntity.getMachine_id());
                        myVirtualEntity.setGpu_type(machineDetailsEntity.getGpu_type());
                        myVirtualEntity.setGpu_num(machineDetailsEntity.getGpu_num());
                        myVirtualEntity.setGpu_mem(machineDetailsEntity.getGpu_mem());
                        myVirtualEntity.setSys_disk(machineDetailsEntity.getSys_disk());
                        myVirtualEntity.setData_disk(machineDetailsEntity.getData_disk());
                        myVirtualEntity.setCpu_type(machineDetailsEntity.getCpu_type());
                        myVirtualEntity.setCpu_core_num(machineDetailsEntity.getCpu_core_num());
                        myVirtualEntity.setCpu_rate(machineDetailsEntity.getCpu_rate());
                        myVirtualEntity.setMem_num(machineDetailsEntity.getMem_num());
                        myVirtualEntity.setUpload_net(machineDetailsEntity.getUpload_net());
                        myVirtualEntity.setDownload_net(machineDetailsEntity.getDownload_net());
                        myVirtualEntity.setTelecom_operators(machineDetailsEntity.getTelecom_operators());
                        myVirtualEntity.setCountry(machineDetailsEntity.getCountry());
                        myVirtualEntity.setCity(machineDetailsEntity.getCity());
                        myVirtualEntity.setCalc_point(machineDetailsEntity.getCalc_point());
                        myVirtualEntity.setGpu_price(String.format("%.2f", machineDetailsEntity.getCalc_point()/100*0.028229));
                        myVirtualEntity.setDbc_price(jsonObject.getDouble("dbc_price"));
                        myVirtualEntity.setDollar(dollar);
                        myVirtualEntity.setDay(day);
                        myVirtualEntity.setCount(count);
                        myVirtualEntity.setDbc(dbc);
                        myVirtualEntity.setWallet(wallet);
                        myVirtualEntity.setOrderStatus("待确认支付");
                        myVirtualEntity.setTime(new Date());
                        myVirtualDao.addInfo(myVirtualEntity);


                        return true;


                }

                public String chain_lease (String only_key,String machine_id,String wallet, int day) throws Exception {                      // 链上租用机器

                        float balance = subdbcWallet.getBanlance("wss://infotest.dbcwallet.io",wallet);
                        String line = "";
                        String Message = "";
                        if (balance > 0.0) {

                            List<String> params = new ArrayList<String>();
                            params.add("node");
                            params.add("src\\main\\resources\\DeepBrainChain\\rentMachine.js");
                            params.add("--key");
                            params.add(dbcWalletDao.getWif(only_key));
                            params.add("--id");
                            params.add(machine_id);
                            params.add("--data");
                            params.add(String.valueOf(day));

                            ProcessBuilder processBuilder = new ProcessBuilder(params);

                            processBuilder.redirectErrorStream(true);

                            Process process = processBuilder.start();
                            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            while ((line = br.readLine()) != null) {
                                Message += line;
                                log.info(line);
                            }

                        }
                        assert false;
                        boolean status = Message.contains("ExtrinsicSuccess");
                        if (status){
                            return "租用成功";
                        }else {
                            return "租用失败";
                        }

                }

                public static String loadJson (String url) {

                            StringBuilder json = new StringBuilder();

                            try {

                                URL urlObject = new URL(url);

                                URLConnection uc = urlObject.openConnection();

                                BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));

                                String inputLine = null;

                                while ( (inputLine = in.readLine()) != null) {

                                    json.append(inputLine);

                                }

                                in.close();

                            } catch (MalformedURLException e) {

                                e.printStackTrace();

                            } catch (IOException e) {

                                e.printStackTrace();

                            }

                            return json.toString();

                }

}
