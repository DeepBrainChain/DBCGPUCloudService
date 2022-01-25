package com.dbc.example;

import com.thetransactioncompany.jsonrpc2.*;

import java.util.*;

public class Json_RPC {


                public static void main(String[] args) {

                    // 远程调用方法
                    String method = "makePayment";

                    // 要传递的必需参数
                    Map<String,Object> params = new HashMap<String,Object>();
                    params.put("recipient", "Penny Adams");
                    params.put("amount", 175.05);

                    // request ID
                    String id = "req-001";

                    System.out.println("Creating new request with properties :");
                    System.out.println("\tmethod     : " + method);
                    System.out.println("\tparameters : " + params);
                    System.out.println("\tid         : " + id + "\n\n");

                    // 创建请求
                    JSONRPC2Request reqOut = new JSONRPC2Request(method, params, id);


                    // 序列化对JSON编码字符串的请求
                    String jsonString = reqOut.toString();

                    System.out.println("Serialised request to JSON-encoded string :");
                    System.out.println("\t" + jsonString + "\n\n");


                    // 字符串现在可以发送到服务器


                    // 解析请求字符串
                    JSONRPC2Request reqIn = null;

                    try {
                        reqIn = JSONRPC2Request.parse(jsonString);

                    } catch (JSONRPC2ParseException e) {
                        System.out.println(e.getMessage());
                        return;
                    }

                    // 提取请求数据
                    System.out.println("Parsed request with properties :");
                    System.out.println("\tmethod     : " + reqIn.getMethod());
                    System.out.println("\tparameters : " + reqIn.getNamedParams());
                    System.out.println("\tid         : " + reqIn.getID() + "\n\n");


                    // 处理请求...


                    String result = "payment-id-001";

                    System.out.println("Creating response with properties : ");
                    System.out.println("\tresult : " + result);
                    System.out.println("\tid     : " + reqIn.getID() + "\n\n"); // ID must be echoed back


                    // 创建 response
                    JSONRPC2Response respOut = new JSONRPC2Response(result, reqIn.getID());


                    // 序列化对JSON编码字符串的响应
                    jsonString = respOut.toString();

                    System.out.println("Serialised response to JSON-encoded string :");
                    System.out.println("\t" + jsonString + "\n\n");


                    // 可以将响应发送回客户端...


                    // 解析响应字符串
                    JSONRPC2Response respIn = null;

                    try {
                        respIn = JSONRPC2Response.parse(jsonString);

                    } catch (JSONRPC2ParseException e) {
                        System.out.println(e.getMessage());
                        return;
                    }


                    // 检查成功或失败
                    if (respIn.indicatesSuccess()) {
                        System.out.println("The request succeeded :");

                        System.out.println("\tresult : " + respIn.getResult());
                        System.out.println("\tid     : " + respIn.getID());
                    }
                    else {
                        System.out.println("The request failed :");

                        JSONRPC2Error err = respIn.getError();

                        System.out.println("\terror.code    : " + err.getCode());
                        System.out.println("\terror.message : " + err.getMessage());
                        System.out.println("\terror.data    : " + err.getData());
                    }
                }
}