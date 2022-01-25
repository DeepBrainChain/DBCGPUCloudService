package com.dbc.task;

import com.dbc.dao.CommitteeListDao;
import com.dbc.service.DBService.CommitteeService;
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
public class GetMCommitTask {

                @Autowired
                CommitteeService committeeService;


                @Scheduled(fixedRate=1000*60*10)
                public String SaveCommittee() throws URISyntaxException {
                    return  committeeService.Commit();
                }
}
