package com.dbc.controller.RustController;

import com.dbc.controller.IdempotentUtils;
import com.dbc.dao.NoRepeatSubmit;
import com.dbc.dao.ResultHashDao;
import com.dbc.entity.ResultHashEntity;
import com.dbc.service.DBService.LCMachineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@CrossOrigin
public class  ResultHashController {

    private static final Logger log = LoggerFactory.getLogger(ResultHashController.class);

                @Autowired
                ResultHashDao resultHashDao;

                @Autowired
                LCMachineService lcMachineService;

                private static String[] reqCache = new String[100];         // 请求ID存储集合
                private static Integer reqCacheCounter = 0;     //请求计数器指示ID存储的位置

                @ResponseBody
                @RequestMapping("Save_ResultHash")
                public String getRequest (ResultHashEntity resultHashEntity,String only_key, String wallet,String signature,String machine_id,String signaturemsg) throws IOException {

                    if(Arrays.asList(reqCache).contains(only_key)){
                        // 重复请求
                            System.out.println("请勿重复提交!" +only_key);
                            return "执行失败，请勿重复提交";
                    }
                    synchronized (this.getClass()) {     // 双重检查锁（DCL） 提高执行效率
                        if (Arrays.asList(reqCache).contains(only_key)) {
                            System.out.println("请勿重复提交!" + only_key);
                            return "执行失败，请勿重复提交";
                        }
                        // 记录请求ID
                        if (reqCacheCounter >= reqCache.length) reqCacheCounter = 0; //重置
                        reqCache[reqCacheCounter] = only_key; //保存到缓存
                        reqCacheCounter++;  // 下表移动
                    }

                    List<String> params = new ArrayList<String>();
                    params.add("node");
                    params.add("src\\main\\resources\\DeepBrainChain\\verify_signature.js");
                    params.add("--msg");
                    params.add(signaturemsg);
                    params.add("--addr");
                    params.add(wallet);
                    params.add("--sig");
                    params.add(signature);

                    ProcessBuilder processBuilder = new ProcessBuilder(params);
                    processBuilder.redirectErrorStream(true);
                    Process process = processBuilder.start();
                    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line = br.readLine();
                    System.out.println(br.readLine());

                    if (line.startsWith("true")){
                        resultHashDao.Saveresult(resultHashEntity,wallet,signature,machine_id);
                    }else {
                        return "执行失败";
                    }

                    return line;
                }


                @RequestMapping("/shows")
                public String show(){
                    return "PutHash";
                }

                @ResponseBody
                @RequestMapping("/GetResultHash")
                public Object findHash(String wallet,String signature,String only_key,String signaturemsg) throws IOException {

                    List<String> params = new ArrayList<String>();
                    params.add("node");
                    params.add("src\\main\\resources\\DeepBrainChain\\verify_signature.js");
                    params.add("--msg");
                    params.add(signaturemsg);
                    params.add("--addr");
                    params.add(wallet);
                    params.add("--sig");
                    params.add(signature);

                    ProcessBuilder processBuilder = new ProcessBuilder(params);
                    processBuilder.redirectErrorStream(true);
                    Process process = processBuilder.start();
                    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line = br.readLine();
                    System.out.println(br.readLine());

                    if (line.startsWith("true")){
                        return resultHashDao.GetRHash(only_key);
                    }else {
                        return "获取原始验证结果失败";
                    }
                }

}
