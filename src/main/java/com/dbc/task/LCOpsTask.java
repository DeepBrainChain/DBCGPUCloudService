package com.dbc.task;

import com.dbc.dao.LCOpsDao;
import com.dbc.entity.LCOpsEntity;
import com.dbc.service.DBService.LCOpsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;

@Component
@Configuration
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class LCOpsTask {

                @Autowired
                LCOpsService lcOpsService;

                @Autowired
                LCOpsDao lcOpsDao;

                @Scheduled(fixedRate=1000*60*30)//5分钟检测一次
                public Object getlcops() throws URISyntaxException {
                    return lcOpsService.LCO();
                }


}
