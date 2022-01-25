package com.dbc.task;

import com.dbc.dao.LCMachineDao;
import com.dbc.service.DBService.LCMachineService;
import com.dbc.service.committee.GetInfoMachineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
public class LCMachineTask {

            @Autowired
            LCMachineService lcMachineService;


            @Scheduled(fixedRate=1000*60)
            public Object SaveMachine() throws URISyntaxException {
                return lcMachineService.LCMachine();
            }
}
