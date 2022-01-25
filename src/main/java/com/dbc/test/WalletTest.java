package com.dbc.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class WalletTest {

            public static void main(String[] args) throws IOException {


                List<String> params = new ArrayList<String>();
                params.add("node");
                params.add("src\\main\\resources\\DeepBrainChain\\test_createWallet.js");
                ProcessBuilder processBuilder = new ProcessBuilder(params);

                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                System.out.println(br.readLine());

                Map<String,Object> paraMap = new HashMap<String ,Object>();
                paraMap.put("br",br.readLine());
                String jsonString = JSONObject.toJSONString(paraMap);
                System.out.println(jsonString);

            }
}
