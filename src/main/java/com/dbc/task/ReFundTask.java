package com.dbc.task;

import com.dbc.service.committee.RefundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;


//@Component
//@Configuration
//@EnableScheduling
//@Slf4j
public class ReFundTask {

            @Autowired
            RefundService refundService;


            @Scheduled(fixedRate=1000*60*35)
            public void RefundInfo () throws URISyntaxException {refundService.ReFund();}
}
