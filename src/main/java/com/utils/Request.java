package com.utils;

import com.dbc.entity.ResultVO;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class Request {

            /**
             *  向url发送post请求
             * @param url     URl
             * @param params    发送的参数
             * @return
             */
            public static ResultVO sendPostRequest(String url, MultiValueMap<String,String> params){
                RestTemplate client =new RestTemplate();
                HttpHeaders headers =new HttpHeaders();
                HttpMethod method =HttpMethod.POST;

                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                //将请求头和参数合并成一个参数
                HttpEntity<MultiValueMap<String,String>> requestEntity =new HttpEntity<>(params,headers);
                //执行HTTP请求，将放回的结构使用ResultVo结构化
                ResponseEntity<ResultVO> response =client.exchange(url,method,requestEntity,ResultVO.class);

                return response.getBody();


            }

}
