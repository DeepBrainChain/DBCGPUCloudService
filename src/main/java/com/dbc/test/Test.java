package com.dbc.test;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class Test {

//                public static <object> void main(String[] args) {
//
//                    List<String> params = new ArrayList<String>();
//                    params.add("node");
//                    params.add("src\\main\\resources\\DeepBrainChain\\sign_txs.js");
//                    params.add("--port");
//                    params.add("wss://infotest.dbcwallet.io");
//                    params.add("--module");
//                    params.add("leaseCommittee");
//                    params.add("--func");
//                    params.add("submitConfirmRaw");
//                    params.add("--js");
//                    params.add("src\\main\\resources\\DeepBrainChain\\types.json");
//                    params.add("--key");
//                    params.add("0xe5be9a5092b81bca64be81d212e7f2f9eba183bb7a90954f7b76361f6edb5c0a");
//
//
//
//
//                    params.add("0xMdgdrgdg45dgdvdvffv");       //machine_id
////                    params.add("0");                            //GPU型号
////                    params.add("0");                           //GPU数量
////                    params.add("0");                            // CUDA core数量
////                    params.add("0");                            // GPU显存
////                    params.add("0");                            // 算力值
////                    params.add("0");                            // 硬盘
////                    params.add("0");                             // CPU型号
////                    params.add("0");                             // CPU内核数
////                    params.add("0");                              // CPU频率
////                    params.add("0");                              // 内存数
////                    params.add("0");                              // 委员会自己的随机字符串
////                    params.add("不");                              // 委员会是否支持该机器上线
//
//                    ProcessBuilder processBuilder = new ProcessBuilder(params);
//
//                    processBuilder.redirectErrorStream(true);
//                    try {
//                        Process process = processBuilder.start();
//                        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                        String line;
//                        String Message = "";
//
//                        List<String> list = new ArrayList<String>();
//                        String s = null;
//                        String e = null;
//                        while ((line = br.readLine()) != null) {
//                            System.out.println(line);
//                            Message += line;
//
//                            if(line.startsWith("{")){
//                                JSONObject object = new JSONObject(line);
////                                        System.out.println(object);
//                                if(object.has("Tx_status")){
//                                    s = object.getString("Tx_status");
//                                    list.add(s);
//                                }
//                                if(object.has("Event")){
//                                    e = object.getString("func");
//                                    list.add(e);
//
//                                }
//                            }
//
//                        }
//                        int exitCode = process.waitFor();
//
//                        System.out.println(list);
//
//                        System.out.println("exitCode = "+exitCode);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }

    public final static String BAIDU_MAP_AK = "S3b9buXfNthnsH1GOF2gI2HIOufDTWhs";
    int a[] =new int[] {};


    public static void main(String[] args) throws Throwable {
        byte[] b = new byte[] {56, 101, -111, 97, 97, -72, 61, 35, 105, 82, 22, 103, -35, -68, -90, -124, -68, 7, -71, 45, 58, 94, 98, 91, 106, 89, -118, 105, 108, -29, 40, 123, 112, -46, -67, 95, -23, -107, 66, 105, 56, -58, -80, 56, 1, -96, 10, -92, -23, -31, -108, -53, 42, 112, 39, -99, 99, -105, -34, 66, -120, -79, -57, -47};


//        String s1 = new String(b);
//        System.out.println(s1);

        String encoded = Base64.getEncoder().encodeToString(b);
        System.out.println(encoded);

        double d =114258d/10000;
        System.out.println(d);





//
       // -> "[97, 98, 99]"
        String s = "5CtUnQK47PjiHZaXdCqPkDRPqi2EasG3EbnbqYq5xtYUW14Q";

        byte[] utf8 = s.getBytes(StandardCharsets.UTF_8);
        System.out.println(Arrays.toString(utf8));

//        String ss = "[[53,70,72,110,101,87,52,54,120,71,88,103,115,53,109,85,105,118,101,85,52,115,98,84,121,71,66,122,109,115,116,85,115,112,90,67,57,50,85,104,106,74,77,54,57,52,116,121]]"; //  [[u8], [u8]]
////        List<Byte> c= JSON.parseArray(b,Byte.class);
////        System.out.println(c);
//        JSONArray a = JSON.parseArray(ss);
//        System.out.println(a.get(0));

//        String str = "[255,12,87,183,232,34,64,121,182,23]";
//        Gson gson = new Gson();
//        byte[] parsed = gson.fromJson(str, byte[].class);

//        byte[] c= JSONArray.parseObject(str,byte[].class);
//        for (byte b1 : c) {
//            System.out.println(b1);
//        }


//        SendMail.send_common(email, email_send_info, team, "Email from "+platform_name, text2,team,website);


//        String baseUrl="http://111.44.254.162:41107/api/v1/mining_nodes";//username=? password=?
//        HttpClient httpclient=new DefaultHttpClient();
//        //以请求的url地址创建httppost请求对象
//        HttpPost httppost=new HttpPost(baseUrl);
//
//        //NameValuePair 表示以类的形式保存提交的键值对
//        NameValuePair pair1=new BasicNameValuePair("machine_id", "6a3460fd97b5ce1a9e0f05c737e899edeec8afd48f1fffb60a814d45bee0a377");
//       // NameValuePair pair2=new BasicNameValuePair("password", "admin");
//        //集合的目的就是存储需要向服务器提交的key-value对的集合
//        List<NameValuePair> listPair=new ArrayList<NameValuePair>();
//        listPair.add(pair1);
//       // listPair.add(pair2);
//        //HttpEntity 封装消息的对象 可以发送和接受服务器的消息  可以通过客户端请求或者是服务器端的响应获取其对象
//        HttpEntity entity=new UrlEncodedFormEntity(listPair);//创建httpEntity对象
//        httppost.setEntity(entity);//将发送消息的载体对象封装到httppost对象中
//
//        HttpResponse response=httpclient.execute(httppost);
//        int responseCode=response.getStatusLine().getStatusCode();
//        if(responseCode==200){
//            //得到服务器响应的消息对象
//            HttpEntity httpentity=response.getEntity();
//            System.out.println("服务器响应结果是:"+ EntityUtils.toString(httpentity, "utf-8"));
//        }else{
//            System.out.println("响应失败!");
//        }
        // lat 39.97646
        //log 116.3039

        getAddressInfoByLngAndLat("104.0667","30.6667");



    }

    public static JSONObject getAddressInfoByLngAndLat(String longitude,String latitude){
        JSONObject obj = new JSONObject();
        String location=latitude+","+longitude;
        //百度url  coordtype :bd09ll（百度经纬度坐标）、bd09mc（百度米制坐标）、gcj02ll（国测局经纬度坐标，仅限中国）、wgs84ll（ GPS经纬度）
        String url ="http://api.map.baidu.com/reverse_geocoding/v3/?ak="+BAIDU_MAP_AK+"&output=json&coordtype=wgs84ll&location="+location;
//        String url ="https://api.map.baidu.com/reverse_geocoding/v3/?ak=jQc7i76SLm2k5j54z5y6ppjWjhb0nlhC&output=json&location="+ location;
        System.out.println(url);
        try {
            String json = loadJSON(url);
            obj = JSONObject.parseObject(json);
            System.out.println(obj.toString());
            // status:0 成功
            String success="0";
            String status = String.valueOf(obj.get("status"));
            if(success.equals(status)){
                String result = String.valueOf(obj.get("result"));
                JSONObject resultObj = JSONObject.parseObject(result);
                String addressComponent = String.valueOf(resultObj.get("addressComponent"));
                //JSON字符串转换成Java对象
                // AddressComponent addressComponentInfo = JSONObject.parseObject(addressComponent, AddressComponent.class);
                System.out.println("addressComponentInfo:"+addressComponent);
            }
        } catch (Exception e) {
            System.out.println("未找到相匹配的经纬度，请检查地址！");
        }
        return obj;
    }

    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {} catch (IOException e) {}
        return json.toString();
    }






}
