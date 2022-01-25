package com.dbc.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import javax.script.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JSTest {

                public static void main(String[] args) throws Throwable {

                    List<String> params = new ArrayList<String>();
                    params.add("node");
                    params.add("src\\main\\resources\\DeepBrainChain\\verify_signature.js");
                    params.add("--msg");
                    params.add("kSWt37l46P88KNbG0pr2UZSz4vfSYzc0JIo5dFINBH4rgy1kOmdzyFX");
                    params.add("--addr");
                    params.add("5HL92dTnQrZSJZy7ckDVYVt9mMX3NsjShWsYDquB3eB3yb5R");
                    params.add("--sig");
                    params.add("0x46adbf9faa8cd7c63de85da8246efa2dd368fda720431539c8abd78b6224fc20b6fbed1e2e2d086edc954f2a60bcfe0d575091a01115cc6aca62d82bee822d84");

                    ProcessBuilder processBuilder = new ProcessBuilder(params);
                    processBuilder.redirectErrorStream(true);
                    Process process = processBuilder.start();
                    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line = br.readLine();
                    System.out.println(line);


//                    SendMail.send_common("json@congtu.cloud",           // 收件人
//                            "service@dbchain.ai",                       // 发件人邮箱
//                            "DBC service",                              // 发件人
//                            "您有一台机器等待验证，需要提交hash值",                                 // 标题
//                            "<br><span style ='font-weight:bold';>如果提交hash值超时，将会扣除1000 DBC罚金</span>" +
//                                    "<br><span style ='font-weight:bold';>如果提交的验证信息错误，将会扣除1000 DBC罚金</span>",                        // 文本内容
//                            "dbc service",                                // tame
//                            "https://www.deepbrainchain.org");          // 网站

                    String res;
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    System.out.println(df.format(new Date()));

                    String url = "https://dbchaininfo.congtu.cloud/query/dbc_info";



                    String json = loadJson(url);
                    System.out.println(json);
                    com.alibaba.fastjson.JSONObject jsonObject1 =com.alibaba.fastjson.JSONObject.parseObject(json);
                    com.alibaba.fastjson.JSONObject asd = new com.alibaba.fastjson.JSONObject(JSON.parseObject(jsonObject1.getString("content")));
                    System.out.println(jsonObject1);


                    double fff =18742;
                    System.out.println(fff/100*0.028229/asd.getDouble("dbc_price"));

                    System.out.println(String.format("%.2f", fff/100*0.028229));
                    JSONArray jsonArray =new JSONArray();

                    System.out.println(System.currentTimeMillis());

                    System.out.println((37635-43294)*30000 + System.currentTimeMillis());
                    System.out.println((37635-43294)*30000 + System.currentTimeMillis() + 14400000);

                    long it = (37635-43294)*30000 + System.currentTimeMillis() + 14400000;
                    long is = (37635-43294)*30000 + System.currentTimeMillis();

                    Date date =new Date(it);
                    System.out.println(date);
                    res=df.format(it);
                    System.out.println(res);

                    String rs =df.format(is);
                    System.out.println(res);

                    jsonArray.add(rs+"—"+res);
                    System.out.println(jsonArray);

                    String s="0x137a9";
                    int b =Integer.parseInt(s.replaceAll("^0[x|X]", ""), 16);      //正则表达式去掉0x
                    System.out.println(b);


//                    service advice kiwi become race hope actor magnet believe say output village
                    String a = "4d078fb748f7062b52f0f192838e9b1e9daed20e632c90acc554fbfa818efcfa";
                    List<String> params2 = new ArrayList<String>();
                    params2.add("node");
                    params2.add("src\\main\\resources\\DeepBrainChain\\test_sign.js");
                    params2.add("--key");
                    params2.add("0xb758cfdb54c26aaf82ee5b6d1e1e71f6c03861f4733c6aad600cb3bfe22cd072");

                    ProcessBuilder processBuilder2 = new ProcessBuilder(params2);


                    processBuilder2.redirectErrorStream(true);
                    Process process2 = processBuilder.start();
                    BufferedReader br2 = new BufferedReader(new InputStreamReader(process2.getInputStream()));
//                    System.out.println(br.readLine());
                    JSONObject j = new JSONObject(br.readLine());
                    j.getString("nonce");
                    j.getString("signature");
                    System.out.println(j);
                    System.out.println(j.getString("nonce"));

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
