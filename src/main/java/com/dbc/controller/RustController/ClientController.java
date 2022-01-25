package com.dbc.controller.RustController;

import com.dbc.entity.Original;
import com.dbc.entity.ResultVO;
import com.dbc.impl.GetReqsultImpl;
import com.dbc.service.DBService.HttpClient;
import com.dbc.service.committee.VerifierMachineService;
import com.utils.HReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
@CrossOrigin
public class ClientController {


                @Autowired
                HttpClient httpClient;

                @Autowired
                VerifierMachineService verifierMachineService;

                /**
                 *  get 请求
                 * @return
                 */
                @Autowired
                GetReqsultImpl getReqsult;

                // 查看验证人虚拟机列表
                @ResponseBody
                @RequestMapping("/VerifierMachine")
                public ResponseEntity<HReply>  Verifier (String wallet, String language) {
                    return verifierMachineService.Verifier_machine(wallet,language);
                }

                @ResponseBody
                @RequestMapping("/Httpclient")
                public Object clients (){



//                    HttpMethod method = HttpMethod.POST;
                    MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
//
//                    return getReqsult.Client(method,params);
                    String machine_id = "6a3460fd97b5ce1a9e0f05c737e899edeec8afd48f1fffb60a814d45bee0a377";
                    HttpMethod method =HttpMethod.POST;
                    return httpClient.HttpRequest(params,machine_id,method);
                }

                @RequestMapping("Getdata")
                public Object getInfo(@RequestParam("machine_id") String machine_id){
//                    Original original = null;
//                    try {
//                        original = (Original) getReqsult.getFromAuthrity(machine_id);
//                    }catch (Exception e){
//
//                    }
                    ResultVO original = (ResultVO) getReqsult.getFromAuthrity(machine_id);

                    return new ResultVO(20,"机器信息",original);
                }

                @RequestMapping("GetPost")
                public Object PostJS(String machine_id){
                    return httpClient.SendPost(machine_id);
                }

}
