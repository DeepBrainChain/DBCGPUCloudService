package com.dbc.task;

import com.dbc.service.DBService.MachineDetailService;
import com.dbc.service.DBService.OpperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;


@Component
@Configuration
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class MachineDetailTask {


            @Autowired
            MachineDetailService detailService;

            @Autowired
            OpperService opperService;

            @Scheduled(initialDelay = 5000,fixedRate=1000*60*10)     // 先添加后延迟跟新
            public Object oper() throws URISyntaxException { return opperService.L_Details();}

            @Scheduled(fixedRate=1000*60*10)            // 半小时更新
            public Object Detail() throws URISyntaxException { return detailService.M_Details();}

}
