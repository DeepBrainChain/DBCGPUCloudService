package com.dbc.controller.committee;


import com.alibaba.fastjson.JSONObject;
import com.dbc.service.committee.VerifierCreateVMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class VerifierController {

                @Autowired
                VerifierCreateVMService verifierCreateVMService;

                // 验证人重启虚拟机
                @ResponseBody
                @RequestMapping("/Restart")
                public JSONObject RestartVM (String machine_id,String nonce,String sign,String wallet,String task_id) {
                    return verifierCreateVMService.Restart_VM(machine_id,nonce,sign,wallet,task_id);
                }

                // 获取虚拟机详情
                @ResponseBody
                @RequestMapping("/Tasks")
                public JSONObject Task (String machine_id,String nonce,String sign,String wallet) {
                    return verifierCreateVMService.Tasks(machine_id,nonce,sign,wallet);
                }
}
