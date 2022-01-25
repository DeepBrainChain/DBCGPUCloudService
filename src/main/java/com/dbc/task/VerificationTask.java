package com.dbc.task;

import com.dbc.dao.VerificationDao;
import com.dbc.service.DBService.VerificationService;
import com.utils.HReply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;

@Component
@Configuration
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class VerificationTask {

                @Autowired
                VerificationService verificationService;

                @Autowired
                VerificationDao verificationDao;

                @Scheduled(fixedRate=1000*60*10)         //5分钟检测一次
                public Object verif() throws URISyntaxException {
                    return verificationService.Verification();
                }

}
