package com.dbc.task;


import com.dbc.dao.InformationDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Configuration
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class InformationTask {

            @Autowired
            InformationDao informationDao;

            @Scheduled(fixedRate=1000*60*60)
            public void SaveInformation (){
                informationDao.InsertInformation();
            }
}
