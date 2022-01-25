package com.dbc.task;


import com.dbc.service.DBService.GetBlockHashService;
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
public class  GetBlockHashTask {

                    @Autowired
                    GetBlockHashService getBlockHashService;




                    @Scheduled(fixedRate=1000*30)        // 30秒更新下快高
                    public void block_hash() throws URISyntaxException {

                        getBlockHashService.BlockHash();
                    }
}
