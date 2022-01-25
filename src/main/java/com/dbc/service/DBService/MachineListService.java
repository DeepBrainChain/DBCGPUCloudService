package com.dbc.service.DBService;

import com.dbc.dao.MachineListDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Service
public class MachineListService {

                @Autowired
                MachineListDao machineListDao;


                @Autowired
                Socket socket;
                @Value("${chainUrl}")
            	private   String chainUrl;
                @Test
                public void addmachine() throws URISyntaxException {
                }

}
