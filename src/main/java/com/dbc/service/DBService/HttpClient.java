package com.dbc.service.DBService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.entity.Original;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HttpClient {
	@Value("${clientUrl}")
	private   String clientUrl;
                //参数 url 请求地址  method 请求方式 MultiValueMAp 获取结果的封装
                public String Client (MultiValueMap<String,String> params ){

                    String nodeid = "2gfpp3MAB47LB5U5kCNQmyrrSFxJbyFwjiNXwa77jdq";
                    String url = clientUrl+"/api/v1/mining_nodes/"+ nodeid ;



                    RestTemplate template = new RestTemplate();
                    ResponseEntity<String> responseEntity =template.getForEntity(url,String.class);


                    HttpMethod method =HttpMethod.POST;

//                    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    //将请求头和参数合并成一个参数
//                    HttpEntity<MultiValueMap<String,String>> requestEntity =new HttpEntity<>(params,headers);



                    return responseEntity.getBody(); // 返回结果（返回String类型）
                }

                public Original HttpRequest(MultiValueMap<String,String> params,String machine_id,HttpMethod method){
                    RestTemplate restTemplate = new RestTemplate();
                    HttpHeaders httpHeaders = new HttpHeaders();
//                    HttpMethod method =HttpMethod.POST;
                    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    params.add("machine_id",machine_id);
                    HttpEntity<MultiValueMap<String,String>>  req = new HttpEntity<>(params,httpHeaders);

                    // 执行 Http 请求
                    try {
                        ResponseEntity<Original> responseEntity =restTemplate.postForEntity(clientUrl+"/api/v1/mining_nodes",req,Original.class);
                        return responseEntity.getBody();
                    }catch (Exception e){
                        return null;
                    }

                }
                /**
                 * 生成post请求的json请求参数
                 * 请求实例
                 * {
                 *     "peer_nodes_list": [
                 *         "6a3460fd97b5ce1a9e0f05c737e899edeec8afd48f1fffb60a814d45bee0a377"
                 *     ],
                 *     "additional": {
                 *
                 *     }
                 * }
                 * @param jsonMap
                 */
                public HttpEntity<Map<String,Object>> PostJson (JSONObject jsonMap){

                        HttpHeaders httpHeaders =new HttpHeaders();

                        MediaType type =MediaType.parseMediaType("application/json;charset=UTF-8");

                        httpHeaders.setContentType(type);

                        HttpEntity<Map<String,Object>> httpEntity =new HttpEntity<>(jsonMap,httpHeaders);

                        return httpEntity;
                }

                /**
                 *  post 请求 请求参数为json
                 */
                public String SendPost (String machine_id) {

                        String url =clientUrl+"/api/v1/mining_nodes";

                        RestTemplate restTemplate = new RestTemplate();

//                        JSONArray list = new JSONArray();
//                        list.add(machine_id);
//
//    //                    list.add("6a3460fd97b5ce1a9e0f05c737e899edeec8afd48f1fffb60a814d45bee0a377");
//
//                        Map<String,JSONArray> jsonMap =new HashMap<>();
//                        jsonMap.put("peer_nodes_list",list);
//                        jsonMap.put("additional",null);
//                        jsonMap.put("sign",null);
//                        jsonMap.put("nonce",null);
//                        jsonMap.put("wallet",null);

                        List<String> list = new ArrayList<>();
                        list.add(machine_id);
                        JSONObject jsonArray =new JSONObject();
                        JSONObject jsonMap = new JSONObject();

                        jsonMap.put("peer_nodes_list",list);
                        jsonMap.put("additional",jsonArray);
                        jsonMap.put("sign","1e500ac6352889453e972ba59ba0f78318531b30c5d607c6ea585e0b8c36a14959bd82ee7005666208bb39ab08884418133600476e91c2d29d383c3441711087");
                        jsonMap.put("nonce","3hjGT9XYyh7Auf85nMHcE7zUUTTDkjzaGoeAojC2vdUnE8DXXuUdZ7g");
                        jsonMap.put("wallet","5G6Bb5Lo9em2wxm2NcSGiV4APxp5Fr9LtvMSLEspRVSq1yUF");

                        System.out.println(jsonMap);


                        ResponseEntity<String> response = restTemplate.postForEntity
                                (
                                        url,
                                        PostJson(jsonMap),
                                        String.class
                                );
                        return response.getBody();
                }



}
