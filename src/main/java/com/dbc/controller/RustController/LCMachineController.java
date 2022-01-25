package com.dbc.controller.RustController;

import com.dbc.dao.LCMachineDao;
import com.dbc.entity.LCMachineEntity;
import com.dbc.service.DBService.LCMachineService;
import com.dbc.service.committee.GetInfoMachineService;
import com.utils.HReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;
import java.util.List;

@Controller
@CrossOrigin
public class LCMachineController {

                @Autowired
                LCMachineService lcMachineService;

                @Autowired
                LCMachineDao lcMachineDao;

                @Autowired
                GetInfoMachineService getInfoMachineService;


                @ResponseBody
                @RequestMapping("/leaseCommittee_committeeMachine")
                public Object SaveMachine() throws URISyntaxException {
                    return lcMachineService.LCMachine();
    }

                @ResponseBody
                @RequestMapping("/machineinfo")
                public LCMachineEntity getInfo(String wallet){ return lcMachineDao.getMachineInfo(wallet);}

                @ResponseBody
                @RequestMapping("/bywallet")
                public Object getMachinesBywallet(String wallet,String language) throws Exception {
                    return getInfoMachineService.getMachinesBywallet(wallet,language);
                }


}
