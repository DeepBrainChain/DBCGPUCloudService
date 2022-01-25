package com.dbc.impl;

import com.dbc.entity.ResultVO;
import com.utils.Request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

@Component
@Service
public class GetReqsultImpl {
	@Value("${clientUrl}")
	private   String clientUrl;
                public Object getFromAuthrity (String machine_id){

                    String AuthUrl =clientUrl+"/api/v1/mining_nodes";

                    MultiValueMap<String,String> params =new LinkedMultiValueMap<>();
                    params.add("machine_id",machine_id);

                    // 发送post并返回数据
                    ResultVO resultVO = Request.sendPostRequest(AuthUrl,params);

//                    if(resultVO.getCode() != 20){       //进行异常处理
//                        switch (resultVO.getCode()){
//                            case 17: throw new SiteException(ResultEnum.AUTHORIZE_DOMAIN_NOT_EXIST);
//                            case 18: throw new SiteException(ResultEnum.AUTHORIZE_USER_NOT_EXIST);
//                            case 19: throw new SiteException(ResultEnum.AUTHORIZE_USER_INFO_INCORRECT);
//                            default: throw new SiteException(ResultEnum.SYSTEM_ERROR);
//                        }
//                    }
                    LinkedHashMap linkedHashMap = (LinkedHashMap) resultVO.getData();
                    return linkedHashMap;

                }

                public String Client (HttpMethod method, MultiValueMap<String,String> params ){

                    String url = clientUrl+"/api/v1/mining_nodes/";
                    RestTemplate template = new RestTemplate();
                    ResponseEntity<String> responseEntity =template.getForEntity(url,String.class);

                    return responseEntity.getBody(); // 返回结果（返回String类型）
                }
}
