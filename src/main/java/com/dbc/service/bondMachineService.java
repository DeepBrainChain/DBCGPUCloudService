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
public class bondMachineService {


                    public List<String> bond(){

                        List<String> params = new ArrayList<String>();
                        params.add("node");
                        params.add("src\\main\\resources\\DeepBrainChain\\sign_txs.js");
                        params.add("--port");
                        params.add("wss://infotest.dbcwallet.io");
                        params.add("--module");
                        params.add("onlineProfile");
                        params.add("--func");
                        params.add("bondMachine");
                        params.add("--js");
                        params.add("src\\main\\resources\\DeepBrainChain\\types.json");
                        params.add("--key");
                        params.add("0xe5be9a5092b81bca64be81d212e7f2f9eba183bb7a90954f7b76361f6edb5c0a");
                        params.add("5FHneW46xGXgs5mUiveU4sbTyGBzmstUspZC92UhjJM694ty");
                        params.add("2gfpp3MAB4Aq2ZPEU72neZTVcZkbzDzX96op9d3fvi3");

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
                            System.out.println(Message);

                            System.out.println("exitCode = "+exitCode);
                            System.out.println(list);

//                        boolean status = Message.contains("ExtrinsicFailed");
//                        boolean Final = Message.contains("Finalized");
//
//                        if(status && Final){
//                            return list;
//                        }else{
//                            return list;
//                        }

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
