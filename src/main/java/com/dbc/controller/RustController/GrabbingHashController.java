package com.dbc.controller.RustController;

import com.dbc.dao.GrabbingHashDao;
import com.dbc.entity.GrabbingHashEntity;
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
public class GrabbingHashController {

            @Autowired
            GrabbingHashDao grabbingHashDao;

            private static String[] reqCache = new String[100];         // 请求ID存储集合
            private static Integer reqCacheCounter = 0;     //请求计数器指示ID存储的位置


            //
            @ResponseBody
            @RequestMapping("/Save_GrabbingHash")
            public String Grabbing (GrabbingHashEntity grabbingHashEntity,String wallet,String signature,String only_key,String signaturemsg) throws IOException {

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
                    grabbingHashDao.SaveGrabbing(grabbingHashEntity);
                }else {
                    return "执行失败";
                }

                return line;

            }

            @ResponseBody
            @RequestMapping("/GetGrabbingHash")
            public Object findGra(String only_key,String wallet,String signature,String signaturemsg) throws IOException {

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
                    return grabbingHashDao.GetGraHash(only_key);
                }else {
                    return "获取原始验证结果失败";
                }

            }



//    http://localhost:8080/Save_GrabbingHash?wallet=5HL92dTnQrZSJZy7ckDVYVt9mMX3NsjShWsYDquB3eB3yb5R&signature=0x4ae5bb9bac535209ab60c6e6753f0713979ebad287bb1bd5ec87504333e43776abf7dbaaf08f1061132fb8a3834d5817a80b611fce09ce768f27d367113c5b8f&machine_id=0214630c4bc5a178f67e7f907bab649fe4757cb92f9c0147328ed0a5398ce546

}
